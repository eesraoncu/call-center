import React, { createContext, useState, useEffect, useContext, useCallback } from 'react';
import { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';

const AuthContext = createContext();

function getFingerprint() {
    return btoa(navigator.userAgent + navigator.language);
}

export function AuthProvider({ children }) {
    const navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    // Operatör ID'sini localStorage'dan almak için yardımcı fonksiyon
    const getStoredOperatorId = () => {
        return localStorage.getItem('operatorId') || null;
    };

    // Token doğrulama fonksiyonu (useCallback ile optimize edildi)
    const validateToken = useCallback(() => {
        try {
            const token = localStorage.getItem('token');
            const usr = localStorage.getItem('user');
            const storedFp = localStorage.getItem('fingerprint');
            if (!token || !usr || !storedFp) throw new Error('Missing auth data');

            let decoded;
            try {
                decoded = jwtDecode(token);
            } catch {
                logout();
                return false;
            }

            if (Date.now() >= decoded.exp * 1000) {
                logout();
                return false;
            }

            if (storedFp !== getFingerprint()) {
                logout();
                return false;
            }

            const userData = JSON.parse(usr);
            setUser({ token, ...userData });
            return true;
        } catch (e) {
            logout();
            return false;
        }
    }, []);

    useEffect(() => {
        const ok = validateToken();
        if (!ok) setUser(null);
        setLoading(false);
    }, [validateToken]);

    const login = ({ token, email, role, securityApproved, userId, operatorId }) => {
        const userObj = {
            email,
            role,
            securityApproved,
            userId,
            operatorId: operatorId || getStoredOperatorId() // Önce gelen operatorId, yoksa stored olanı kullan
        };
        debugger;
        localStorage.setItem('token', token);
        localStorage.setItem('user', JSON.stringify(userObj));
        localStorage.setItem('fingerprint', getFingerprint());

        // Operatör ID'sini ayrıca kaydet (eğer varsa)
        if (operatorId) {
            localStorage.setItem('operatorId', operatorId);
        }

        setUser({ token, ...userObj });
        navigate('/');
    };

    const logout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        localStorage.removeItem('fingerprint');
        // Operatör ID'sini silme (diğer sayfalarda kullanılabilir)
        setUser(null);
        navigate('/login');
    };

    // Operatör ID'sini döndüren fonksiyon (öncelikle user'dan, sonra localStorage'dan)
    const getOperatorId = () => {
        return user?.operatorId || getStoredOperatorId();
    };

    // Token validasyonunu düzenli aralıklarla kontrol et
    useEffect(() => {
        if (!user) return;
        const iv = setInterval(() => {
            validateToken();
        }, 60_000);
        return () => clearInterval(iv);
    }, [user, validateToken]);

    // Storage event'lerini dinle
    useEffect(() => {
        const onStorage = (e) => {
            if (['token', 'user', 'fingerprint'].includes(e.key)) {
                validateToken();
            }
        };
        window.addEventListener('storage', onStorage);
        return () => window.removeEventListener('storage', onStorage);
    }, [validateToken]);

    return (
        <AuthContext.Provider
            value={{
                user,
                login,
                logout,
                loading,
                getOperatorId, // Müşteri ekleme sayfasında kullanılacak
                isAuthenticated: !!user?.token
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
}