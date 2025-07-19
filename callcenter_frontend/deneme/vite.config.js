// vite.config.js
import { defineConfig, loadEnv } from 'vite'
import react from '@vitejs/plugin-react'

export default ({ mode }) => {
  // .env dosyasındaki VITE_API_BASE_URL değişkenini yükle
  const env = loadEnv(mode, process.cwd(), '')

  return defineConfig({
    plugins: [react()],
    server: {

      proxy: {
        // Login isteklerini backend'e yönlendir
        '/login': {
          target: env.VITE_API_BASE_URL,
          changeOrigin: true,
          secure: false,
        },
        // Tüm /api/* isteklerini backend'e yönlendir
        '/api': {
          target: env.VITE_API_BASE_URL,
          changeOrigin: true,
          secure: false,
        },
      },
    },
    resolve: {
      alias: {
        '@services': '/src/services',
      },
    },
  })
}
