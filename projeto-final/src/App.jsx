import { Routes, Route, Navigate } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import ProductsPage from "./pages/ProductsPage";
import AdminPage from "./pages/AdminPage";
import Login from "./components/Login";
import ProtectedRoute from "./components/ProtectedRoute";
import Cart from "./components/Cart";

export default function App() {
  return (
    <>
      <Navbar />
      <main style={{ padding: 20, minHeight: "70vh" }}>
        <Routes>
          <Route path="/" element={<ProductsPage />} />
          <Route path="/cart" element={<Cart />} />
          <Route path="/login" element={<Login />} />

          <Route
            path="/admin/*"
            element={
              <ProtectedRoute>
                <AdminPage />
              </ProtectedRoute>
            }
          />

          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </main>
      <Footer />
    </>
  );
}
