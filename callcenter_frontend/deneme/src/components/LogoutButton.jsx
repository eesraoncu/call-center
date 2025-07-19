// src/components/LogoutButton.jsx
import { useNavigate } from "react-router-dom";
import { useAuth } from '../context/AuthContext'; // Import useAuth hook

const LogoutButton = () => { // No longer needs setUser prop
    const navigate = useNavigate();
    const { logout } = useAuth(); // Get the logout function from AuthContext

    const handleLogout = () => {
        // Call the logout function from your AuthContext
        logout();
        // AuthContext's logout function should handle:
        // 1. Removing 'token' and 'user' from localStorage
        // 2. Setting the global user state to null

        // Redirect to the login page
        navigate("/login");
    };

    return (
        <div className="logout-container">
            <button onClick={handleLogout} className="logout-button">
                Çıkış Yap
            </button>
        </div>
    );
};

export default LogoutButton;