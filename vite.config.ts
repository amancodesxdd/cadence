import { defineConfig } from 'vite';

// Vite config. `server.port` keeps the dev server on a fixed port so we can
// always bookmark the same URL while learning.
export default defineConfig({
  server: {
    port: 5173,
    open: false,
  },
  build: {
    target: 'es2022',
  },
});
