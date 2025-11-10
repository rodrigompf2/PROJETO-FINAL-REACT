import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();
  const isAuth = !!localStorage.getItem("auth");

  const logout = () => {
    localStorage.removeItem("auth");
    navigate("/login");
  };

  return (
    <nav>
      <div className="container" style={{display:"flex", justifyContent:"space-between", alignItems:"center"}}>
        <div>
          <Link to="/">Ecommerce</Link>
          <Link to="/" style={{marginLeft:12}}>Produtos</Link>
          <Link to="/cart" style={{marginLeft:12}}>Carrinho</Link>
          <Link to="/admin" style={{marginLeft:12}}>Admin</Link>
        </div>
        <div>
          {isAuth ? <button className="btn" onClick={logout}>Sair</button> : <Link to="/login">Login</Link>}
        </div>
      </div>
    </nav>
  );
}
