import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../services/api';
import LoadingSpinner from '../components/LoadingSpinner';
import './DashboardPage.css';

const DashboardPage = () => {
  const [produtos, setProdutos] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProdutos = async () => {
      try {
        const response = await api.get('/produtos');
        setProdutos(response.data);
      } catch (error) {
        console.error("Erro ao buscar produtos:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchProdutos();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este produto?')) {
      try {
        await api.delete(`/produtos/${id}`);
        setProdutos(produtos.filter(p => p.id !== id));
      } catch (error) {
        console.error('Erro ao excluir produto:', error);
      }
    }
  };

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <h2>Painel do Administrador</h2>
        <Link to="/produtos/novo" className="btn btn-success btn-add-product">
          Adicionar Novo Produto
        </Link>
      </header>

      <h3>Todos os Produtos</h3>
      <table className="product-admin-table">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Preço</th>
            <th>Descrição</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {produtos.length > 0 ? (
            produtos.map(produto => (
              <tr key={produto.id}>
                <td>{produto.nome}</td>
                <td>R$ {produto.valor?.toFixed(2)}</td>
                <td>{produto.descricao || 'N/A'}</td>
                <td className="actions-cell">
                  <Link 
                    to={`/produtos/editar/${produto.id}`} 
                    className="btn btn-warning btn-edit"
                  >
                    Editar
                  </Link>
                  <button 
                    onClick={() => handleDelete(produto.id)} 
                    className="btn btn-danger btn-delete"
                  >
                    Excluir
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4" style={{ textAlign: 'center' }}>Nenhum produto cadastrado.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default DashboardPage;