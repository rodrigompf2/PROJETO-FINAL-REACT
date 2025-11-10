import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api";

export default function Login(){
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [msg, setMsg] = useState("");
  const navigate = useNavigate();

  const handle = async (e) => {
    e.preventDefault();
    try {
      const res = await api.get(`/users?username=${username}&password=${password}`);
      if (res.data.length > 0) {
        localStorage.setItem("auth", JSON.stringify({ username }));
        navigate("/admin");
      } else {
        setMsg("Usuário ou senha inválidos.");
      }
    } catch (err) {
      setMsg("Erro ao conectar ao servidor.");
    }
  };

  return (
    <div className="container">
      <h2>Login</h2>
      {msg && <p style={{color:'red'}}>{msg}</p>}
      <form onSubmit={handle} style={{maxWidth:400}}>
        <div className="form-row">
          <input className="input" placeholder="Usuário" value={username} onChange={e=>setUsername(e.target.value)} />
        </div>
        <div className="form-row">
          <input className="input" type="password" placeholder="Senha" value={password} onChange={e=>setPassword(e.target.value)} />
        </div>
        <button className="btn btn-primary" type="submit">Entrar</button>
      </form>
    </div>
  );
}
