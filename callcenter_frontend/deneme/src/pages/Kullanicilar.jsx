import React, { useEffect, useState } from 'react';
import {
    getAllOperators,
    createOperator,
    updateOperator,
    deleteOperator,
} from '../services/api';

export default function Kullanicilar() {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [editUser, setEditUser] = useState(null);
    const [formData, setFormData] = useState({
        operatorName: '',
        operatorSurname: '',
        operatorEmail: '',
        operatorPhoneNumber: '',
        operatorPassword: '',
    });
    const [error, setError] = useState('');

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {
        setLoading(true);
        try {
            const result = await getAllOperators();
            setUsers(Array.isArray(result) ? result : result?.data || []);
        } catch {
            setUsers([]);
        }
        setLoading(false);
    };

    const openAddModal = () => {
        setEditUser(null);
        setFormData({
            operatorName: '',
            operatorSurname: '',
            operatorEmail: '',
            operatorPhoneNumber: '',
            operatorPassword: '',
        });
        setError('');
        setShowModal(true);
    };

    const openEditModal = (user) => {
        setEditUser(user);
        setFormData({
            operatorName: user.operatorName,
            operatorSurname: user.operatorSurname,
            operatorEmail: user.operatorEmail,
            operatorPhoneNumber: user.operatorPhoneNumber,
            operatorPassword: '',
        });
        setError('');
        setShowModal(true);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        if (!formData.operatorName || !formData.operatorSurname || !formData.operatorEmail) {
            setError('Ad, Soyad ve E-posta zorunludur.');
            return;
        }

        try {
            if (editUser) {
                const updatePayload = { ...formData };
                if (!updatePayload.operatorPassword) delete updatePayload.operatorPassword;
                await updateOperator(editUser.operatorId, updatePayload);
            } else {
                await createOperator(formData);
            }
            await fetchUsers();
            setShowModal(false);
        } catch (err) {
            setError(err?.response?.data?.message || 'Bir hata oluştu');
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm('Bu kullanıcıyı silmek istediğinizden emin misiniz?')) return;
        try {
            await deleteOperator(id);
            await fetchUsers();
        } catch {
            alert('Silme işlemi sırasında hata oluştu.');
        }
    };

    return (
        <div className="kullanicilar-container">
            <h1 className="kullanicilar-heading">Kullanıcılar</h1>
            <button className="add-customer-btn" onClick={openAddModal}>
                + Yeni Kullanıcı Ekle
            </button>

            {loading ? (
                <p>Yükleniyor...</p>
            ) : (
                <div className="customer-table-container">
                    <table className="customer-table">
                        <thead>
                            <tr>
                                <th>Ad</th>
                                <th>Soyad</th>
                                <th>E-posta</th>
                                <th>Telefon</th>
                                <th>İşlemler</th>
                            </tr>
                        </thead>
                        <tbody>
                            {users.map(user => (
                                <tr key={user.operatorId}>
                                    <td>{user.operatorName}</td>
                                    <td>{user.operatorSurname}</td>
                                    <td>{user.operatorEmail}</td>
                                    <td>{user.operatorPhoneNumber}</td>
                                    <td>
                                        <button
                                            className="action-btn edit-btn"
                                            onClick={() => openEditModal(user)}
                                        >Düzenle</button>
                                        <button
                                            className="action-btn delete-btn"
                                            onClick={() => handleDelete(user.operatorId)}
                                        >Sil</button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

            {showModal && (
                <div className="modal-overlay" onClick={() => setShowModal(false)}>
                    <div className="modal-content" onClick={e => e.stopPropagation()}>
                        <div className="modal-header">
                            <h2>{editUser ? 'Kullanıcı Düzenle' : 'Yeni Kullanıcı Ekle'}</h2>
                            <button className="close-modal" onClick={() => setShowModal(false)}>&times;</button>
                        </div>
                        <form onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label>Ad
                                    <input
                                        name="operatorName"
                                        value={formData.operatorName}
                                        onChange={handleChange}
                                        required
                                    />
                                </label>
                            </div>
                            <div className="form-group">
                                <label>Soyad
                                    <input
                                        name="operatorSurname"
                                        value={formData.operatorSurname}
                                        onChange={handleChange}
                                        required
                                    />
                                </label>
                            </div>
                            <div className="form-group">
                                <label>E-posta
                                    <input
                                        type="email"
                                        name="operatorEmail"
                                        value={formData.operatorEmail}
                                        onChange={handleChange}
                                        required
                                    />
                                </label>
                            </div>
                            <div className="form-group">
                                <label>Telefon
                                    <input
                                        name="operatorPhoneNumber"
                                        value={formData.operatorPhoneNumber}
                                        onChange={handleChange}
                                    />
                                </label>
                            </div>
                            <div className="form-group">
                                <label>Şifre {editUser ? '(Değiştirmek için yazın)' : '(Zorunlu)'}
                                    <input
                                        type="password"
                                        name="operatorPassword"
                                        value={formData.operatorPassword}
                                        onChange={handleChange}
                                        required={!editUser}
                                    />
                                </label>
                            </div>
                            {error && <p className="error">{error}</p>}
                            <div className="form-actions">
                                <button
                                    type="button"
                                    className="cancel-btn"
                                    onClick={() => setShowModal(false)}
                                >İptal</button>
                                <button type="submit" className="submit-btn">
                                    {editUser ? 'Güncelle' : 'Ekle'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}
