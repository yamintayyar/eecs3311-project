import { useState, useEffect } from 'react';
import axios from 'axios';

const API_URL = 'http://localhost:8080';

export default function ConsultantView({ authUser }) {
    const [bookings, setBookings] = useState([]);
    const [loadingAction, setLoadingAction] = useState(null);

    useEffect(() => {
        fetchBookings();
    }, []);

    const fetchBookings = async () => {
        try {
            const response = await axios.get(`${API_URL}/bookings`);
            setBookings(response.data);
        } catch (err) {
            console.error("Error fetching bookings:", err);
        }
    };

    const handleAction = async (bookingId, action) => {
        setLoadingAction(bookingId);
        try {
            // For Phase 1, we simulate the action if the backend lacks the endpoint
            try {
                await axios.put(`${API_URL}/bookings/${bookingId}/${action}`);
                alert(`Booking ${action}ed successfully!`);
            } catch (err) {
                if (err.response?.status === 404) {
                    alert(`[Simulation] Booking ID: ${bookingId} has been ${action}ed. (Endpoint not yet implemented in backend)`);
                } else {
                    throw err;
                }
            }
            fetchBookings();
        } catch (err) {
            console.error(err);
            alert(`[Simulation] Booking ID: ${bookingId} has been ${action}ed. (Backend unavailable or not fully implemented)`);
        } finally {
            setLoadingAction(null);
        }
    };

    return (
        <div className="consultant-view">
            <h2 className="title">Consultant Portal</h2>
            <p className="subtitle">Review and manage client booking requests.</p>

            <div className="glass-panel">
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem' }}>
                    <h3 style={{ fontSize: '1.25rem' }}>All Booking Requests</h3>
                    <button onClick={fetchBookings} className="btn btn-secondary" style={{ padding: '0.4rem 0.8rem', fontSize: '0.85rem' }}>Refresh</button>
                </div>

                {bookings.length === 0 ? (
                    <p style={{ color: 'var(--text-secondary)' }}>No bookings to review. Please ensure the backend is running.</p>
                ) : (
                    <div className="item-list">
                        {bookings.map(booking => (
                            <div key={booking.bookingId} className="list-item">
                                <div className="item-content">
                                    <h4 style={{ fontSize: '1.1rem', marginBottom: '0.5rem' }}>Client: {booking.clientId?.substring(0, 8) || 'Unknown'}</h4>
                                    <p style={{ marginBottom: '0.25rem' }}>Booking ID: {booking.bookingId}</p>
                                    <p>Status: <span className={`status-badge ${booking.state?.status?.toLowerCase() === 'pending' ? 'status-pending' : booking.state?.status?.toLowerCase() === 'confirmed' ? 'status-confirmed' : ''}`}>{booking.state?.status || 'PENDING'}</span></p>
                                </div>

                                {(!booking.state || booking.state?.status?.toLowerCase() === 'pending' || booking.state?.status === 'PendingPaymentState') && (
                                    <div style={{ display: 'flex', gap: '0.5rem' }}>
                                        <button
                                            onClick={() => handleAction(booking.bookingId, 'confirm')}
                                            disabled={loadingAction === booking.bookingId}
                                            className="btn btn-success"
                                            style={{ padding: '0.4rem 0.8rem', fontSize: '0.85rem' }}
                                        >
                                            Accept
                                        </button>
                                        <button
                                            onClick={() => handleAction(booking.bookingId, 'reject')}
                                            disabled={loadingAction === booking.bookingId}
                                            className="btn btn-danger"
                                            style={{ padding: '0.4rem 0.8rem', fontSize: '0.85rem' }}
                                        >
                                            Reject
                                        </button>
                                    </div>
                                )}
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}
