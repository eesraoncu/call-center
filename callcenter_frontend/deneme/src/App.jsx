// src/App.jsx
import React, { useState } from 'react';
import { Routes, Route, Navigate, useLocation } from 'react-router-dom';
import { useAuth } from './context/AuthContext';

import Sidebar from './components/Sidebar';
import LogoutButton from './components/LogoutButton';
import AnaSayfa from './pages/AnaSayfa';
import AnaSayfaKullanici from './pages/AnaSayfaKullanici';
import AramaKayitlari from './pages/AramaKayitlari';
import Musteriler from './pages/Musteriler';
import Kullanicilar from './pages/Kullanicilar';
import CagriMerkezi from './pages/CagriMerkezi';
import Login from './pages/Login';
import LogSayfasi from './pages/LogSayfasi';

export default function App() {
  const { user, loading } = useAuth();
  console.log("user.role:", user?.role);
  const location = useLocation();
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const toggleSidebar = () => setSidebarOpen(o => !o);

  // Yükleniyor durumu
  if (loading) {
    return <div style={{ padding: 20, textAlign: 'center' }}>Yükleniyor...</div>;
  }

  return (
    <div className="app-container">
      {user && (
        <>
          <button className="menu-button" onClick={toggleSidebar}>☰</button>
          <Sidebar
            isOpen={sidebarOpen}
            toggleSidebar={toggleSidebar}
            user={user}
          />
          <LogoutButton />
        </>
      )}

      <main className="main-content">
        <Routes>
          {/* Login Route */}
          <Route
            path="/login"
            element={
              !user
                ? <Login />
                : <Navigate to="/" replace />
            }
          />
          <Route
            path="/"
            element={
              user
                ? (user.role === "OPERATOR" ? <AnaSayfaKullanici /> : <AnaSayfa />)
                : <Navigate to="/login" state={{ from: location }} replace />
            }
          />

          {/* Diğer korumalı rotalar */}
          <Route
            path="/arama-kayitlari"
            element={<AramaKayitlari />}
          />

          <Route
            path="/musteriler"
            element={
              user
                ? <Musteriler />
                : <Navigate to="/login" replace />
            }
          />

          <Route
            path="/kullanicilar"
            element={
              user?.role === "ADMIN"
                ? <Kullanicilar />
                : <Navigate to="/" replace />
            }
          />

          <Route
            path="/cagrimerkezi"
            element={
              user
                ? <CagriMerkezi />
                : <Navigate to="/login" replace />
            }
          />

          <Route path="/logs" element={<LogSayfasi />} />

          {/* Fallback */}
          <Route
            path="*"
            element={
              user
                ? <Navigate to="/" replace />
                : <Navigate to="/login" replace />
            }
          />
        </Routes>
      </main>
    </div>
  );
}