import React, { useState, useEffect, useRef } from 'react';
import api from '../services/api';
import { useCart } from '../contexts/CartContext';
import LoadingSpinner from '../components/LoadingSpinner';
import './StorePage.css';

const StorePage = () => {
  const [produtos, setProdutos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filtro, setFiltro] = useState('');
  const { addToCart } = useCart();
  const productSectionRef = useRef(null);

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

  const scrollToProducts = () => {
    productSectionRef.current.scrollIntoView({ behavior: 'smooth' });
  };

  const produtosFiltrados = produtos.filter(p =>
    p.nome.toLowerCase().includes(filtro.toLowerCase()) ||
    (p.descricao && p.descricao.toLowerCase().includes(filtro.toLowerCase()))
  );

  if (loading) {
    return <LoadingSpinner />;
  }
  
  const getFallbackImage = (produto) => {
    const id = produto.id ? produto.id.charCodeAt(0) % 50 + 1 : 1;
    return `https://picsum.photos/id/${id}/300/200`; 
  };

  return (
    <div className="store-page-wrapper">
      <header className="store-hero-banner">
        <div className="hero-content">
          <h1>Ofertas Incríveis na Serra Commerce</h1>
          <p>Encontre os melhores produtos com os melhores preços. Compre já!</p>
          <button onClick={scrollToProducts} className="btn btn-hero">Ver Ofertas</button>
        </div>
      </header>

      <section ref={productSectionRef} className="product-listing-section">
        <div className="filter-bar">
          <input
            type="text"
            placeholder="Buscar produtos..."
            className="search-input"
            value={filtro}
            onChange={(e) => setFiltro(e.target.value)}
          />
        </div>

        <div className="product-grid-v2">
          {produtosFiltrados.length > 0 ? (
            produtosFiltrados.map(produto => (
              <div key={produto.id} className="product-card-v2">
                <button className="favorite-btn" onClick={(e) => e.stopPropagation()}>
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                    <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path>
                  </svg>
                </button>

                <img 
                  src={produto.imagemUrl || getFallbackImage(produto)} 
                  alt={produto.nome} 
                  className="product-image" 
                  onError={(e) => { e.target.onerror = null; e.target.src = getFallbackImage(produto); }} // Usa fallback em caso de erro
                />
                
                <div className="product-info">
                  <h3 className="product-title">{produto.nome}</h3>
                  
                  <div className="product-reviews">
                    <span>★★★★☆</span>
                    <span className="review-count">(123)</span>
                  </div>

                  <div className="product-price-section">
                    <span className="current-price">R$ {produto.valor?.toFixed(2)}</span>
                  </div>

                  <button 
                    onClick={() => addToCart(produto)} 
                    className="btn btn-add-to-cart"
                  >
                    Adicionar
                  </button>
                </div>
              </div>
            ))
          ) : (
            <p className="no-products-found">Nenhum produto encontrado com os critérios de busca.</p>
          )}
        </div>
      </section>
    </div>
  );
};

export default StorePage;