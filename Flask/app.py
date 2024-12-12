from flask import Flask, request, jsonify, render_template
import tensorflow as tf
import pandas as pd
import numpy as np
import requests
from tensorflow.keras.models import load_model
from sklearn.neighbors import NearestNeighbors
from geopy.geocoders import Nominatim

# Initialize Flask app
app = Flask(__name__)

def mse(y_true, y_pred):
    return MeanSquaredError()(y_true, y_pred)

# Memuat model dengan custom_objects
model = load_model('travelin.h5', custom_objects={'mse': mse})

# Load and preprocess destination data from API
def fetch_destinations():
    try:
        response = requests.get('https://backend-service1-473303426237.asia-southeast2.run.app/places')
        response.raise_for_status()
        destinations = pd.DataFrame(response.json())

    
        print(f"Fetched {len(destinations)} destinations from API.")

        # Preprocessing
        destinations['category'] = destinations['category'].fillna('Unknown')

        # Convert 'rating' column to numeric and handle invalid values
        destinations['rating'] = pd.to_numeric(destinations['rating'], errors='coerce')
        destinations['rating'] = destinations['rating'].fillna(destinations['rating'].mean())  # Fill NaN with mean

        destinations['price'] = destinations['price'].fillna(0)
        return destinations
    except requests.exceptions.RequestException as e:
        raise RuntimeError(f"Failed to fetch destinations: {e}")
    except Exception as e:
        raise RuntimeError(f"Data processing error: {e}")



def calculate_distances(destinations, user_location):
    user_lat, user_long = user_location
    destinations['distance'] = np.sqrt(
        (destinations['lat'] - user_lat) ** 2 +
        (destinations['long'] - user_long) ** 2
    )
    # Round distances to two decimal places
    destinations['distance'] = destinations['distance'].round(2)
    return destinations.sort_values(by='distance')


def recommend_destinations(destinations, tourists_location, k=5):
    if destinations.empty:
        raise ValueError("No destinations available for recommendation.")

    features = ['lat', 'long', 'rating']
    X = destinations[features]

    # Ensure k does not exceed the number of data points
    k = min(k, len(destinations))
    if k <= 0:
        raise ValueError("The value of k must be at least 1.")

    knn = NearestNeighbors(n_neighbors=k)
    knn.fit(X)

    placeholder_rating = destinations['rating'].mean()
    user_data = [[tourists_location[0], tourists_location[1], placeholder_rating]]
    distances, indices = knn.kneighbors(user_data)

    recommended_destinations = destinations.iloc[indices[0]]
    return recommended_destinations

# Modify the response in the predict function to include the 'description' field
@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.json
        lat = data.get('lat')  # Ambil latitude dari input
        long = data.get('long')  # Ambil longitude dari input

        # Validasi input lat dan long
        if lat is not None and long is not None:
            try:
                user_location = (float(lat), float(long))
            except ValueError:
                return jsonify({'error': 'Latitude and longitude must be valid numbers.'}), 400
        else:
            return jsonify({'error': 'Latitude and longitude must be provided.'}), 400

        # Fetch destinations
        destinations = fetch_destinations()

        # Calculate distances
        sorted_destinations = calculate_distances(destinations, user_location)

        # Recommend destinations (Top 5 nearest)
        recommendations = recommend_destinations(sorted_destinations, user_location, k=5)

        # Prepare the correct number of features (add any missing feature)
        features_for_prediction = []
        for _, destination in recommendations.iterrows():
            lat, long, rating, price, description = destination['lat'], destination['long'], destination['rating'], destination['price'], destination.get('description', 'No description available')
            # Assuming you need lat, long, rating, price, and description
            features_for_prediction.append([lat, long, rating, price, description])

        features_for_prediction = np.array(features_for_prediction)
        
        if features_for_prediction.shape[1] != 5:  # Adjusting to match 5 features now
            return jsonify({'error': f"Expected 5 input features, but got {features_for_prediction.shape[1]}."}), 400

        # predicted_relevance = model.predict(features_for_prediction)
        
        # max_relevance = np.max(predicted_relevance)
        
        # if max_relevance > 0:
        #     predicted_relevance = (predicted_relevance / max_relevance) * 100
        
        # predicted_relevance = np.round(predicted_relevance).astype(int)
        
        # recommendations['predicted_relevance'] = predicted_relevance
        
        response = {
            'recommendations': recommendations[['place_name', 'city', 'category', 'rating', 'price', 'distance', 'description']].to_dict('records')
        }

        return jsonify(response)

    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5634)