import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

const API_URL = 'http://localhost:8080';

export default function Login({ setAuthUser }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('CLIENT'); // 'CLIENT' or 'CONSULTANT'
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            // Phase 1 Mock Login: Fetch all users of that role and find a match
            const endpoint = role === 'CLIENT' ? '/clients' : '/consultants';
            const response = await axios.get(`${API_URL}${endpoint}`);
            const users = response.data;

            const user = users.find(u => u.email === email && u.password === password);

            if (user) {
                // Success
                const userId = role === 'CLIENT' ? user.clientId : user.consultantId;
                const authData = { id: userId, name: user.name, role: role };

                // Save to elevated state
                setAuthUser(authData);
                localStorage.setItem('auth', JSON.stringify(authData));

                // Redirect to appropriate portal
                navigate(role === 'CLIENT' ? '/client' : '/consultant');
            } else {
                setError('Invalid email or password');
            }
        } catch (err) {
            console.error(err);
            setError('Error connecting to authentication service.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-container">
            <div className="glass-panel" style={{ maxWidth: '400px', width: '100%' }}>
                <h2 className="title" style={{ textAlign: 'center', marginBottom: '0.5rem' }}>Welcome Back</h2>
                <p className="subtitle" style={{ textAlign: 'center', marginBottom: '2rem' }}>Sign in to continue to Service Booking.</p>

                {error && <div style={{ color: 'var(--danger-color)', marginBottom: '1rem', textAlign: 'center', backgroundColor: 'rgba(239, 68, 68, 0.1)', padding: '0.5rem', borderRadius: '0.5rem' }}>{error}</div>}

                <form onSubmit={handleLogin}>
                    <div className="form-group">
                        <label className="form-label">I am a...</label>
                        <div style={{ display: 'flex', gap: '1rem', marginBottom: '0.5rem' }}>
                            <button
                                type="button"
                                className={`btn ${role === 'CLIENT' ? 'btn-primary' : 'btn-secondary'}`}
                                style={{ flex: 1 }}
                                onClick={() => setRole('CLIENT')}
                            >
                                Client
                            </button>
                            <button
                                type="button"
                                className={`btn ${role === 'CONSULTANT' ? 'btn-primary' : 'btn-secondary'}`}
                                style={{ flex: 1 }}
                                onClick={() => setRole('CONSULTANT')}
                            >
                                Consultant
                            </button>
                        </div>
                    </div>

                    <div className="form-group">
                        <label className="form-label">Email</label>
                        <input
                            type="email"
                            className="form-input"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label className="form-label">Password</label>
                        <input
                            type="password"
                            className="form-input"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    <button type="submit" className="btn btn-primary" style={{ width: '100%', marginTop: '1rem' }} disabled={loading}>
                        {loading ? 'Signing In...' : 'Sign In'}
                    </button>
                </form>

                <p style={{ textAlign: 'center', marginTop: '1.5rem', color: 'var(--text-secondary)' }}>
                    Don't have an account? <Link to="/register" style={{ color: 'var(--primary-color)', textDecoration: 'none' }}>Register here</Link>
                </p>
            </div>
        </div>
    );
}
