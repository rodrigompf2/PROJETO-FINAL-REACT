import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../services/api';
import './RegisterPage.css';

const RegisterPage = () => {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const novoUsuario = {
      nome,
      email,
      senha: password
    };
    
    try {
      await api.post('/usuarios', novoUsuario); 
      alert('Cadastro realizado com sucesso! Faça o login.');
      navigate('/login');
    } catch (error) {
      console.error("Erro ao cadastrar:", error);
      alert('Erro ao cadastrar. Verifique os dados.');
    }
  };

  return (
    <div className="register-page-wrapper">
      <div className="register-container-v2">
        
        <div className="register-branding-side">
          <div className="branding-content">
            <h2>Crie sua Conta</h2>
            <p>Rápido e fácil. Preencha seus dados para começar a comprar.</p>
          </div>
        </div>

        <div className="register-form-side">
          <form onSubmit={handleSubmit} className="register-form-v2">
            <h2>Novo Cadastro</h2>
            
            <div className="form-group">
              <label htmlFor="nome">Nome Completo</label>
              <input
                id="nome"
                type="text"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
                placeholder="Seu nome completo"
                required
              />
            </div>

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
                placeholder="Crie uma senha forte"
                required
              />
            </div>
            
            <button type="submit" className="register-button-v2">Cadastrar</button>

            <div className="form-links-register">
              <Link to="/login" className="login-link-v2">Já tem conta? Faça o login</Link>
            </div>
            
          </form>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;