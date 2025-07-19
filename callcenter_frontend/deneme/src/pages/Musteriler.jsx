import React, { useEffect, useState } from 'react';
import {
    getAllCustomers,
    createCustomer,
    updateCustomer,
    getAllCities,
    getDistrictsByCityId,
    getTownshipsByDistrictId,
    getNeighbourhoodsByTownshipId,
    getAddressByNeighbourhood,
    getAddressById,
} from '../services/api';
import { useAuth } from "../context/AuthContext";

export default function Musteriler() {
    const { getOperatorId } = useAuth();
    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [editCustomer, setEditCustomer] = useState(null);
    const [formData, setFormData] = useState({
        customerName: '',
        customerSurname: '',
        customerPhoneNumber: '',
        customerEmail: '',
        customerExplanation: '',
        customerActive: true,
        addressId: null,
        cityId: '',
        districtId: '',
        townshipId: '',
        neighbourhoodId: '',
        neighbourhoodExplanation: '',
    });
    const [cities, setCities] = useState([]);
    const [districts, setDistricts] = useState([]);
    const [townships, setTownships] = useState([]);
    const [neighbourhoods, setNeighbourhoods] = useState([]);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        fetchCustomers();
        fetchCities();
    }, []);

    const fetchCustomers = async () => {
        setLoading(true);
        setError('');
        try {
            const result = await getAllCustomers();
            let arr = [];
            if (Array.isArray(result)) arr = result;
            else if (Array.isArray(result?.data)) arr = result.data;
            else if (Array.isArray(result?.customers)) arr = result.customers;

            const customersWithId = arr.map(c => ({ ...c, id: c.customerId ?? c.id }));

            const customersWithAddress = await Promise.all(
                customersWithId.map(async (customer) => {
                    if (customer.addressId) {
                        try {
                            const response = await getAddressById(customer.addressId);
                            const address = response?.data || null;
                            return { ...customer, address };
                        } catch (err) {
                            return { ...customer, address: null };
                        }
                    } else {
                        return { ...customer, address: null };
                    }
                })
            );

            setCustomers(customersWithAddress);
        } catch (error) {
            setError('Müşteri verileri yüklenemedi.');
            setCustomers([]);
        }
        setLoading(false);
    };

    const fetchCities = async () => {
        try {
            const data = await getAllCities();
            setCities(data || []);
        } catch {
            setCities([]);
        }
    };

    const handleCityChange = async (e) => {
        const cityId = e.target.value;
        setFormData(prev => ({
            ...prev,
            cityId,
            districtId: '',
            townshipId: '',
            neighbourhoodId: '',
            addressId: null,
            neighbourhoodExplanation: ''
        }));
        setDistricts([]);
        setTownships([]);
        setNeighbourhoods([]);

        if (cityId) {
            try {
                const data = await getDistrictsByCityId(cityId);
                setDistricts(data || []);
            } catch {
                setDistricts([]);
            }
        }
    };

    const handleDistrictChange = async (e) => {
        const districtId = e.target.value;
        setFormData(prev => ({
            ...prev,
            districtId,
            townshipId: '',
            neighbourhoodId: '',
            addressId: null,
            neighbourhoodExplanation: ''
        }));
        setTownships([]);
        setNeighbourhoods([]);

        if (districtId) {
            try {
                const data = await getTownshipsByDistrictId(districtId);
                setTownships(data || []);
            } catch {
                setTownships([]);
            }
        }
    };

    const handleTownshipChange = async (e) => {
        const townshipId = e.target.value;
        setFormData(prev => ({
            ...prev,
            townshipId,
            neighbourhoodId: '',
            addressId: null,
            neighbourhoodExplanation: ''
        }));
        setNeighbourhoods([]);

        if (townshipId) {
            try {
                const data = await getNeighbourhoodsByTownshipId(townshipId);
                setNeighbourhoods(data || []);
            } catch {
                setNeighbourhoods([]);
            }
        }
    };

    const handleNeighbourhoodChange = async (e) => {
        const neighbourhoodId = e.target.value;
        setFormData(prev => ({ ...prev, neighbourhoodId }));

        if (neighbourhoodId) {
            try {
                const res = await getAddressByNeighbourhood(parseInt(neighbourhoodId));
                if (res?.data) {
                    const addr = res.data;
                    setFormData(prev => ({
                        ...prev,
                        addressId: addr.addressId,
                        cityId: addr.cityId.toString(),
                        districtId: addr.districtId.toString(),
                        townshipId: addr.townshipId.toString(),
                        neighbourhoodId: addr.neighbourhoodId.toString(),
                        neighbourhoodExplanation: addr.neighbourhoodExplanation || '',
                    }));

                    try {
                        const dists = await getDistrictsByCityId(addr.cityId);
                        setDistricts(dists || []);
                    } catch { setDistricts([]); }
                    try {
                        const towns = await getTownshipsByDistrictId(addr.districtId);
                        setTownships(towns || []);
                    } catch { setTownships([]); }
                    try {
                        const neighs = await getNeighbourhoodsByTownshipId(addr.townshipId);
                        setNeighbourhoods(neighs || []);
                    } catch { setNeighbourhoods([]); }
                } else {
                    setError('Adres bilgisi alınamadı.');
                    setFormData(prev => ({ ...prev, addressId: null }));
                }
            } catch {
                setError('Adres bilgisi alınamadı.');
                setFormData(prev => ({ ...prev, addressId: null }));
            }
        } else {
            setFormData(prev => ({ ...prev, addressId: null, neighbourhoodExplanation: '' }));
            setDistricts([]);
            setTownships([]);
            setNeighbourhoods([]);
        }
    };

    const openAddModal = () => {
        setEditCustomer(null);
        setFormData({
            customerName: '',
            customerSurname: '',
            customerPhoneNumber: '',
            customerEmail: '',
            customerExplanation: '',
            customerActive: true,
            addressId: null,
            cityId: '',
            districtId: '',
            townshipId: '',
            neighbourhoodId: '',
            neighbourhoodExplanation: '',
        });
        setError('');
        setDistricts([]);
        setTownships([]);
        setNeighbourhoods([]);
        setShowModal(true);
    };

    const openEditModal = (customer) => {
        setEditCustomer(customer);
        setFormData({
            customerName: customer.customerName,
            customerSurname: customer.customerSurname,
            customerPhoneNumber: customer.customerPhoneNumber,
            customerEmail: customer.customerEmail,
            customerExplanation: customer.customerExplanation,
            customerActive: customer.customerActive,
            addressId: customer.addressId || null,
            cityId: customer.address?.cityId?.toString() || '',
            districtId: customer.address?.districtId?.toString() || '',
            townshipId: customer.address?.townshipId?.toString() || '',
            neighbourhoodId: customer.address?.neighbourhoodId?.toString() || '',
            neighbourhoodExplanation: customer.address?.neighbourhoodExplanation || '',
        });
        setError('');
        setShowModal(true);
    };

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');

        if (!formData.customerName || !formData.customerSurname || !formData.customerPhoneNumber) {
            setError('Ad, Soyad ve Telefon zorunludur.');
            return;
        }

        if (!formData.addressId && !editCustomer) {
            setError('Lütfen bir adres seçiniz.');
            return;
        }

        try {
            // localStorage'dan operatör ID'sini al
            const storedOperatorId = localStorage.getItem('operatorId');
            // AuthContext'ten de operatör ID'sini al (yedek olarak)
            const contextOperatorId = getOperatorId();

            const submitData = {
                customerName: formData.customerName,
                customerSurname: formData.customerSurname,
                customerPhoneNumber: formData.customerPhoneNumber,
                customerEmail: formData.customerEmail,
                customerExplanation: formData.customerExplanation,
                customerActive: editCustomer ? formData.customerActive : true,
                addressId: formData.addressId,
                neighbourhoodExplanation: formData.neighbourhoodExplanation,
                operatorId: storedOperatorId || contextOperatorId || null, // Önce localStorage, sonra context
            };

            // Operatör ID kontrolü (isteğe bağlı)
            if (!submitData.operatorId) {
                console.warn('Operatör ID bulunamadı! İşlem null operatorId ile devam ediyor.');
            }

            if (editCustomer) {
                await updateCustomer(editCustomer.id, submitData);
                setSuccess('Müşteri başarıyla güncellendi.');
            } else {
                await createCustomer(submitData);
                setSuccess('Müşteri başarıyla eklendi.');
            }

            await fetchCustomers();
            setShowModal(false);
            setTimeout(() => setSuccess(''), 2500);
        } catch (err) {
            setError(err.response?.data?.message || 'Bir hata oluştu.');
        }
    };
    const handleDelete = async (id) => {
        if (!window.confirm('Bu müşteriyi pasif hale getirmek istediğinizden emin misiniz?')) return;

        try {
            const customerToUpdate = customers.find(c => c.id === id);
            if (!customerToUpdate) {
                setError('Müşteri bulunamadı.');
                return;
            }

            // localStorage'dan operatör ID'sini al
            const storedOperatorId = localStorage.getItem('operatorId');
            // AuthContext'ten de operatör ID'sini al (yedek olarak)
            const contextOperatorId = getOperatorId();

            const updatedData = {
                ...customerToUpdate,
                customerActive: false,
                operatorId: storedOperatorId || contextOperatorId || null, // Önce localStorage, sonra context
            };

            // Operatör ID kontrolü (isteğe bağlı)
            if (!updatedData.operatorId) {
                console.warn('Operatör ID bulunamadı! İşlem null operatorId ile devam ediyor.');
            }

            await updateCustomer(id, updatedData);
            setCustomers(prev => prev.map(c => (c.id === id ? { ...c, customerActive: false } : c)));
            setSuccess('Müşteri pasif hale getirildi.');
            setTimeout(() => setSuccess(''), 2000);
        } catch (err) {
            setError(err.response?.data?.message || 'Pasif hale getirme sırasında hata oluştu.');
        }
    };

    return (
        <div className="musteriler-container">
            <h1 className="musteriler-heading">Müşteriler</h1>
            <button className="add-customer-btn" onClick={openAddModal}>+ Yeni Müşteri Ekle</button>

            {error && <div className="error-message">{error}</div>}
            {success && <div className="success-message">{success}</div>}

            {loading ? <p>Yükleniyor...</p> : (
                <div className="customer-table-container">
                    <table className="customer-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Ad</th>
                                <th>Soyad</th>
                                <th>Telefon</th>
                                <th>E-posta</th>
                                <th>Adres</th>
                                <th>Aktif</th>
                                <th>İşlemler</th>
                            </tr>
                        </thead>
                        <tbody>
                            {customers.length > 0 ? customers.map(customer => (
                                <tr key={customer.id}>
                                    <td>{customer.id}</td>
                                    <td>{customer.customerName}</td>
                                    <td>{customer.customerSurname}</td>
                                    <td>{customer.customerPhoneNumber}</td>
                                    <td>{customer.customerEmail || '-'}</td>
                                    <td>
                                        {customer.address
                                            ? (
                                                customer.address.cityName + ' / ' +
                                                customer.address.districtName + ' / ' +
                                                customer.address.townshipName + ' / ' +
                                                customer.address.neighbourhoodName +
                                                (customer.address.neighbourhoodExplanation ? ' / ' + customer.address.neighbourhoodExplanation : '')
                                            )
                                            : '-'}
                                    </td>
                                    <td>{customer.customerActive ? 'Evet' : 'Hayır'}</td>
                                    <td>
                                        <button className="action-btn edit-btn" onClick={() => openEditModal(customer)}>Düzenle</button>
                                        <button className="action-btn delete-btn" onClick={() => handleDelete(customer.id)}>Sil</button>
                                    </td>
                                </tr>
                            )) : (
                                <tr><td colSpan={8}>Kayıtlı müşteri yok.</td></tr>
                            )}
                        </tbody>
                    </table>
                </div>
            )}

            {showModal && (
                <div className="modal-overlay" onClick={() => setShowModal(false)}>
                    <div className="modal-content" onClick={e => e.stopPropagation()}>
                        <div className="modal-header">
                            <h2>{editCustomer ? 'Müşteri Düzenle' : 'Yeni Müşteri Ekle'}</h2>
                            <button className="close-modal" onClick={() => setShowModal(false)}>&times;</button>
                        </div>
                        <form onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label>Ad
                                    <input
                                        name="customerName"
                                        value={formData.customerName}
                                        onChange={handleChange}
                                        required
                                    />
                                </label>
                            </div>
                            <div className="form-group">
                                <label>Soyad
                                    <input
                                        name="customerSurname"
                                        value={formData.customerSurname}
                                        onChange={handleChange}
                                        required
                                    />
                                </label>
                            </div>
                            <div className="form-group">
                                <label>Telefon
                                    <input
                                        name="customerPhoneNumber"
                                        value={formData.customerPhoneNumber}
                                        onChange={handleChange}
                                        required
                                    />
                                </label>
                            </div>
                            <div className="form-group">
                                <label>E-posta
                                    <input
                                        type="email"
                                        name="customerEmail"
                                        value={formData.customerEmail}
                                        onChange={handleChange}
                                    />
                                </label>
                            </div>
                            <div className="form-group">
                                <label>Açıklama
                                    <textarea
                                        name="customerExplanation"
                                        value={formData.customerExplanation}
                                        onChange={handleChange}
                                    />
                                </label>
                            </div>
                            <div className="form-group">
                                <label>Şehir
                                    <select
                                        value={formData.cityId || ''}
                                        onChange={handleCityChange}
                                    >
                                        <option value="">Seçiniz</option>
                                        {cities.map(city => (
                                            <option key={city.cityId} value={city.cityId}>{city.cityName}</option>
                                        ))}
                                    </select>
                                </label>
                            </div>
                            <div className="form-group">
                                <label>İlçe
                                    <select
                                        value={formData.districtId || ''}
                                        onChange={handleDistrictChange}
                                        disabled={!formData.cityId}
                                    >
                                        <option value="">Seçiniz</option>
                                        {districts.map(d => (
                                            <option key={d.districtId} value={d.districtId}>{d.districtName}</option>
                                        ))}
                                    </select>
                                </label>
                            </div>
                            <div className="form-group">
                                <label>Bucak
                                    <select
                                        value={formData.townshipId || ''}
                                        onChange={handleTownshipChange}
                                        disabled={!formData.districtId}
                                    >
                                        <option value="">Seçiniz</option>
                                        {townships.map(t => (
                                            <option key={t.townshipId} value={t.townshipId}>{t.townshipName}</option>
                                        ))}
                                    </select>
                                </label>
                            </div>
                            <div className="form-group">
                                <label>Mahalle
                                    <select
                                        name="neighbourhoodId"
                                        value={formData.neighbourhoodId || ''}
                                        onChange={handleNeighbourhoodChange}
                                        disabled={!formData.townshipId}
                                    >
                                        <option value="">Seçiniz</option>
                                        {neighbourhoods.map(n => (
                                            <option key={n.neighbourhoodId} value={n.neighbourhoodId}>{n.neighbourhoodName}</option>
                                        ))}
                                    </select>
                                </label>
                            </div>
                            <div className="form-group">
                                <label>Mahalle Açıklaması
                                    <input
                                        type="text"
                                        name="neighbourhoodExplanation"
                                        value={formData.neighbourhoodExplanation}
                                        onChange={handleChange}
                                    />
                                </label>
                            </div>
                            {error && <div className="error-message">{error}</div>}
                            <div className="form-actions">
                                <button type="button" className="cancel-btn" onClick={() => setShowModal(false)}>İptal</button>
                                <button type="submit" className="submit-btn">{editCustomer ? 'Güncelle' : 'Ekle'}</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}