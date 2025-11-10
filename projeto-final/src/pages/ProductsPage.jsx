import { useEffect, useState } from "react";
import api from "../api";
import ProductCard from "../components/ProductCard";

export default function ProductsPage(){
  const [products, setProducts] = useState([]);
  const [filter, setFilter] = useState("");
  const [error, setError] = useState("");

  useEffect(()=> {
    api.get("/products")
      .then(res => setProducts(res.data))
      .catch(()=> setError("Erro ao carregar produtos"));
  }, []);

  const addToCart = (product) => {
    const cart = JSON.parse(localStorage.getItem("cart") || "[]");
    const exists = cart.find(i=>i.id === product.id);
    if (exists) {
      const updated = cart.map(i => i.id === product.id ? {...i, qty:(i.qty||1)+1} : i);
      localStorage.setItem("cart", JSON.stringify(updated));
    } else {
      localStorage.setItem("cart", JSON.stringify([...cart, {...product, qty:1}]));
    }
    alert("Adicionado ao carrinho");
  };

  const filtered = products.filter(p => p.name.toLowerCase().includes(filter.toLowerCase()) || p.category.toLowerCase().includes(filter.toLowerCase()));

  return (
    <div className="container">
      <h2>Produtos</h2>
      <div style={{marginBottom:12}}>
        <input className="input" placeholder="Filtrar por nome ou categoria" value={filter} onChange={e=>setFilter(e.target.value)} />
      </div>
      {error && <p style={{color:'red'}}>{error}</p>}
      <div className="grid">
        {filtered.map(p => <ProductCard key={p.id} product={p} onAdd={addToCart} />)}
      </div>
    </div>
  );
}
