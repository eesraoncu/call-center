import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login as loginService, approveSecurity } from '../services/auth';
import { useAuth } from '../context/AuthContext';
import Policy from '../components/policy';

export default function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [showPolicy, setShowPolicy] = useState(false);
    const [tempUser, setTempUser] = useState(null);
    const navigate = useNavigate();
    const { login } = useAuth();

    const togglePolicy = () => {
        setShowPolicy(!showPolicy);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const creds = await loginService({ email, password });

            // Eğer kullanıcı gizlilik sözleşmesini onaylamamışsa
            if (!creds.securityApproved) {
                setTempUser(creds); // Kullanıcı bilgilerini sakla
                togglePolicy(); // Politikayı göster
                return;
            }

            // Onaylanmışsa direkt giriş yap
            login(creds);
            navigate('/');
        } catch (err) {
            setError(err.message || 'Giriş sırasında bir hata oluştu. Lütfen bilgilerinizi kontrol edin.');
        }
    };

    const handlePolicyAccept = async () => {
        try {
            await approveSecurity(tempUser.id, tempUser.email);
            const creds = await loginService({ email, password });
            login(creds);
            navigate('/');
        } catch (err) {
            setError(err.message || 'Onay işlemi sırasında hata oluştu');
        } finally {
            togglePolicy();
        }
    };

    return (
        <div className="login-container">
            <form onSubmit={handleSubmit} className="login-form">
                <h2 className="login-title">Giriş Yap</h2>
                <input
                    type="email"
                    placeholder="E-posta"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    className="login-input"
                />
                <input
                    type="password"
                    placeholder="Şifre"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    className="login-input"
                />
                <button type="submit" className="login-button">
                    Giriş
                </button>
                {error && <p className="login-error">{error}</p>}
            </form>

            <Policy
                isOpen={showPolicy}
                onClose={togglePolicy}
                onAccept={handlePolicyAccept}
            />
        </div>
    );
}