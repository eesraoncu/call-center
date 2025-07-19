// src/services/auth.js
import apiClient from './apiClient';

/**
 * Kullanıcı giriş işlemi
 * @param {string} email - Kullanıcı email adresi
 * @param {string} password - Kullanıcı şifresi
 * @returns {Promise<object>} - Kullanıcı bilgileri ve token
 */
export async function login({ email, password }) {
    try {
        // API'ye login isteği gönder
        const response = await apiClient.post('/login', {
            email,
            password
        });

        // Yanıt verisini kontrol et
        if (!response.data || response.data.status !== 'success') {
            throw new Error(response.data?.message || 'Giriş başarısız: Geçersiz yanıt');
        }

        // Gerekli alanları destructing ile al
        const {
            token,
            role,
            securityApproved,
            id,
            operatorId,
            name // İhtiyaca göre ek bilgiler
        } = response.data;

        // Zorunlu alanları kontrol et
        if (!token) throw new Error('Token bilgisi eksik');
        if (!role) throw new Error('Rol bilgisi eksik');
        if (typeof securityApproved !== 'boolean') {
            throw new Error('Geçersiz güvenlik onay durumu');
        }

        // Dönecek kullanıcı verisi
        return {
            token,
            email,
            role,
            securityApproved,
            id,
            operatorId,
            name
        };

    } catch (error) {
        // Hata mesajını işle
        let errorMessage = 'Giriş sırasında bir hata oluştu';

        if (error.response) {
            // Backend'den gelen hata mesajı
            errorMessage = error.response.data?.message || errorMessage;
        } else if (error.request) {
            // İstek gönderildi ama yanıt alınamadı
            errorMessage = 'Sunucuya ulaşılamadı';
        } else {
            // Diğer hatalar
            errorMessage = error.message || errorMessage;
        }

        throw new Error(errorMessage);
    }
}

/**
 * Gizlilik sözleşmesi onay işlemi
 * @param {number|string} userId - Onaylanacak kullanıcı ID'si
 * @returns {Promise<object>} - İşlem sonucul
 */
export const approveSecurity = async (userId, email) => {
    try {
        const response = await apiClient.post('/api/operators/{id}/approve-security', {
            email: email,
            approved: true
        });
        return response.data;
    } catch (error) {
        throw new Error(error.response?.data?.message || 'Onay işlemi başarısız');
    }
};
/**
 * Kullanıcı oturumunu kontrol etme
 * @returns {Promise<object>} - Mevcut kullanıcı bilgileri
 */
export const checkAuth = async () => {
    try {
        const response = await apiClient.get('/api/auth/check');
        return response.data;
    } catch (error) {
        throw new Error('Oturum kontrolü başarısız');
    }
};