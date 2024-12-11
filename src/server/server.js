const Hapi = require('@hapi/hapi');
const placesRoutes = require('../routes/placeRoutes');

const init = async () => {
    const server = Hapi.server({
        port: process.env.PORT || 5423,
        host: '0.0.0.0',
        routes: {
            cors: {
              origin: ['*'],
            },
        },
    });

    server.route(placesRoutes);

    await server.start();
    console.log('Server running on %s', server.info.uri);
};

process.on('unhandledRejection', (err) => {
    console.log(err);
    process.exit(1);
});

init();