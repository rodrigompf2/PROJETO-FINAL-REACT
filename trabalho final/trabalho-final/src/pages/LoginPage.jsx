import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { Link } from 'react-router-dom';
import './LoginPage.css';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useAuth();

  const handleSubmit = (e) => {
    e.preventDefault();
    login(email, password);
  };

  return (
    <div className="login-page-wrapper">
      <div className="login-container-v2">
        
        <div className="login-branding-side">
          <div className="branding-content">
            <h2>Bem-vindo de volta!</h2>
            <p>Acesse sua conta para ver seus pedidos, salvar seus favoritos e muito mais.</p>
          </div>
        </div>

        <div className="login-form-side">
          <form onSubmit={handleSubmit} className="login-form-v2">
            <h2>Login Serra Commerce</h2>
            
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input
                id="email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="seuemail@exemplo.com"
                required
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="password">Senha</label>
              <input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Sua senha"
                required
              />
            </div>
            
            <div className="form-links">
              <Link to="/cadastrar" className="register-link-v2">Não tem conta? Cadastre-se</Link>
              <Link to="/esqueci-senha" className="forgot-password-link">Esqueci minha senha</Link>
            </div>

            <button type="submit" className="login-button-v2">Entrar</button>

            <div className="form-divider">
              <span>ou</span>
            </div>

            <div className="social-login">
              <button type="button" className="social-btn google">
                Entrar com Google
              </button>
              <button type="button" className="social-btn facebook">
                Entrar com Facebook
              </button>
            </div>
            
          </form>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;