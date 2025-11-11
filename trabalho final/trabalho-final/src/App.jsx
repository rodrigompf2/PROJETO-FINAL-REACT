import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import { CartProvider } from './contexts/CartContext';

import Navbar from './components/Navbar.jsx';
import Footer from './components/Footer.jsx';
import ProtectedRoute from './components/ProtectedRoute.jsx';

import StorePage from './pages/StorePage.jsx';
import CartPage from './pages/CartPage.jsx';
import LoginPage from './pages/LoginPage.jsx';
import RegisterPage from './pages/RegisterPage.jsx';
import DashboardPage from './pages/DashboardPage.jsx';
import ProductFormPage from './pages/ProductFormPage.jsx';

import './App.css'; 

function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <div className="app-container">
          <Navbar />
          <main className="main-content">
            <Routes>
              <Route path="/login" element={<LoginPage />} />
              <Route path="/cadastrar" element={<RegisterPage />} />
              
              <Route element={<ProtectedRoute />}> 
                <Route path="/" element={<StorePage />} /> 
                <Route path="/cart" element={<CartPage />} />
                <Route path="/dashboard" element={<DashboardPage />} />
                <Route path="/produtos/novo" element={<ProductFormPage />} />
                <Route path="/produtos/editar/:id" element={<ProductFormPage />} />
              </Route>

              <Route path="*" element={<h2>Página não encontrada</h2>} />
            </Routes>
          </main>
          <Footer />
        </div>
      </CartProvider>
    </AuthProvider>
  );
}

export default App;