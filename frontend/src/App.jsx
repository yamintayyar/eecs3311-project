import { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route, Link, Navigate, useNavigate } from 'react-router-dom';
import Dashboard from './components/Dashboard';
import ClientView from './components/ClientView';
import ConsultantView from './components/ConsultantView';
import Login from './components/Login';
import Register from './components/Register';
import './index.css';

function App() {
  const [authUser, setAuthUser] = useState(null);

  // Check LocalStorage on initial load
  useEffect(() => {
    const savedAuth = localStorage.getItem('auth');
    if (savedAuth) {
      setAuthUser(JSON.parse(savedAuth));
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('auth');
    setAuthUser(null);
  };

  return (
    <BrowserRouter>
      <div className="app-container">
        <nav className="navbar">
          <Link to="/" style={{ textDecoration: 'none' }}>
            <h1>Service Booking</h1>
          </Link>
          <div className="nav-links" style={{ alignItems: 'center' }}>
            {!authUser ? (
              <>
                <Link to="/login">Sign In</Link>
                <Link to="/register" className="btn btn-primary" style={{ padding: '0.4rem 1rem', display: 'flex', color: 'white' }}>
                  Get Started
                </Link>
              </>
            ) : (
              <>
                <span style={{ color: 'var(--text-secondary)' }}>
                  Welcome, <strong style={{ color: 'var(--text-primary)' }}>{authUser.name}</strong> ({authUser.role})
                </span>
                <button onClick={handleLogout} className="btn btn-danger" style={{ padding: '0.4rem 1rem' }}>
                  Logout
                </button>
              </>
            )}
          </div>
        </nav>
        <main className="main-content">
          <Routes>
            <Route path="/" element={<Dashboard authUser={authUser} />} />
            <Route path="/login" element={!authUser ? <Login setAuthUser={setAuthUser} /> : <Navigate to="/" />} />
            <Route path="/register" element={!authUser ? <Register setAuthUser={setAuthUser} /> : <Navigate to="/" />} />

            {/* Protected Routes */}
            <Route path="/client/*" element={
              authUser?.role === 'CLIENT' ? <ClientView authUser={authUser} /> : <Navigate to="/login" />
            } />
            <Route path="/consultant/*" element={
              authUser?.role === 'CONSULTANT' ? <ConsultantView authUser={authUser} /> : <Navigate to="/login" />
            } />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
