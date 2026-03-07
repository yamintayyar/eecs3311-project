import { useState, useEffect } from 'react';
import axios from 'axios';

const API_URL = 'http://localhost:8080';

export default function ClientView({ authUser }) {
    const [bookings, setBookings] = useState([]);
    const [services] = useState([
        { id: 1, name: 'Initial Consultation', price: 150 },
        { id: 2, name: 'Follow-up Session', price: 100 },
        { id: 3, name: 'Premium Package', price: 300 }
    ]);

    const [selectedService, setSelectedService] = useState(services[0].id);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchBookings();
    }, []);

    const fetchBookings = async () => {
        try {
            const response = await axios.get(`${API_URL}/bookings`);
            setBookings(response.data);
        } catch (err) {
            console.error("Error fetching bookings:", err);
            // Fallback empty list or ignore if backend isn't up
        }
    };

    const handleBookSession = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);
        try {
            // Dynamic fetch of consultant/service for demo
            const consultantsRes = await axios.get(`${API_URL}/consultants`);
            const firstConsultant = consultantsRes.data[0];

            if (!firstConsultant) {
                throw new Error("No consultants found. Please register a consultant first!");
            }

            const consultantId = firstConsultant.id;
            const serviceId = firstConsultant.services[0]?.id;
            const slotId = firstConsultant.availabilities[0]?.id;

            if (!serviceId || !slotId) {
                throw new Error("Consultant has no services or slots. This shouldn't happen with demo seeding!");
            }

            const payload = {
                clientId: authUser.id,
                serviceId: serviceId.toString(),
                consultantId: consultantId,
                slotIds: [slotId]
            };
            await axios.post(`${API_URL}/bookings`, payload);
            alert('Booking request sent to consultant!');
            fetchBookings();
        } catch (err) {
            setError('Failed to book session. Please ensure Backend is running.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handlePayment = async (bookingId) => {
        try {
            // Assuming a PaymentController exists or we simulate it here based on Phase 1 prompt.
            // E.g. POST /payments or just a simulation local state change for frontend demo
            alert(`Simulating payment for booking ${bookingId} via Credit Card Strategy... Payment Successful!`);
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div className="client-view">
            <h2 className="title">Client Portal</h2>
            <p className="subtitle">Book new services and manage your ongoing appointments.</p>

            {error && <div style={{ color: 'var(--danger-color)', marginBottom: '1rem' }}>{error}</div>}

            <div className="dashboard-grid">
                {/* Booking Form */}
                <div className="glass-panel">
                    <h3 style={{ marginBottom: '1.5rem', fontSize: '1.25rem' }}>Book a Session</h3>
                    <form onSubmit={handleBookSession}>
                        <div className="form-group">
                            <label className="form-label">Select Service</label>
                            <select
                                className="form-select"
                                value={selectedService}
                                onChange={(e) => setSelectedService(e.target.value)}
                            >
                                {services.map(s => (
                                    <option key={s.id} value={s.id}>{s.name} - ${s.price}</option>
                                ))}
                            </select>
                        </div>

                        {/* Client ID is now implicitly pulled from authUser state */}

                        <button type="submit" className="btn btn-primary" disabled={loading}>
                            {loading ? 'Submitting...' : 'Request Booking'}
                        </button>
                    </form>
                </div>

                {/* My Bookings List */}
                <div className="glass-panel">
                    <h3 style={{ marginBottom: '1.5rem', fontSize: '1.25rem' }}>My Bookings</h3>
                    {bookings.length === 0 ? (
                        <p style={{ color: 'var(--text-secondary)' }}>No bookings found. Try requesting one!</p>
                    ) : (
                        <div className="item-list">
                            {bookings.map(booking => (
                                <div key={booking.bookingId} className="list-item">
                                    <div className="item-content">
                                        <h4 style={{ fontSize: '1.1rem', marginBottom: '0.5rem' }}>Booking #{booking.bookingId?.substring(0, 8)}</h4>
                                        <p>Status: <span style={{ fontWeight: 600, color: 'var(--primary-color)' }}>{booking.state?.status || 'PENDING'}</span></p>
                                        {booking.price && <p>Price: ${booking.price}</p>}
                                    </div>
                                    <div>
                                        {/* If state happens to be "Confirmed", show a Pay button as per Phase 1 logic */}
                                        <button
                                            onClick={() => handlePayment(booking.bookingId)}
                                            className="btn btn-success"
                                            style={{ padding: '0.4rem 0.8rem', fontSize: '0.85rem' }}
                                        >
                                            Pay Now
                                        </button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                    <div style={{ marginTop: '1rem' }}>
                        <button onClick={fetchBookings} className="btn btn-secondary" style={{ padding: '0.4rem 0.8rem', fontSize: '0.85rem' }}>Refresh</button>
                    </div>
                </div>
            </div>
        </div>
    );
}
