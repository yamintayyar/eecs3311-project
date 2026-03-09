import { Link } from 'react-router-dom';

const Dashboard = ({ authUser }) => {
    return (
        <div className="dashboard-container">
            <h2 className="title">Select Your Role</h2>
            <p className="subtitle">
                Choose a portal to interact with the service booking system.
            </p>

            <div className="dashboard-grid">
                <div className="glass-panel" style={{ opacity: (!authUser || authUser.role === 'CLIENT') ? 1 : 0.5 }}>
                    <h3 style={{ fontSize: '1.5rem', marginBottom: '1rem' }}>Client Portal</h3>
                    <p style={{ color: 'var(--text-secondary)', marginBottom: '2rem' }}>
                        Browse available services, book new sessions with consultants, and process dummy payments for your confirmed bookings.
                    </p>
                    <Link
                        to="/client"
                        className="btn btn-primary"
                        style={{ width: '100%', pointerEvents: authUser?.role === 'CONSULTANT' ? 'none' : 'auto' }}
                    >
                        Enter as Client
                    </Link>
                </div>

                <div className="glass-panel" style={{ opacity: (!authUser || authUser.role === 'CONSULTANT') ? 1 : 0.5 }}>
                    <h3 style={{ fontSize: '1.5rem', marginBottom: '1rem' }}>Consultant Portal</h3>
                    <p style={{ color: 'var(--text-secondary)', marginBottom: '2rem' }}>
                        Review your incoming booking requests, manage your availability, and accept or reject sessions.
                    </p>
                    <Link
                        to="/consultant"
                        className="btn btn-secondary"
                        style={{ width: '100%', pointerEvents: authUser?.role === 'CLIENT' ? 'none' : 'auto' }}
                    >
                        Enter as Consultant
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
