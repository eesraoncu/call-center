import React, { useEffect, useState, useRef } from 'react';
import {
    getAllCalls,
    createCall,
    updateCall,
    deleteCall,
    getAllCustomers,
    getAllServices,
    getServiceById,
    linkOperatorCustomerCall
} from '../services/api';
import { useAuth } from "../context/AuthContext";
import './CagriMerkezi.css';

export default function CagriMerkezi() {
    const { user } = useAuth();
    const [calls, setCalls] = useState([]);
    const [customers, setCustomers] = useState([]);
    const [services, setServices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [error, setError] = useState('');
    const [creating, setCreating] = useState(false);

    const [simState, setSimState] = useState({
        started: false,
        startTime: null,
        duration: 0,
    });
    const timerRef = useRef(null);

    const [selectedId, setSelectedId] = useState(null);

    const [formData, setFormData] = useState({
        callDate: new Date().toISOString().slice(0, 10),
        callTime: '00:00:00',
        callDuration: 0,
        customerId: '',
        serviceId: ''
    });

    useEffect(() => {
        fetchCalls();
        fetchCustomers();
        fetchServices();
        return () => clearInterval(timerRef.current);
    }, []);

    const fetchCalls = async () => {
        setLoading(true);
        setError('');
        try {
            const callsRes = await getAllCalls();
            setCalls(Array.isArray(callsRes) ? callsRes : []);
        } catch (e) {
            setError('Çağrılar alınamadı!');
            setCalls([]);
        }
        setLoading(false);
    };

    const fetchCustomers = async () => {
        try {
            const customersRes = await getAllCustomers();
            setCustomers(Array.isArray(customersRes) ? customersRes : []);
        } catch {
            setCustomers([]);
        }
    };

    const fetchServices = async () => {
        try {
            const servicesRes = await getAllServices();
            setServices(Array.isArray(servicesRes) ? servicesRes : servicesRes.data || []);
        } catch {
            setServices([]);
        }
    };

    const handleSimulateCall = () => {
        if (!simState.started) {
            setSimState({
                started: true,
                startTime: Date.now(),
                duration: 0,
            });
            timerRef.current = setInterval(() => {
                setSimState(s => ({ ...s, duration: Math.floor((Date.now() - s.startTime) / 1000) }));
            }, 1000);
        } else {
            clearInterval(timerRef.current);
            const duration = Math.floor((Date.now() - simState.startTime) / 1000);
            setSimState({ started: false, startTime: null, duration: 0 });
            setFormData({
                callDate: new Date().toISOString().slice(0, 10),
                callTime: new Date().toISOString().slice(11, 19),
                callDuration: duration,
                customerId: '',
                serviceId: ''
            });
            setShowModal(true);
        }
    };

    const handleCreate = async (e) => {
        e.preventDefault();
        setCreating(true);
        setError('');
        try {
            if (!formData.customerId || !formData.serviceId) {
                setError("Lütfen müşteri ve servis seçin!");
                setCreating(false);
                return;
            }

            const newCall = await createCall({
                ...formData,
                customerId: Number(formData.customerId),
                serviceId: Number(formData.serviceId),
            });

            if (user?.userId && formData.customerId && newCall?.callId) {
                await linkOperatorCustomerCall({
                    operatorId: user.userId,
                    customerId: Number(formData.customerId),
                    callId: newCall.callId
                });

                window.dispatchEvent(new CustomEvent('callCreated', {
                    detail: {
                        operatorId: user.userId,
                        callId: newCall.callId
                    }
                }));
            }

            setShowModal(false);
            setFormData({
                callDate: new Date().toISOString().slice(0, 10),
                callTime: '00:00:00',
                callDuration: 0,
                customerId: '',
                serviceId: ''
            });
            await fetchCalls();
        } catch (err) {
            setError('Çağrı eklenemedi!\n' + (err?.response?.data?.message || err.message));
        }
        setCreating(false);
    };

    const handleSelect = (id) => {
        setSelectedId(id === selectedId ? null : id);
    };

    const handleDelete = async (callId) => {
        if (!window.confirm("Bu çağrıyı silmek istediğinize emin misiniz?")) return;
        try {
            await deleteCall(callId);
            setCalls(calls => calls.filter(c => c.callId !== callId));
            if (selectedId === callId) setSelectedId(null);
        } catch {
            setError('Silme işlemi sırasında hata oluştu.');
        }
    };

    const selectedCall = calls.find(c => c.callId === selectedId);

    const formatDuration = (seconds) => {
        if (!seconds) return '0 sn';
        const m = Math.floor(seconds / 60);
        const s = seconds % 60;
        return m ? `${m} dk ${s} sn` : `${s} sn`;
    };

    const getCustomerName = (customerId) => {
        const customer = customers.find(c => String(c.customerId) === String(customerId));
        return customer?.customerName || customer?.name || `ID: ${customerId}`;
    };

    const getServiceName = (serviceId) => {
        const service = services.find(s => String(s.serviceId) === String(serviceId));
        return service ? `${service.serviceType} (${service.serviceExplanation || ''})` : `ID: ${serviceId}`;
    };

    return (
        <div className="cagrimerkezi-page">
            <aside className="calls-list-container">
                <h2 style={{ color: "#FFD600", letterSpacing: "1px" }}>Gelen Çağrılar</h2>
                <button
                    onClick={handleSimulateCall}
                    className="submit-btn"
                    style={{
                        marginBottom: 12,
                        background: simState.started ? "#eb5c31" : "#1d3557",
                        color: "#fff",
                        fontWeight: "bold"
                    }}
                >
                    {simState.started ? "Çağrıyı Bitir ve Kaydet" : "Yeni Çağrı Simüle Et"}
                </button>
                {simState.started && (
                    <div style={{
                        marginBottom: 16,
                        color: "#FFD600",
                        fontWeight: "bold",
                        fontSize: 18,
                        textAlign: "center"
                    }}>
                        Süre: {simState.duration} sn
                    </div>
                )}
                {error && <div className="error-message">{error}</div>}
                <ul className="calls-list-ul">
                    {loading
                        ? <li>Yükleniyor...</li>
                        : (calls.length === 0
                            ? <li>Hiç çağrı yok.</li>
                            : calls.map(c => (
                                <li
                                    key={c.callId}
                                    className={`calls-list-li${selectedId === c.callId ? " selected" : ""}`}
                                    onClick={() => handleSelect(c.callId)}
                                >
                                    <span>
                                        {c.callDate} - {formatDuration(c.callDuration)}
                                    </span>
                                    <button
                                        className="delete-call-btn"
                                        onClick={e => {
                                            e.stopPropagation();
                                            handleDelete(c.callId);
                                        }}
                                        title="Çağrıyı Sil"
                                    >🗑️</button>
                                </li>
                            ))
                        )
                    }
                </ul>
            </aside>
            <section className="call-details-container">
                {selectedCall ? (
                    <div className="call-details-card">
                        <h3>Çağrı Detayları</h3>
                        <div><b>Tarih:</b> {selectedCall.callDate}</div>
                        <div><b>Zaman:</b> {selectedCall.callTime}</div>
                        <div><b>Süre:</b> {formatDuration(selectedCall.callDuration)}</div>
                        <div>
                            <b>Müşteri:</b> {getCustomerName(selectedCall.customerId)}
                        </div>
                        <div>
                            <b>Servis:</b> {getServiceName(selectedCall.serviceId)}
                        </div>
                    </div>
                ) : (
                    <p className="placeholder">Soldan bir çağrı seçin.</p>
                )}
            </section>
            {showModal && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h2>Yeni Çağrı Oluştur</h2>
                            <button
                                className="close-modal"
                                onClick={() => {
                                    setShowModal(false);
                                    setError('');
                                }}
                            >
                                ×
                            </button>
                        </div>
                        <form onSubmit={handleCreate}>
                            <div className="form-group">
                                <label>Çağrı Tarihi</label>
                                <input
                                    name="callDate"
                                    type="date"
                                    value={formData.callDate}
                                    onChange={e => setFormData(f => ({ ...f, callDate: e.target.value }))}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Çağrı Zamanı</label>
                                <input
                                    name="callTime"
                                    type="time"
                                    value={formData.callTime.slice(0, 5)}
                                    onChange={e => setFormData(f => ({ ...f, callTime: e.target.value + ':00' }))}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Çağrı Süresi (saniye)</label>
                                <input
                                    name="callDuration"
                                    type="number"
                                    min={0}
                                    value={formData.callDuration}
                                    onChange={e => setFormData(f => ({ ...f, callDuration: Number(e.target.value) }))}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Müşteri Seç</label>
                                <select
                                    name="customerId"
                                    value={formData.customerId}
                                    onChange={e => setFormData(f => ({ ...f, customerId: e.target.value }))}
                                    required
                                >
                                    <option value="">Müşteri seçin...</option>
                                    {customers.map(c => (
                                        <option key={c.customerId} value={c.customerId}>
                                            {c.customerName || c.name || `Müşteri #${c.customerId}`}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-group">
                                <label>Servis Seç</label>
                                <select
                                    name="serviceId"
                                    value={formData.serviceId}
                                    onChange={e => setFormData(f => ({ ...f, serviceId: e.target.value }))}
                                    required
                                >
                                    <option value="">Servis seçin...</option>
                                    {services.map(s => (
                                        <option key={s.serviceId} value={s.serviceId}>
                                            {s.serviceType} ({s.serviceExplanation})
                                        </option>
                                    ))}
                                </select>
                            </div>
                            {error && <div className="error-message">{error}</div>}
                            <div className="form-actions">
                                <button
                                    type="button"
                                    className="cancel-btn"
                                    onClick={() => {
                                        setShowModal(false);
                                        setError('');
                                    }}
                                    disabled={creating}
                                >
                                    İptal
                                </button>
                                <button type="submit" className="submit-btn" disabled={creating}>
                                    {creating ? 'Ekleniyor...' : 'Ekle'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}