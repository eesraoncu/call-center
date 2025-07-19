// src/components/Charts.jsx
import React, { useState, useEffect } from 'react';
import {
    LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer,
    BarChart, Bar, PieChart, Pie, Cell, AreaChart, Area
} from 'recharts';
import './css/charts.css'; // Stil dosyamız

// --- Çağrı Hacmi Grafiği Bileşeni ---
export const CagriHacmiGrafigi = () => {
    const [chartData, setChartData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchChartData = async () => {
            try {
                setLoading(true);
                const dummyData = [
                    { name: 'Pzt', Gelen: Math.floor(Math.random() * 5000) + 1000, Giden: Math.floor(Math.random() * 3000) + 500 },
                    { name: 'Sal', Gelen: Math.floor(Math.random() * 5000) + 1000, Giden: Math.floor(Math.random() * 3000) + 500 },
                    { name: 'Çar', Gelen: Math.floor(Math.random() * 5000) + 1000, Giden: Math.floor(Math.random() * 3000) + 500 },
                    { name: 'Per', Gelen: Math.floor(Math.random() * 5000) + 1000, Giden: Math.floor(Math.random() * 3000) + 500 },
                    { name: 'Cum', Gelen: Math.floor(Math.random() * 5000) + 1000, Giden: Math.floor(Math.random() * 3000) + 500 },
                    { name: 'Cmt', Gelen: Math.floor(Math.random() * 5000) + 1000, Giden: Math.floor(Math.random() * 3000) + 500 },
                    { name: 'Paz', Gelen: Math.floor(Math.random() * 5000) + 1000, Giden: Math.floor(Math.random() * 3000) + 500 },
                ];
                setChartData(dummyData);
            } catch (err) {
                setError('Çağrı Hacmi verileri çekilirken bir hata oluştu: ' + err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchChartData();
    }, []);

    if (loading) return <p>Çağrı Hacmi verileri yükleniyor...</p>;
    if (error) return <p style={{ color: 'red' }}>Hata: {error}</p>;

    return (
        <div className="chart-container">
            <h3>Haftalık Çağrı Hacmi</h3>
            <ResponsiveContainer>
                <LineChart data={chartData} margin={{ top: 20, right: 20, left: 20, bottom: 60 }}>
                    <CartesianGrid strokeDasharray="3 3" stroke="#555" />
                    <XAxis dataKey="name" stroke="#bbb" />
                    <YAxis stroke="#bbb" />
                    <Tooltip contentStyle={{ backgroundColor: '#333', border: 'none', color: '#eee' }} />
                    <Legend wrapperStyle={{ color: '#bbb' }} />
                    <Line type="monotone" dataKey="Gelen" stroke="#e38dc8" activeDot={{ r: 8 }} />
                    <Line type="monotone" dataKey="Giden" stroke="#5d85b3" />
                </LineChart>
            </ResponsiveContainer>
        </div>
    );
};

// --- Ortalama Çağrı Süresi Grafiği Bileşeni ---
export const OrtalamaCagriSuresiGrafigi = () => {
    const [chartData, setChartData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchChartData = async () => {
            try {
                setLoading(true);
                const dummyData = [
                    { name: 'Görüşme', süre: Math.floor(Math.random() * 300) + 60 },
                    { name: 'Bekleme', süre: Math.floor(Math.random() * 60) + 10 },
                    { name: 'Sonlandırma', süre: Math.floor(Math.random() * 90) + 15 },
                ];
                setChartData(dummyData);
            } catch (err) {
                setError('Ortalama Çağrı Süresi verileri çekilirken bir hata oluştu: ' + err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchChartData();
    }, []);

    if (loading) return <p>Ortalama Çağrı Süresi verileri yükleniyor...</p>;
    if (error) return <p style={{ color: 'red' }}>Hata: {error}</p>;

    const COLORS = ['#5d85b3', '#9b5ea8', '#e8cd7b'];

    return (
        <div className="chart-container">
            <h3>Ortalama Çağrı Süresi Dağılımı </h3>
            <ResponsiveContainer>
                <PieChart>

                    <Pie
                        margin={{
                            top: 20,
                            right: 30,
                            left: 30, // Yan boşlukları artırın
                            bottom: 80 // Legend için yer bırakın
                        }}
                        data={chartData}
                        dataKey="süre"
                        nameKey="name"
                        cx="50%"
                        cy="50%"
                        innerRadius={60}
                        outerRadius={80}
                        label
                    >
                        {chartData.map((entry, index) => (
                            <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                        ))}
                    </Pie>
                    <Tooltip contentStyle={{ backgroundColor: '#333', border: 'none', color: '#eee' }} />
                    <Legend
                        layout="horizontal"
                        verticalAlign="bottom"
                        align="center"
                        wrapperStyle={{
                            paddingTop: '20px' // Legend ile grafik arası boşluk
                        }} />
                </PieChart>
            </ResponsiveContainer>
        </div>
    );
};

// --- İlk Çağrı Çözüm Oranı Grafiği Bileşeni ---
export const IlkCagriCozumOraniGrafigi = () => {
    const [chartData, setChartData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchChartData = async () => {
            try {
                setLoading(true);
                const resolved = Math.floor(Math.random() * 80) + 10; // Çözülen çağrı yüzdesi
                const unresolved = 100 - resolved; // Çözülmeyen çağrı yüzdesi
                const dummyData = [
                    { name: 'Çözülen', value: resolved },
                    { name: 'Çözülemeyen', value: unresolved },
                ];
                setChartData(dummyData);
            } catch (err) {
                setError('İlk Çağrı Çözüm Oranı verileri çekilirken bir hata oluştu: ' + err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchChartData();
    }, []);

    if (loading) return <p>İlk Çağrı Çözüm Oranı yükleniyor...</p>;
    if (error) return <p style={{ color: 'red' }}>Hata: {error}</p>;

    const COLORS = ['#5d85b3', '#9b5ea8'];

    return (
        <div className="chart-container">
            <h3>İlk Çağrı Çözüm Oranı (%)</h3>
            <ResponsiveContainer>
                <PieChart>
                    <Pie
                        data={chartData}
                        dataKey="value"
                        nameKey="name"
                        cx="50%"
                        cy="50%"
                        outerRadius={80}
                        label={({ name, percent }) => `${name}: ${(percent * 100).toFixed(0)}%`}
                    >
                        {chartData.map((entry, index) => (
                            <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                        ))}
                    </Pie>
                    <Tooltip contentStyle={{ backgroundColor: '#333', border: 'none', color: '#eee' }} />
                    <Legend wrapperStyle={{ color: '#bbb' }} />
                </PieChart>
            </ResponsiveContainer>
        </div>
    );
};

// --- Müşteri Memnuniyeti Skorları (CSAT/NPS) Grafiği Bileşeni ---
export const MusteriMemnuniyetiGrafigi = () => {
    const [chartData, setChartData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchChartData = async () => {
            try {
                setLoading(true);
                const dummyData = [
                    { name: 'Çok Memnun', value: Math.floor(Math.random() * 40) + 20 },
                    { name: 'Memnun', value: Math.floor(Math.random() * 30) + 15 },
                    { name: 'Nötr', value: Math.floor(Math.random() * 20) + 5 },
                    { name: 'Memnun Değil', value: Math.floor(Math.random() * 15) + 2 },
                    { name: 'Çok Memnun Değil', value: Math.floor(Math.random() * 10) + 1 },
                ];
                // Toplam %100 olması için oranları ayarlayabiliriz, ancak şimdilik mutlak değerler
                setChartData(dummyData);
            } catch (err) {
                setError('Müşteri Memnuniyeti verileri çekilirken bir hata oluştu: ' + err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchChartData();
    }, []);

    if (loading) return <p>Müşteri Memnuniyeti verileri yükleniyor...</p>;
    if (error) return <p style={{ color: 'red' }}>Hata: {error}</p>;

    const COLORS = ['#65a669', '#9b5ea8', '#e8c97b', '#5d85b3', '#FF4500'];
    return (
        <div className="chart-container">
            <h3>Müşteri Memnuniyeti Skorları</h3>
            <ResponsiveContainer>
                <BarChart
                    data={chartData}
                    margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
                    layout="vertical" // Dikey çubuk grafik
                >
                    <CartesianGrid strokeDasharray="3 3" stroke="#555" />
                    <XAxis type="number" stroke="#bbb" />
                    <YAxis type="category" dataKey="name" stroke="#bbb" />
                    <Tooltip contentStyle={{ backgroundColor: '#333', border: 'none', color: '#eee' }} />
                    <Legend wrapperStyle={{ color: '#bbb' }} />
                    <Bar dataKey="value" fill="#8884d8">
                        {chartData.map((entry, index) => (
                            <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                        ))}
                    </Bar>
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
};

// --- Temsilci Performans Grafiği Bileşeni ---
export const TemsilciPerformansGrafigi = () => {
    const [chartData, setChartData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchChartData = async () => {
            try {
                setLoading(true);
                const dummyData = [
                    { name: 'Ali Y.', Çağrı: Math.floor(Math.random() * 150) + 50, Süre: Math.floor(Math.random() * 300) + 120, Çözüm: Math.floor(Math.random() * 40) + 60 },
                    { name: 'Ayşe K.', Çağrı: Math.floor(Math.random() * 150) + 50, Süre: Math.floor(Math.random() * 300) + 120, Çözüm: Math.floor(Math.random() * 40) + 60 },
                    { name: 'Can B.', Çağrı: Math.floor(Math.random() * 150) + 50, Süre: Math.floor(Math.random() * 300) + 120, Çözüm: Math.floor(Math.random() * 40) + 60 },
                    { name: 'Deniz S.', Çağrı: Math.floor(Math.random() * 150) + 50, Süre: Math.floor(Math.random() * 300) + 120, Çözüm: Math.floor(Math.random() * 40) + 60 },
                ];
                setChartData(dummyData);
            } catch (err) {
                setError('Temsilci Performans verileri çekilirken bir hata oluştu: ' + err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchChartData();
    }, []);

    if (loading) return <p>Temsilci Performans verileri yükleniyor...</p>;
    if (error) return <p style={{ color: 'red' }}>Hata: {error}</p>;

    return (
        <div className="chart-container">
            <h3>Temsilci Performansı (Ort. Çağrı Süresi / Çözüm Oranı)</h3>
            <ResponsiveContainer>
                <BarChart
                    data={chartData}
                    margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
                >
                    <CartesianGrid strokeDasharray="3 3" stroke="#555" />
                    <XAxis dataKey="name" stroke="#bbb" />
                    <YAxis yAxisId="left" orientation="left" stroke="#bbb" label={{ value: 'Çağrı/Süre', angle: -90, position: 'insideLeft', fill: '#bbb' }} />
                    <YAxis yAxisId="right" orientation="right" stroke="#bbb" label={{ value: 'Çözüm (%)', angle: 90, position: 'insideRight', fill: '#bbb' }} />
                    <Tooltip contentStyle={{ backgroundColor: '#333', border: 'none', color: '#eee' }} />
                    <Legend wrapperStyle={{ color: '#bbb' }} />
                    <Bar yAxisId="left" dataKey="Çağrı" fill="#5d85b3" name="Çağrı Sayısı" />
                    <Bar yAxisId="left" dataKey="Süre" fill="#e8c97b" name="Ort. Süre (sn)" />
                    <Line yAxisId="right" type="monotone" dataKey="Çözüm" stroke="#00C49F" name="Çözüm Oranı (%)" />
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
};

// --- Bekleme Süresi Grafiği Bileşeni ---
export const BeklemeSuresiGrafigi = () => {
    const [chartData, setChartData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchChartData = async () => {
            try {
                setLoading(true);
                const dummyData = [
                    { name: '09:00', Bekleme: Math.floor(Math.random() * 60) + 5 },
                    { name: '10:00', Bekleme: Math.floor(Math.random() * 90) + 10 },
                    { name: '11:00', Bekleme: Math.floor(Math.random() * 120) + 15 },
                    { name: '12:00', Bekleme: Math.floor(Math.random() * 70) + 5 },
                    { name: '13:00', Bekleme: Math.floor(Math.random() * 50) + 5 },
                    { name: '14:00', Bekleme: Math.floor(Math.random() * 80) + 10 },
                    { name: '15:00', Bekleme: Math.floor(Math.random() * 100) + 10 },
                ];
                setChartData(dummyData);
            } catch (err) {
                setError('Bekleme Süresi verileri çekilirken bir hata oluştu: ' + err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchChartData();
    }, []);

    if (loading) return <p>Bekleme Süresi verileri yükleniyor...</p>;
    if (error) return <p style={{ color: 'red' }}>Hata: {error}</p>;

    return (
        <div className="chart-container">
            <h3>Ortalama Bekleme Süresi (Dakika)</h3>
            <ResponsiveContainer>
                <AreaChart
                    data={chartData}
                    margin={{ top: 10, right: 30, left: 0, bottom: 0 }}
                >
                    <CartesianGrid strokeDasharray="3 3" stroke="#555" />
                    <XAxis dataKey="name" stroke="#bbb" />
                    <YAxis stroke="#bbb" />
                    <Tooltip contentStyle={{ backgroundColor: '#333', border: 'none', color: '#eee' }} />
                    <Area type="monotone" dataKey="Bekleme" stroke="#5d85b3" fill="#5d85b3" fillOpacity={0.6} />
                </AreaChart>
            </ResponsiveContainer>
        </div>
    );
};