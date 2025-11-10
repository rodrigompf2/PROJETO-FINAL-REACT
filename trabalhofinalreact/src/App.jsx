import React, { useState, useEffect } from "react";
import "./App.css";

export default function App() {
  const initialProducts = [
    {
      id: 1,
      name: "Camiseta Vintage",
      price: 79.9,
      image: "https://picsum.photos/seed/camiseta/400/300",
      description: "Algodão 100% — confortável e estilosa."
    },
    {
      id: 2,
      name: "Tênis Casual",
      price: 249.9,
      image: "https://picsum.photos/seed/tenis/400/300",
      description: "Leve, bom amortecimento para o dia a dia."
    },
    {
      id: 3,
      name: "Mochila Urbana",
      price: 179.0,
      image: "https://picsum.photos/seed/mochila/400/300",
      description: "Compartimento para laptop e bolsos organizadores."
    }
  ];

  const [products] = useState(initialProducts);
  const [cart, setCart] = useState(() => {
    const raw = localStorage.getItem("cart_v1");
    return raw ? JSON.parse(raw) : [];
  });
  const [orderMessage, setOrderMessage] = useState("");

  useEffect(() => {
    localStorage.setItem("cart_v1", JSON.stringify(cart));
  }, [cart]);

  function addToCart(product) {
    setCart((prev) => {
      const found = prev.find((p) => p.id === product.id);
      return found
        ? prev.map((p) =>
            p.id === product.id ? { ...p, qty: p.qty + 1 } : p
          )
        : [...prev, { ...product, qty: 1 }];
    });
  }

  function removeFromCart(id) {
    setCart((prev) => prev.filter((p) => p.id !== id));
  }

  function clearCart() {
    setCart([]);
  }

  const total = cart.reduce((sum, p) => sum + p.price * p.qty, 0);

  function handleCheckout() {
    setOrderMessage(`Compra finalizada! Total: R$ ${total.toFixed(2)}`);
    clearCart();
  }

  return (
    <div className="app-container">
      <header className="header">
        <h1>Minha Loja</h1>
      </header>

      <main className="main-content">
        <section className="product-list">
          <h2>Produtos</h2>
          <div className="grid">
            {products.map((p) => (
              <div key={p.id} className="product-card">
                <img src={p.image} alt={p.name} />
                <h4>{p.name}</h4>
                <p>{p.description}</p>
                <strong>R$ {p.price.toFixed(2)}</strong>
                <button onClick={() => addToCart(p)}>Adicionar</button>
              </div>
            ))}
          </div>
        </section>

        <aside className="cart">
          <h2>Carrinho</h2>
          {cart.length === 0 ? (
            <p>Seu carrinho está vazio</p>
          ) : (
            <>
              {cart.map((item) => (
                <div key={item.id} className="cart-item">
                  <span>
                    {item.name} x {item.qty}
                  </span>
                  <span>R$ {(item.price * item.qty).toFixed(2)}</span>
                  <button onClick={() => removeFromCart(item.id)}>
                    Remover
                  </button>
                </div>
              ))}
              <hr />
              <h3>Total: R$ {total.toFixed(2)}</h3>
              <button className="checkout-btn" onClick={handleCheckout}>
                Finalizar compra
              </button>
              <button className="clear-btn" onClick={clearCart}>
                Limpar carrinho
              </button>
            </>
          )}
          {orderMessage && <p className="success-msg">{orderMessage}</p>}
        </aside>
      </main>

      <footer className="footer">Serra Commerce </footer>
    </div>
  );
}
