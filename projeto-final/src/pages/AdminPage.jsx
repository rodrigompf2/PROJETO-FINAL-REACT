import { useState, useEffect } from "react";
import api from "../api";
import ProductForm from "../components/ProductForm";

export default function AdminPage(){
  const [products, setProducts] = useState([]);
  const [editing, setEditing] = useState(null);
  const [error, setError] = useState("");

  const load = () => {
    api.get("/products")
      .then(res => setProducts(res.data))
      .catch(()=> setError("Erro ao carregar"));
  };

  useEffect(()=> load(), []);

  const create = async (data) => {
    try {
      await api.post("/products", data);
      load();
    } catch { setError("Erro ao criar"); }
  };

  const update = async (data) => {
    try {
      await api.put(`/products/${editing.id}`, data);
      setEditing(null);
      load();
    } catch { setError("Erro ao atualizar"); }
  };

  const remove = async (id) => {
    if (!confirm("Deseja excluir?")) return;
    try {
      await api.delete(`/products/${id}`);
      load();
    } catch { setError("Erro ao deletar"); }
  };

  return (
    <div className="container">
      <h2>Painel Admin</h2>
      {error && <p style={{color:'red'}}>{error}</p>}

      <div style={{display:"flex", gap:20, flexWrap:"wrap"}}>
        <div style={{flex:1, minWidth:320}}>
          <h3>{editing ? "Editar Produto" : "Criar Produto"}</h3>
          <ProductForm initial={editing||{}} onSubmit={editing ? update : create} />
          {editing && <button onClick={()=>setEditing(null)}>Cancelar</button>}
        </div>

        <div style={{flex:2, minWidth:320}}>
          <h3>Produtos</h3>
          {products.map(p => (
            <div key={p.id} style={{display:"flex", justifyContent:"space-between", alignItems:"center", marginBottom:8, background:"#fff", padding:8, borderRadius:6}}>
              <div>
                <strong>{p.name}</strong><br/>
                <small>{p.category} • R$ {p.price}</small>
              </div>
              <div style={{display:"flex", gap:8}}>
                <button onClick={()=>setEditing(p)}>Editar</button>
                <button onClick={()=>remove(p.id)}>Excluir</button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
