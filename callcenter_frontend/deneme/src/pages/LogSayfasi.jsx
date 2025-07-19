import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { Navigate } from 'react-router-dom';
import { getAllOperators, fetchLogs } from '../services/api';
import './LogSayfasi.css';

export default function LogSayfasi() {
    const { user, loading: authLoading } = useAuth();
    const [allLogs, setAllLogs] = useState([]);
    const [filteredLogs, setFilteredLogs] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [showLogCount, setShowLogCount] = useState(true);
    const [showAllLogs, setShowAllLogs] = useState(false);
    const [filters, setFilters] = useState({
        operatorId: '',
        search: '',
        startDate: '',
        endDate: '',
        method: ''
    });
    const [currentPage, setCurrentPage] = useState(1);
    const [logsPerPage] = useState(10);
    const [operators, setOperators] = useState([]);

    const httpMethods = [
        { value: '', label: 'Tüm Methodlar' },
        { value: 'GET', label: 'GET' },
        { value: 'POST', label: 'POST' },
        { value: 'PUT', label: 'PUT' },
        { value: 'DELETE', label: 'DELETE' },
        { value: 'PATCH', label: 'PATCH' },
        { value: 'HEAD', label: 'HEAD' },
        { value: 'OPTIONS', label: 'OPTIONS' }
    ];

    // Auth kontrolü
    if (authLoading) return <div>Yükleniyor...</div>;
    if (!user || user.role?.toLowerCase() !== 'admin') {
        return <Navigate to="/" replace />;
    }

    useEffect(() => {
        async function fetchInitialData() {
            try {
                const operatorsResponse = await getAllOperators();
                const operatorsData = Array.isArray(operatorsResponse?.data)
                    ? operatorsResponse.data
                    : operatorsResponse || [];
                setOperators(operatorsData);

                // Başlangıçta tüm logları çek
                await fetchLogData();
            } catch (error) {
                console.error("Veri alınırken hata:", error);
                setError(error.message);
            } finally {
                setLoading(false);
            }
        }
        fetchInitialData();
    }, []);

    // Filtreler değiştiğinde logları yeniden filtrele
    useEffect(() => {
        if (allLogs.length > 0) {
            applyFilters();
        }
    }, [filters, showAllLogs, allLogs]);

    const fetchLogData = async () => {
        try {
            setLoading(true);
            const params = {
                operatorId: filters.operatorId ? Number(filters.operatorId) : undefined,
                startDate: filters.startDate || undefined,
                endDate: filters.endDate || undefined,
                search: filters.search || undefined,
                method: filters.method || undefined
            };

            const logsResponse = await fetchLogs(params);
            const logs = Array.isArray(logsResponse?.data) ? logsResponse.data : logsResponse || [];

            setAllLogs(logs);
            setCurrentPage(1);
        } catch (error) {
            console.error("Loglar alınırken hata:", error);
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    const applyFilters = () => {
        let filtered = [...allLogs];

        // Operatör filtrelemesi (type-safe karşılaştırma)
        if (filters.operatorId) {
            filtered = filtered.filter(log =>
                Number(log.operatorId) === Number(filters.operatorId)
            );
        }

        // Method filtrelemesi
        if (filters.method) {
            filtered = filtered.filter(log => log.method === filters.method);
        }

        // Tarih filtrelemesi
        if (filters.startDate) {
            const startDate = new Date(filters.startDate);
            filtered = filtered.filter(log => new Date(log.logDatetime) >= startDate);
        }

        if (filters.endDate) {
            const endDate = new Date(filters.endDate);
            endDate.setHours(23, 59, 59, 999);
            filtered = filtered.filter(log => new Date(log.logDatetime) <= endDate);
        }

        // Arama metni filtrelemesi
        if (filters.search) {
            const searchTerm = filters.search.toLowerCase();
            filtered = filtered.filter(log =>
                log.logDescription?.toLowerCase().includes(searchTerm) ||
                log.method?.toLowerCase().includes(searchTerm) ||
                log.uri?.toLowerCase().includes(searchTerm) ||
                String(log.responseStatus).includes(searchTerm)
            );
        }

        // Hata logları filtrelemesi
        if (!showAllLogs) {
            filtered = filtered.filter(log => log.responseStatus >= 400 && log.responseStatus < 600);

            if (filters.search && filters.search.toLowerCase().includes('error')) {
                filtered = filtered.filter(log => log.responseStatus >= 400 && log.responseStatus < 600);
            }
        }

        setFilteredLogs(filtered);
    };

    const handleFilterChange = (e) => {
        const { name, value } = e.target;
        setFilters(prev => ({
            ...prev,
            [name]: name === 'operatorId' ? (value === '' ? '' : Number(value)) : value
        }));
    };

    const handleFilterSubmit = (e) => {
        e.preventDefault();
        fetchLogData();
    };

    const toggleLogCount = () => {
        setShowLogCount(!showLogCount);
    };

    const toggleLogType = () => {
        setShowAllLogs(!showAllLogs);
    };

    const formatDateTime = (dateString) => {
        const options = {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        };
        return new Date(dateString).toLocaleString('tr-TR', options);
    };

    const getOperatorName = (operatorId) => {
        if (!operatorId) return 'Sistem';
        const operator = operators.find(op =>
            Number(op.operatorId) === Number(operatorId)
        );
        return operator ? `${operator.operatorName} ${operator.operatorSurname}` : `Operatör ID: ${operatorId}`;
    };

    // Pagination variables
    const indexOfLastLog = currentPage * logsPerPage;
    const indexOfFirstLog = indexOfLastLog - logsPerPage;
    const paginatedLogs = filteredLogs.slice(indexOfFirstLog, indexOfLastLog);
    const totalPages = Math.ceil(filteredLogs.length / logsPerPage);
    const errorLogsCount = allLogs.filter(log => log.responseStatus >= 400 && log.responseStatus < 600).length;

    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    const getPaginationRange = () => {
        const maxVisiblePages = 5;
        let startPage = Math.max(1, currentPage - Math.floor(maxVisiblePages / 2));
        let endPage = startPage + maxVisiblePages - 1;

        if (endPage > totalPages) {
            endPage = totalPages;
            startPage = Math.max(1, endPage - maxVisiblePages + 1);
        }

        return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
    };

    if (error) {
        return (
            <div className="error-message">
                <h2>Hata oluştu</h2>
                <p>{error}</p>
                <p>Lütfen sayfayı yenileyin veya daha sonra tekrar deneyin.</p>
            </div>
        );
    }

    return (
        <div className="log-sayfasi-container">
            <h1>Log Kayıtları</h1>
            <p>
                Bu sayfada sistem log kayıtlarını görüntüleyebilir ve filtreleyebilirsiniz.
                {showAllLogs ? ' Şu anda TÜM loglar gösteriliyor.' : ' Şu anda sadece HATA logları gösteriliyor.'}
                {filters.operatorId && ` - Filtrelenmiş Operatör: ${getOperatorName(filters.operatorId)}`}
            </p>

            <div className="log-toggle-section">
                <button
                    onClick={toggleLogType}
                    className={`toggle-button ${showAllLogs ? 'active' : ''}`}
                >
                    {showAllLogs ? 'Sadece Hataları Göster' : 'Tüm Logları Göster'}
                </button>

                <button onClick={toggleLogCount} className="log-count-button">
                    {showLogCount ? 'Log Sayısını Gizle' : 'Log Sayısını Göster'}
                </button>

                {showLogCount && (
                    <div className="log-count-display">
                        {showAllLogs ? (
                            <>Toplam Log Sayısı: <strong>{allLogs.length}</strong> (Hatalı: {errorLogsCount})</>
                        ) : (
                            <>Toplam Hatalı Log Sayısı: <strong>{filteredLogs.length}</strong></>
                        )}
                        {filters.operatorId && (
                            <span className="operator-filter-info">
                                - Operatör: {getOperatorName(filters.operatorId)}
                            </span>
                        )}
                    </div>
                )}
            </div>

            <form className="filter-section" onSubmit={handleFilterSubmit}>
                <div className="filter-row">
                    <div className="filter-group">
                        <label htmlFor="operatorId">Operatör:</label>
                        <select
                            id="operatorId"
                            name="operatorId"
                            value={filters.operatorId}
                            onChange={handleFilterChange}
                            key={`operator-select-${filters.operatorId}`}
                        >
                            <option value="">Tüm Operatörler</option>
                            {operators.map(operator => (
                                <option
                                    key={operator.operatorId}
                                    value={operator.operatorId}
                                >
                                    {operator.operatorName} {operator.operatorSurname} (ID: {operator.operatorId})
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="filter-group">
                        <label htmlFor="method">Method:</label>
                        <select
                            id="method"
                            name="method"
                            value={filters.method}
                            onChange={handleFilterChange}
                        >
                            {httpMethods.map(method => (
                                <option key={method.value} value={method.value}>
                                    {method.label}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="filter-group">
                        <label htmlFor="search">Ara:</label>
                        <input
                            type="text"
                            id="search"
                            name="search"
                            placeholder="Log açıklaması, method, URI veya durum kodu ara..."
                            value={filters.search}
                            onChange={handleFilterChange}
                        />
                    </div>
                </div>

                <div className="filter-row">
                    <div className="filter-group">
                        <label htmlFor="startDate">Başlangıç Tarihi:</label>
                        <input
                            type="date"
                            id="startDate"
                            name="startDate"
                            value={filters.startDate}
                            onChange={handleFilterChange}
                        />
                    </div>

                    <div className="filter-group">
                        <label htmlFor="endDate">Bitiş Tarihi:</label>
                        <input
                            type="date"
                            id="endDate"
                            name="endDate"
                            value={filters.endDate}
                            onChange={handleFilterChange}
                        />
                    </div>

                    <button
                        type="submit"
                        className="filter-button"
                        onClick={() => {
                            fetchLogData();
                            applyFilters();
                        }}
                    >
                        Filtrele
                    </button>
                </div>
            </form>

            {loading ? (
                <p>Yükleniyor...</p>
            ) : (
                <div className="log-list">
                    {filteredLogs.length > 0 ? (
                        <>
                            <div className="results-count">
                                Toplam {filteredLogs.length} kayıt bulundu
                                {showAllLogs && ` (Hatalı: ${errorLogsCount})`}
                                {filters.operatorId && ` - Operatör: ${getOperatorName(filters.operatorId)}`}
                                {filters.method && ` - Method: ${filters.method}`}
                                {filteredLogs.length > logsPerPage && (
                                    <span className="page-info">
                                        - Sayfa {currentPage}/{totalPages}
                                    </span>
                                )}
                            </div>
                            <table className="log-table">
                                <thead>
                                    <tr>
                                        <th>Tarih/Saat</th>
                                        <th>Operatör</th>
                                        <th>Açıklama</th>
                                        <th>Method</th>
                                        <th>URI</th>
                                        <th className="status-header">Durum Kodu</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {paginatedLogs.map((log) => (
                                        <tr
                                            key={log.logId}
                                            className={`status-${Math.floor(log.responseStatus / 100)}xx 
                                                ${log.responseStatus >= 400 && log.responseStatus < 600 ? 'error-log' : ''}`}
                                        >
                                            <td>{formatDateTime(log.logDatetime)}</td>
                                            <td>{getOperatorName(log.operatorId)}</td>
                                            <td>{log.logDescription}</td>
                                            <td>{log.method}</td>
                                            <td>{log.uri}</td>
                                            <td className={`status-code ${log.responseStatus >= 500 ? 'server-error' :
                                                log.responseStatus >= 400 ? 'client-error' : 'success'
                                                }`}>
                                                {log.responseStatus}
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>

                            {filteredLogs.length > logsPerPage && (
                                <div className="pagination">
                                    <button
                                        className="page-button"
                                        onClick={() => paginate(currentPage - 1)}
                                        disabled={currentPage === 1}
                                    >
                                        Önceki
                                    </button>

                                    {currentPage > 3 && totalPages > 5 && (
                                        <>
                                            <button className="page-button" onClick={() => paginate(1)}>1</button>
                                            {currentPage > 4 && <span className="page-ellipsis">...</span>}
                                        </>
                                    )}

                                    {getPaginationRange().map(number => (
                                        <button
                                            key={number}
                                            className={`page-button ${currentPage === number ? 'active' : ''}`}
                                            onClick={() => paginate(number)}
                                        >
                                            {number}
                                        </button>
                                    ))}

                                    {currentPage < totalPages - 2 && totalPages > 5 && (
                                        <>
                                            {currentPage < totalPages - 3 && <span className="page-ellipsis">...</span>}
                                            <button className="page-button" onClick={() => paginate(totalPages)}>{totalPages}</button>
                                        </>
                                    )}

                                    <button
                                        className="page-button"
                                        onClick={() => paginate(currentPage + 1)}
                                        disabled={currentPage === totalPages}
                                    >
                                        Sonraki
                                    </button>
                                </div>
                            )}
                        </>
                    ) : (
                        <p className="no-results-message">
                            {filters.search
                                ? `"${filters.search}" aramasına uygun kayıt bulunamadı.`
                                : filters.operatorId
                                    ? `${getOperatorName(filters.operatorId)} operatörüne ait filtreleme kriterlerinize uygun kayıt bulunamadı.`
                                    : filters.method
                                        ? `${filters.method} methoduna ait filtreleme kriterlerinize uygun kayıt bulunamadı.`
                                        : 'Filtreleme kriterlerinize uygun kayıt bulunamadı.'}
                        </p>
                    )}
                </div>
            )}
        </div>
    );
}