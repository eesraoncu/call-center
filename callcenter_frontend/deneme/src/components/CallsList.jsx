import React, { useState, useEffect } from 'react';
import './CallsList.css';
import { getAllCalls } from '../services/api';

export default function CallsList({ onSelect }) {
    const [calls, setCalls] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        getAllCalls()
            .then(data => setCalls(data))
            .catch(err => setError(err.message || 'Çağrı listesi yüklenirken hata oluştu'))
            .finally(() => setLoading(false));
    }, []);

    if (loading) {
        return <div className="calls-list">Yükleniyor…</div>;
    }

    if (error) {
        return <div className="calls-list error">{error}</div>;
    }

    return (
        <ul className="calls-list">
            {calls.map(call => (
                <li key={call.id} onClick={() => onSelect(call.id)}>
                    <div className="call-item-header">
                        <span className="call-id">{call.id}</span>
                        <span className={`call-status ${call.status?.toLowerCase() || ''}`}>
                            {call.status}
                        </span>
                    </div>
                    <div className="call-customer">
                        {call.customerName || call.customer?.name}
                    </div>
                    <div className="call-time">
                        {call.timestamp || call.time}
                    </div>
                </li>
            ))}
        </ul>
    );
}
