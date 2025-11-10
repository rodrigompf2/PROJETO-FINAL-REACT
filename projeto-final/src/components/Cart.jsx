import { useState, useEffect } from "react";

export default function Cart(){
  const [items, setItems] = useState(() => JSON.parse(localStorage.getItem("cart")||"[]"));

  useEffect(()=> { localStorage.setItem("cart", JSON.stringify(items)); }, [items]);

  const remove = (id) => setItems(prev => prev.filter(i => i.id !== id));
  const changeQty = (id, qty) => setItems(prev => prev.map(i => i.id === id ? {...i, qty} : i));

  const total = items.reduce((s,i) => s + (i.price * (i.qty||1)), 0);

  return (
    <div className="container">
      <h2>Carrinho</h2>
      {items.length === 0 ? <p>Sem itens</p> : (
        <>
          {items.map(i => (
            <div key={i.id} style={{display:"flex", gap:12, alignItems:"center", marginBottom:8}}>
              <div style={{flex:1}}>{i.name}</div>
              <div>R$ {i.price.toFixed(2)}</div>
              <input type="number" value={i.qty||1} style={{width:60}} onChange={e=>changeQty(i.id, Number(e.target.value))} />
              <button onClick={()=>remove(i.id)}>Remover</button>
            </div>
          ))}
          <h3>Total: R$ {total.toFixed(2)}</h3>
          <button className="btn btn-primary" onClick={()=>{ alert('Compra finalizada (simulada)'); setItems([]); }}>Finalizar Compra</button>
        </>
      )}
    </div>
  );
}
