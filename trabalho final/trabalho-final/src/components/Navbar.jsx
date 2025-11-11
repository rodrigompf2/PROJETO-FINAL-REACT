import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useCart } from '../contexts/CartContext';
import './Navbar.css';

const Navbar = () => {
  const { isAuthenticated, logout } = useAuth();
  const { totalItems } = useCart();

  return (
    <nav className="navbar">
      <Link to="/" className="navbar-logo-link">
        <span className="navbar-logo-text">Serra Commerce</span>
      </Link>
      
      <div className="navbar-links">
        <Link to="/cart" className="navbar-cart-link">
          Carrinho ({totalItems})
        </Link>
        
        {isAuthenticated ? (
          <>
            <Link to="/dashboard" className="navbar-link">Admin</Link>
            <button onClick={logout} className="navbar-button">
              Sair
            </button>
          </>
        ) : (
          <Link to="/login" className="navbar-link">
            Login
          </Link>
        )}
      </div>
    </nav>
  );
};

export default Navbar;