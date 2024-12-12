const redis = require('redis');
const admin = require('firebase-admin');
const tf = require('@tensorflow/tfjs-node');
const serviceAccount = require('../../credentials.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
});

const db = admin.firestore();

// Konfigurasi Redis
const redisClient = redis.createClient({
    socket: {
        host: '10.79.20.251', // Ganti dengan host Redis dari Memorystore
        port: 6379,
    },
});

redisClient.connect()
    .then(() => console.log('Connected to Redis'))
    .catch(err => console.error('Redis connection error:', err));

// Fungsi Haversine untuk menghitung jarak
function calculateDistance(lat1, lon1, lat2, lon2) {
    const R = 6371; // Radius bumi dalam kilometer
    const dLat = ((lat2 - lat1) * Math.PI) / 180;
    const dLon = ((lon2 - lon1) * Math.PI) / 180;
    const a =
        Math.sin(dLat / 2) ** 2 +
        Math.cos((lat1 * Math.PI) / 180) *
        Math.cos((lat2 * Math.PI) / 180) *
        Math.sin(dLon / 2) ** 2;
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
}

// Fungsi rekomendasi tempat wisata menggunakan model ML
const getRecommendations = async (request, h) => {
    const { userId } = request.params;
    const modelPath = './my_model/model.json'; // Path model TensorFlow.js Anda

    try {
        // Ambil lokasi pengguna dari Firestore
        const userDoc = await db.collection('userLocations').doc(userId).get();
        if (!userDoc.exists) {
            return h.response({ error: `User with ID ${userId} not found` }).code(404);
        }
        const userLocation = userDoc.data();

        // Ambil semua tempat wisata dari Firestore
        const placesRef = db.collection('places');
        const snapshot = await placesRef.get();
        const places = snapshot.docs.map(doc => ({
            id: doc.id,
            ...doc.data(),
        }));

        // Load model TensorFlow.js
        const model = await tf.loadLayersModel(`file://${modelPath}`);

        // Hitung jarak dan skor relevansi
        const recommendations = places
            .map((place) => {
                const distance = calculateDistance(
                    userLocation.latitude,
                    userLocation.longitude,
                    place.lat,
                    place.long
                );

                // Prediksi skor relevansi berdasarkan jarak
                const score = model.predict(tf.tensor2d([[distance]])).dataSync()[0];
                return { ...place, distance: distance.toFixed(2), score: score.toFixed(2) };
            })
            .filter((item) => item.distance <= 10) // Filter jarak <= 10 KM
            .sort((a, b) => b.score - a.score); // Urutkan berdasarkan skor

        return h.response(recommendations).code(200);
    } catch (error) {
        console.error('Error fetching recommendations:', error);
        return h.response({ error: 'Failed to fetch recommendations' }).code(500);
    }
};
// Handler getPlaces
const getPlaces = async (request, h) => {
    const cacheKey = `places_data`; 

    try {
        const cachedData = await redisClient.get(cacheKey);
        if (cachedData) {
            console.log('Cache hit');
            return h.response(JSON.parse(cachedData)).code(200);
        }

        console.log('Cache miss, fetching from Firestore');
        const placesRef = db.collection('places').orderBy('place_name'); 
        console.time('fetchPlaces');
        const snapshot = await placesRef.get();
        console.timeEnd('fetchPlaces');

        const places = snapshot.docs.map(doc => ({
            id: doc.id,
            ...doc.data(),
        }));

        // Simpan hasil ke Redis
        await redisClient.set(cacheKey, JSON.stringify(places), {
            EX: 3600, // TTL dalam detik
        });

        return h.response(places).code(200);
    } catch (error) {
        console.error('Error fetching places:', error);
        return h.response({ error: 'Failed to fetch places' }).code(500);
    }
};


// Mendapatkan tempat berdasarkan ID
const getPlaceByID = async (request, h) => {
    const { id: placeID } = request.params;

    try {
        const placeRef = db.collection('places').doc(placeID);
        const doc = await placeRef.get();

        if (!doc.exists) {
            return h.response({ message: 'Place not found' }).code(404);
        }

        return h.response({ id: doc.id, ...doc.data() }).code(200);
    } catch (error) {
        console.error('Error fetching place by ID:', error);
        return h.response({ error: 'Failed to fetch place by ID' }).code(500);
    }
};

// Mendapatkan tempat berdasarkan kategori
const getPlacesByCategory = async (request, h) => {
    const { category } = request.params;

    try {
        const placesRef = db.collection('places').where('category', '==', category);
        const snapshot = await placesRef.get();

        const places = snapshot.docs.map(doc => ({
            id: doc.id,
            ...doc.data(),
        }));

        return h.response(places).code(200);
    } catch (error) {
        console.error('Error fetching places by category:', error);
        return h.response({ error: 'Failed to fetch places by category' }).code(500);
    }
};

// Menyimpan lokasi pengguna
const saveUserLocation = async (request, h) => {
    try {
        const { userId, latitude, longitude } = request.payload;

        if (!userId || !latitude || !longitude) {
            return h.response({ message: 'Missing required fields' }).code(400);
        }

        await db.collection('userLocations').doc(userId).set({
            latitude,
            longitude,
            timestamp: admin.firestore.FieldValue.serverTimestamp(),
        });

        return h.response({ message: 'Location saved successfully!' }).code(200);
    } catch (error) {
        console.error('Error saving user location:', error);
        return h.response({ error: 'Failed to save location' }).code(500);
    }
};

// Mendapatkan lokasi pengguna berdasarkan ID
const getLocUserById = async (request, h) => {
    const { userId } = request.params;

    try {
        const userDoc = await db.collection('userLocations').doc(userId).get();

        if (!userDoc.exists) {
            return h.response({ error: `User with ID ${userId} not found` }).code(404);
        }

        const userData = userDoc.data();

        if (userData.timestamp && userData.timestamp.toDate) {
            userData.timestamp = userData.timestamp.toDate().toISOString();
        }

        return h.response(userData).code(200);
    } catch (error) {
        console.error('Error fetching user location by ID:', error);
        return h.response({ error: 'Failed to fetch user location' }).code(500);
    }
};

const getAllUserId = async (request, h) => {
    try {
        const usersRef = db.collection('userLocations');
        const snapshot = await usersRef.get();

        const users = snapshot.docs.map(doc => ({
            id: doc.id,
            ...doc.data(),
        }));

        return h.response(users).code(200);
    } catch (error) {
        console.error('Error fetching users:', error);
        return h.response({ error: 'Failed to fetch users' }).code(500);
    }
};


const getTopPlaces = async (request, h) => {
    try {
        const usersRef = db.collection('placeTop');
        const snapshot = await usersRef.get();

        const users = snapshot.docs.map(doc => ({
            id: doc.id,
            ...doc.data(),
        }));

        return h.response(users).code(200);
    } catch (error) {
        console.error('Error fetching places:', error);
        return h.response({ error: 'Failed to fetch places' }).code(500);
    }
};

module.exports = {
    getPlaces,
    getPlaceByID,
    getPlacesByCategory,
    saveUserLocation,
    getLocUserById,
    getAllUserId,
    getRecommendations,
    getTopPlaces
};
