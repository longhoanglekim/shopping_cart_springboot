const { defineConfig } = require('@vue/cli-service');
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        port: 3000,
        proxy: {
            '/ws': {
                target: 'http://localhost:8080',  // Proxy cho WebSocket server trên Spring Boot
                changeOrigin: true,
                ws: true
            }
        },
        hot: false,  // Tắt HMR (Hot Module Replacement)
        liveReload: false  // Tắt tính năng Live Reload
    }
});
