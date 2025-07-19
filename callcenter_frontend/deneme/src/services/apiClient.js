import axios from 'axios';

// Proxy kullanıldığı için baseURL ayarı yok,
// istekler '/login' veya '/api/…' ile başlamalı.

const apiClient = axios.create({
    // withCredentials: true, // Backend cookie/sessions kullanıyorsa açabilirsin
});

apiClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            // Token geçersizse logout işlemi
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);


export default apiClient;
