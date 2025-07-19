import React, { useEffect, useState } from 'react';
import { getAllCalls, getAllCustomers, getAllOperators, getOperatorCustomerStatsByEmail } from '../services/api';
import { useAuth } from '../context/AuthContext';
import './AnaSayfa.css';
import {
    CagriHacmiGrafigi,
    OrtalamaCagriSuresiGrafigi,
    IlkCagriCozumOraniGrafigi,
    MusteriMemnuniyetiGrafigi,
    TemsilciPerformansGrafigi,
    BeklemeSuresiGrafigi
} from '../components/charts';

export default function AnaSayfa() {
    const { user } = useAuth();
    const [callsCount, setCallsCount] = useState(0);
    const [customersCount, setCustomersCount] = useState(0);
    const [operatorsCount, setOperatorsCount] = useState(0);
    const [operatorCustomerStats, setOperatorCustomerStats] = useState([]);
    const [loading, setLoading] = useState(true);
    const [statsLoading, setStatsLoading] = useState(true);

    useEffect(() => {
        async function fetchStats() {
            setLoading(true);
            try {
                const [calls, customers, operators] = await Promise.all([
                    getAllCalls(),
                    getAllCustomers(),
                    getAllOperators(),
                ]);
                setCallsCount(Array.isArray(calls?.data) ? calls.data.length : calls.length || 0);
                setCustomersCount(Array.isArray(customers?.data) ? customers.data.length : customers.length || 0);
                setOperatorsCount(Array.isArray(operators?.data) ? operators.data.length : operators.length || 0);
            } catch {
                setCallsCount(0);
                setCustomersCount(0);
                setOperatorsCount(0);
            }
            setLoading(false);
        }
        fetchStats();
    }, []);

    useEffect(() => {
        async function fetchOperatorCustomerStats() {
            setStatsLoading(true);
            try {
                // Tüm operatörlerin müşteri istatistiklerini al
                const operators = await getAllOperators();
                const operatorList = Array.isArray(operators?.data) ? operators.data : operators;

                // Her operatör için istatistikleri al
                const statsPromises = operatorList.map(async (operator) => {
                    try {
                        const stats = await getOperatorCustomerStatsByEmail(operator.operatorEmail);
                        return {
                            operatorName: operator.operatorName,
                            operatorSurname: operator.operatorSurname,
                            operatorEmail: operator.operatorEmail,
                            customerCount: stats.customerCount || 0,
                            lastAddedCustomers: stats.lastAddedCustomers || []
                        };
                    } catch (error) {
                        return {
                            operatorName: operator.operatorName,
                            operatorSurname: operator.operatorSurname,
                            operatorEmail: operator.operatorEmail,
                            customerCount: 0,
                            lastAddedCustomers: []
                        };
                    }
                });

                const statsResults = await Promise.all(statsPromises);
                setOperatorCustomerStats(statsResults);
            } catch (error) {
                console.error("Operatör müşteri istatistikleri alınamadı:", error);
                setOperatorCustomerStats([]);
            }
            setStatsLoading(false);
        }

        fetchOperatorCustomerStats();
    }, []);

    // Tarih formatlama fonksiyonu
    const formatDate = (dateString) => {
        if (!dateString) return '-';
        const date = new Date(dateString);
        return date.toLocaleDateString('tr-TR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    return (
        <div className="anasayfa-container">
            <h1 className="anasayfa-title">
                {`Hoş geldiniz${user?.email ? ' ' + user.email : ''}!`}
            </h1>
            <p className="anasayfa-desc">
                Çağrı merkezi yönetim paneline hoş geldiniz.
                Sol menüden işlemlere erişebilirsiniz.
            </p>

            <div className="dashboard-stats">
                <div className="stat-card">
                    <div className="stat-label">Toplam Çağrı</div>
                    <div className="stat-value">{loading ? '...' : callsCount}</div>
                </div>
                <div className="stat-card">
                    <div className="stat-label">Toplam Müşteri</div>
                    <div className="stat-value">{loading ? '...' : customersCount}</div>
                </div>
                <div className="stat-card">
                    <div className="stat-label">Toplam Kullanıcı</div>
                    <div className="stat-value">{loading ? '...' : operatorsCount}</div>
                </div>
            </div>


            <div className="operator-customer-section">
                <h2>Operatör Müşteri İlişkileri</h2>
                {statsLoading ? (
                    <p>Yükleniyor...</p>
                ) : (
                    <div className="operator-customer-table">
                        {operatorCustomerStats.map((operator, index) => (
                            <div key={index} className="operator-card">
                                <h3>{operator.operatorName} {operator.operatorSurname}</h3>
                                <p><strong>E-posta:</strong> {operator.operatorEmail}</p>
                                <p><strong>Toplam Müşteri:</strong> {operator.customerCount}</p>

                                {operator.lastAddedCustomers.length > 0 ? (
                                    <>
                                        <p><strong>Son Eklenenler:</strong></p>
                                        <table>
                                            <thead>
                                                <tr>
                                                    <th>Müşteri</th>
                                                    <th>Telefon</th>
                                                    <th>Tarih</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {operator.lastAddedCustomers.map((customer, custIndex) => (
                                                    <tr key={custIndex}>
                                                        <td>{customer.customerName} {customer.customerSurname}</td>
                                                        <td>{customer.customerPhoneNumber}</td>
                                                        <td>{formatDate(customer.createdAt)}</td>
                                                    </tr>
                                                ))}
                                            </tbody>
                                        </table>
                                    </>
                                ) : (
                                    <p className="no-customer-message">Bu operatör tarafından eklenen müşteri bulunamadı.</p>
                                )}
                            </div>
                        ))}
                    </div>
                )}
            </div>

            <div className="charts-section">
                <h2>Haftalık Raporlar</h2>
                <CagriHacmiGrafigi />
                <OrtalamaCagriSuresiGrafigi />
                <IlkCagriCozumOraniGrafigi />
                <MusteriMemnuniyetiGrafigi />
                <TemsilciPerformansGrafigi />
                <BeklemeSuresiGrafigi />
            </div>
        </div>
    );
}