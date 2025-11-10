import { useState, useEffect } from "react";

export default function ProductForm({ onSubmit, initial = {} }) {
  const [name, setName] = useState(initial.name || "");
  const [price, setPrice] = useState(initial.price || "");
  const [category, setCategory] = useState(initial.category || "");
  const [stock, setStock] = useState(initial.stock || "");
  const [error, setError] = useState("");

  useEffect(() => {
    setName(initial.name || "");
    setPrice(initial.price || "");
    setCategory(initial.category || "");
    setStock(initial.stock || "");
    setError("");
  }, [initial]);

  const handle = (e) => {
    e.preventDefault();
    if (!name.trim() || !price || isNaN(Number(price))) {
      setError("Preencha nome e preço corretamente.");
      return;
    }
    onSubmit({ name, price: Number(price), category, stock: Number(stock || 0) });
    setName(""); setPrice(""); setCategory(""); setStock("");
  };

  return (
    <form onSubmit={handle} style={{maxWidth:480}}>
      {error && <p style={{color:'red'}}>{error}</p>}
      <div className="form-row">
        <input className="input" placeholder="Nome" value={name} onChange={e=>setName(e.target.value)} />
      </div>
      <div className="form-row">
        <input className="input" placeholder="Preço" value={price} onChange={e=>setPrice(e.target.value)} />
      </div>
      <div className="form-row">
        <input className="input" placeholder="Categoria" value={category} onChange={e=>setCategory(e.target.value)} />
      </div>
      <div className="form-row">
        <input className="input" placeholder="Estoque" value={stock} onChange={e=>setStock(e.target.value)} />
      </div>
      <button className="btn btn-primary" type="submit">Salvar</button>
    </form>
  );
}
