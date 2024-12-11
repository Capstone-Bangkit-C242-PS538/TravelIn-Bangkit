const placesHandler = require('../handler/placesHandler');

const routes = [
    {
        method: 'GET',
        path: '/places',
        handler: placesHandler.getPlaces
    },
    {
        method: 'GET',
        path: '/places/{id}',
        handler: placesHandler.getPlaceByID,
    },
    {
        method: 'GET',
        path: '/places/category/{category}',
        handler: placesHandler.getPlacesByCategory
    },
    {
        method: 'POST',
        path: '/location',
        handler: placesHandler.saveUserLocation,
    },
    {
        method: 'GET',
        path: '/location/user/{userId}',
        handler: placesHandler.getLocUserById,
    },
    {
        method: 'GET',
        path: '/users',
        handler: placesHandler.getAllUserId,
    },
    {
        method: 'GET',
        path: '/recommendations/{userId}',
        handler: placesHandler.getRecommendations,
    },
    {
        method: 'GET',
        path: '/top-places',
        handler: placesHandler.getTopPlaces,
    }
];

module.exports = routes;
