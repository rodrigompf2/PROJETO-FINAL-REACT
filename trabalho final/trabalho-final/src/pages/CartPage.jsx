import React from 'react';
import { useCart } from '../contexts/CartContext';
import { Link, useNavigate } from 'react-router-dom';
import './CartPage.css';

const CartPage = () => {
  const { cart, removeFromCart, updateQuantity, totalPrice, clearCart } = useCart();
  const navigate = useNavigate();

  const handleCheckout = () => {
    alert('Compra finalizada!');
    clearCart();
    navigate('/');
  };

  return (
    <div className="cart-container">
      <h2>Seu Carrinho</h2>
      {cart.length === 0 ? (
        <div className="cart-empty">
          <p>Seu carrinho está vazio.</p>
          <Link to="/" className="btn btn-primary">Voltar à Loja</Link>
        </div>
      ) : (
        <>
          <div className="cart-items-list">
            {cart.map(item => (
              <div key={item.id} className="cart-item">
                <div className="cart-item-details">
                  <h4>{item.nome}</h4>
                  <p>Preço: R$ {item.valor?.toFixed(2)}</p>
                  <p>Subtotal: R$ {(item.valor * item.quantity).toFixed(2)}</p>
                </div>
                <div className="cart-item-actions">
                  <div className="quantity-controls">
                    <button onClick={() => updateQuantity(item.id, -1)}>-</button>
                    <span>{item.quantity}</span>
                    <button onClick={() => updateQuantity(item.id, 1)}>+</button>
                  </div>
                  <button onClick={() => removeFromCart(item.id)} className="btn btn-danger">
                    Remover
                  </button>
                </div>
              </div>
            ))}
          </div>
          <div className="cart-summary">
            <h3>Resumo do Pedido</h3>
            <h4>Total: R$ {totalPrice.toFixed(2)}</h4>
            <button onClick={handleCheckout} className="btn btn-success">
              Finalizar Compra
            </button>
            <button onClick={clearCart} className="btn btn-danger-outline">
              Limpar Carrinho
            </button>
          </div>
        </>
      )}
    </div>
  );
};

export default CartPage;