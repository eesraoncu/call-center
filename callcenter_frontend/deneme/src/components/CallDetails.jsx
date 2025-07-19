import React, { useState, useEffect } from 'react';
import './CallDetails.css';
import { getCallById } from '../services/api';

export default function CallDetails({ callId }) {
    const [call, setCall] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!callId) {
            setLoading(false);
            return;
        }

        setLoading(true);
        setError(null);

        getCallById(callId)
            .then(data => {
                setCall(data);
            })
            .catch(err => {
                setError(err.message || 'Çağrı detayları yüklenirken bir hata oluştu');
            })
            .finally(() => {
                setLoading(false);
            });
    }, [callId]);

    if (loading) {
        return <p className="loading-text">Detaylar yükleniyor…</p>;
    }

    if (error) {
        return <p className="loading-text error">{error}</p>;
    }

    if (!call) {
        return <p className="loading-text">Lütfen soldan bir çağrı seçin.</p>;
    }

    return (
        <div className="call-details">
            <section className="call-overview">
                <div><strong>Çağrı ID:</strong> {call.id}</div>
                <div><strong>Zaman:</strong> {call.timestamp}</div>
                <div><strong>Durum:</strong> {call.status}</div>
            </section>

            <section className="customer-info">
                <h3>Müşteri Bilgileri</h3>
                <div className="info-row">
                    <span className="info-label">Ad Soyad:</span>
                    <span className="info-value">{call.customer.name}</span>
                </div>
                <div className="info-row">
                    <span className="info-label">Telefon:</span>
                    <span className="info-value">{call.customer.phone}</span>
                </div>
                <div className="info-row">
                    <span className="info-label">Adres:</span>
                    <span className="info-value">{call.customer.address}</span>
                </div>
            </section>

            <section className="complaint-section">
                <h3>Şikayet / Konu</h3>
                <p className="complaint-text">{call.complaint}</p>
            </section>

            <section className="notes-section">
                <h3>Çağrı Notları</h3>
                {call.notes && call.notes.length > 0 ? (
                    <ul className="notes-list">
                        {call.notes.map((note, idx) => (
                            <li key={idx} className="note-item">
                                <span className="note-time">{note.time}</span>
                                <span className="note-text">{note.text}</span>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p className="no-notes">Henüz not yok.</p>
                )}
            </section>
        </div>
    );
}
