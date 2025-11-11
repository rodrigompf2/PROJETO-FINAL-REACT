import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../services/api';
import './ProductFormPage.css';
import LoadingSpinner from '../components/LoadingSpinner';

const ProductFormPage = () => {
  const [nome, setNome] = useState('');
  const [valor, setValor] = useState('');
  const [descricao, setDescricao] = useState('');
  const [imagemUrl, setImagemUrl] = useState('');
  const [idCategoria, setIdCategoria] = useState('');
  
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(false);
  
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    setLoading(true);
    api.get('/categorias')
      .then(response => {
        setCategorias(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar categorias:', error);
        alert('Não foi possível carregar as categorias.');
      })
      .finally(() => {
        setLoading(false);
      });

    if (id) {
      setLoading(true);
      api.get(`/produtos/${id}`)
        .then(response => {
          const { nome, valor, descricao, imagemUrl, categoria } = response.data;
          setNome(nome);
          setValor(valor);
          setDescricao(descricao || '');
          setImagemUrl(imagemUrl || '');
          setIdCategoria(categoria?.id || ''); 
        })
        .catch(error => {
          console.error('Erro ao buscar produto:', error);
          alert('Não foi possível carregar o produto para edição.');
        })
        .finally(() => {
          setLoading(false);
        });
    }
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    const produtoData = {
      nome,
      valor: parseFloat(valor),
      descricao,
      imagemUrl,
      idCategoria
    };

    try {
      if (id) {
        await api.put(`/produtos/${id}`, produtoData);
      } else {
        await api.post('/produtos', produtoData);
      }
      alert('Produto salvo com sucesso!');
      navigate('/dashboard');
    } catch (error) {
      console.error('Erro ao salvar produto:', error);
      alert('Erro ao salvar produto. Verifique se preencheu todos os campos.');
    } finally {
      setLoading(false);
    }
  };

  if (loading && !categorias.length) {
    return <LoadingSpinner />;
  }

  return (
    <div className="product-form-container">
      <form onSubmit={handleSubmit} className="product-form">
        <h2>{id ? 'Editar Produto' : 'Cadastrar Novo Produto'}</h2>
        
        <div className="form-group">
          <label htmlFor="nome">Nome do Produto</label>
          <input
            id="nome"
            type="text"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            required
          />
        </div>
        
        <div className="form-group">
          <label htmlFor="valor">Valor (R$)</label>
          <input
            id="valor"
            type="number"
            step="0.01"
            value={valor}
            onChange={(e) => setValor(e.target.value)}
            required
          />
        </div>
        
        <div className="form-group">
          <label htmlFor="descricao">Descrição</label>
          <textarea
            id="descricao"
            rows="4"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label htmlFor="imagemUrl">URL da Imagem</label>
          <input
            id="imagemUrl"
            type="text"
            value={imagemUrl}
            onChange={(e) => setImagemUrl(e.target.value)}
            placeholder="https://exemplo.com/imagem.png"
          />
        </div>

        <div className="form-group">
          <label htmlFor="categoria">Categoria</label>
          <select
            id="categoria"
            value={idCategoria}
            onChange={(e) => setIdCategoria(e.target.value)}
            required
          >
            <option value="" disabled>Selecione uma categoria</option>
            {categorias.map(cat => (
              <option key={cat.id} value={cat.id}>
                {cat.nome}
              </option>
            ))}
          </select>
        </div>

        <div className="form-actions">
          <button 
            type="button" 
            className="btn btn-secondary" 
            onClick={() => navigate('/dashboard')}
            disabled={loading}
          >
            Cancelar
          </button>
          <button 
            type="submit" 
            className="btn btn-success" 
            disabled={loading}
          >
            {loading ? 'Salvando...' : 'Salvar Produto'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default ProductFormPage;