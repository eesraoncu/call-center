import React from 'react';
import { NavLink } from 'react-router-dom';
import './Sidebar.css';

export default function Sidebar({ isOpen, toggleSidebar, user }) {
    return (
        <aside className={`sidebar ${isOpen ? 'open' : ''}`}>
            <button className="close-button" onClick={toggleSidebar}>
                ✕
            </button>
            <h2 className="sidebar-title">Çağrı Merkezi</h2>
            <nav className="nav-links">
                <NavLink to="/" className="nav-link" onClick={toggleSidebar}>
                    Ana Sayfa
                </NavLink>
                <NavLink to="/arama-kayitlari" className="nav-link" onClick={toggleSidebar}>
                    Arama Kayıtları
                </NavLink>
                <NavLink to="/musteriler" className="nav-link" onClick={toggleSidebar}>
                    Müşteriler
                </NavLink>
                {user.role === "ADMIN" && (
                    <NavLink to="/kullanicilar" className="nav-link" onClick={toggleSidebar}>
                        Kullanıcılar
                    </NavLink>
                )}
                <NavLink to="/cagrimerkezi" className="nav-link" onClick={toggleSidebar}>
                    Genel Bilgi
                </NavLink>

                {user.role === "ADMIN" && (
                    <NavLink to="/logs" className="nav-link" onClick={toggleSidebar}>
                        Log Kayıtları
                    </NavLink>
                )}
            </nav>
        </aside>
    );
}