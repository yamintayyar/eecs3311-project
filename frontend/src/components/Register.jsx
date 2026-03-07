import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

const API_URL = 'http://localhost:8080';

export default function Register({ setAuthUser }) {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('CLIENT'); // 'CLIENT' or 'CONSULTANT'
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            const endpoint = role === 'CLIENT' ? '/clients' : '/consultants';
            const payload = { name, email, password };

            const response = await axios.post(`${API_URL}${endpoint}`, payload);
            const user = response.data;

            // Auto-login upon successful registration
            const userId = role === 'CLIENT' ? user.clientId : user.consultantId;
            const authData = { id: userId, name: user.name, role: role };

            setAuthUser(authData);
            localStorage.setItem('auth', JSON.stringify(authData));

            navigate(role === 'CLIENT' ? '/client' : '/consultant');
        } catch (err) {
            console.error(err);
            setError('Failed to register account. Please check your connection.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-container">
            <div className="glass-panel" style={{ maxWidth: '400px', width: '100%' }}>
                <h2 className="title" style={{ textAlign: 'center', marginBottom: '0.5rem' }}>Create Account</h2>
                <p className="subtitle" style={{ textAlign: 'center', marginBottom: '2rem' }}>Join the Service Booking platform.</p>

                {error && <div style={{ color: 'var(--danger-color)', marginBottom: '1rem', textAlign: 'center', backgroundColor: 'rgba(239, 68, 68, 0.1)', padding: '0.5rem', borderRadius: '0.5rem' }}>{error}</div>}

                <form onSubmit={handleRegister}>
                    <div className="form-group">
                        <label className="form-label">I want to register as a...</label>
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
                        <label className="form-label">Full Name</label>
                        <input
                            type="text"
                            className="form-input"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                        />
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
                            minLength={4}
                        />
                    </div>

                    <button type="submit" className="btn btn-primary" style={{ width: '100%', marginTop: '1rem' }} disabled={loading}>
                        {loading ? 'Creating Account...' : 'Create Account'}
                    </button>
                </form>

                <p style={{ textAlign: 'center', marginTop: '1.5rem', color: 'var(--text-secondary)' }}>
                    Already have an account? <Link to="/login" style={{ color: 'var(--primary-color)', textDecoration: 'none' }}>Sign In here</Link>
                </p>
            </div>
        </div>
    );
}
