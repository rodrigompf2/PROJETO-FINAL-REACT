export default function ProductCard({ product, onAdd }) {
  return (
    <div className="card">
      <img src={product.image || "https://via.placeholder.com/200x120?text=Produto"} alt={product.name} style={{width:"100%", borderRadius:6}} />
      <h3>{product.name}</h3>
      <p>R$ {product.price.toFixed(2)}</p>
      <p style={{fontSize:12, color:"#666"}}>{product.category}</p>
      <div style={{display:"flex", justifyContent:"center", gap:8, marginTop:8}}>
        <button className="btn btn-primary" onClick={() => onAdd(product)}>Adicionar</button>
      </div>
    </div>
  );
}
