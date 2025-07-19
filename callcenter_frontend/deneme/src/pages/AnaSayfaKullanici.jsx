import React, { useEffect, useState } from 'react';
import {
    getAllCalls,
    getAllCustomers,
    getCallDetails,
    getOperatorStatsByEmail
} from '../services/api';
import { useAuth } from '../context/AuthContext';
import './AnaSayfa.css';

export default function AnaSayfaKullanici() {
    const { user } = useAuth();
    const [callsCount, setCallsCount] = useState(0);
    const [customersCount, setCustomersCount] = useState(0);
    const [operatorStats, setOperatorStats] = useState({ callCount: 0, customerCount: 0 });
    const [serviceTypes, setServiceTypes] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function fetchStats() {
            setLoading(true);
            try {
                const [calls, customers, stats] = await Promise.all([
                    getAllCalls(),
                    getAllCustomers(),
                    user?.email ? getOperatorStatsByEmail(user.email) : Promise.resolve({ callCount: 0, customerCount: 0 })
                ]);

                setCallsCount(Array.isArray(calls?.data) ? calls.data.length : (Array.isArray(calls) ? calls.length : 0));
                setCustomersCount(Array.isArray(customers?.data) ? customers.data.length : (Array.isArray(customers) ? customers.length : 0));

                // Operatöre özel istatistikler
                if (stats) {
                    setOperatorStats({
                        callCount: stats.callCount || 0,
                        customerCount: stats.customerCount || 0
                    });
                }


            } catch (error) {
                console.error("İstatistikler alınırken hata:", error);
                setCallsCount(0);
                setCustomersCount(0);
                setOperatorStats({ callCount: 0, customerCount: 0 });
                setServiceTypes([]);
            }
            setLoading(false);
        }

        fetchStats();

        // Yeni çağrı oluşturulduğunda istatistikleri yenile
        const handleCallCreated = async (e) => {
            if (user?.email && e.detail.operatorId === user.userId) {
                try {
                    // Tüm istatistikleri tek seferde güncelle
                    const [stats, calls, customers] = await Promise.all([
                        getOperatorStatsByEmail(user.email),
                        getAllCalls(),
                        getAllCustomers()
                    ]);

                    setOperatorStats({
                        callCount: stats.callCount || 0,
                        customerCount: stats.customerCount || 0
                    });

                    setCallsCount(Array.isArray(calls?.data) ? calls.data.length : (Array.isArray(calls) ? calls.length : 0));
                    setCustomersCount(Array.isArray(customers?.data) ? customers.data.length : (Array.isArray(customers) ? customers.length : 0));

                    // Hizmet türlerini de güncelle (isteğe bağlı)
                    let fetchedCalls = Array.isArray(calls?.data) ? calls.data : (Array.isArray(calls) ? calls : []);
                    if (fetchedCalls.length > 0) {
                        const uniqueServiceTypes = new Set();
                        await Promise.all(
                            fetchedCalls.slice(0, 10).map(async (call) => {
                                try {
                                    const details = await getCallDetails(call.callId || call.id);
                                    if (details?.serviceType) {
                                        uniqueServiceTypes.add(details.serviceType);
                                    }
                                } catch (error) {
                                    console.error("Hizmet türü detayı alınamadı:", error);
                                }
                            })
                        );
                        setServiceTypes(Array.from(uniqueServiceTypes));
                    }
                } catch (error) {
                    console.error("İstatistik güncelleme hatası:", error);
                }
            }
        };

        window.addEventListener('callCreated', handleCallCreated);

        return () => {
            window.removeEventListener('callCreated', handleCallCreated);
        };
    }, [user?.email, user?.userId]);

    return (
        <div className="anasayfa-container">
            <h1 className="anasayfa-title">
                Hoş geldiniz{user?.email ? `, ${user.email}` : ''}!
            </h1>
            <p className="anasayfa-desc">
                Çağrı merkezi yönetim paneline hoş geldiniz.
                Sol menüden işlemlere erişebilirsiniz.
            </p>

            <div className="dashboard-stats">
                {/* Genel İstatistikler */}
                <div className="stat-card">
                    <div className="stat-label">Toplam Çağrı</div>
                    <div className="stat-value">{loading ? '...' : callsCount}</div>
                </div>
                <div className="stat-card">
                    <div className="stat-label">Toplam Müşteri</div>
                    <div className="stat-value">{loading ? '...' : customersCount}</div>
                </div>

                {/* Operatöre Özel İstatistikler */}
                {user?.email && (
                    <>
                        <div className="stat-card operator-stat">
                            <div className="stat-label">Operatörün Çağrıları</div>
                            <div className="stat-value">{loading ? '...' : operatorStats.callCount}</div>
                        </div>
                        <div className="stat-card operator-stat">
                            <div className="stat-label">Operatörün Görüştüğü Müşteriler</div>
                            <div className="stat-value">{loading ? '...' : operatorStats.customerCount}</div>
                        </div>
                    </>
                )}

                <div className="anasayfa-info">
                    <h2>Yapabilecekleriniz</h2>
                    <ul>
                        <li>Müşteri kaydı ekleyebilir ve düzenleyebilirsiniz.</li>
                        <li>Kullanıcı/operatör yönetimi yapabilirsiniz.</li>
                        <li>Arama kayıtlarını ve çağrı geçmişini görüntüleyebilirsiniz.</li>
                        <li>Yeni çağrı kaydı ekleyip sistemde tutabilirsiniz.</li>
                    </ul>
                </div>
            </div>
        </div>
    );
}