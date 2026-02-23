import React, { useState } from 'react';
import { appointmentAPI } from '../services/api';
import './Contact.css';

const Contact = () => {
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });

  const [appointmentData, setAppointmentData] = useState({
    patientName: '',
    phoneNumber: '',
    email: '',
    appointmentDateTime: '',
    reason: '',
    notes: '',
  });

  const handleAppointmentChange = (e) => {
    setAppointmentData({
      ...appointmentData,
      [e.target.name]: e.target.value,
    });
  };

  const handleAppointmentSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage({ type: '', text: '' });

    try {
      const appointmentDateTime = new Date(appointmentData.appointmentDateTime).toISOString();
      await appointmentAPI.create({
        ...appointmentData,
        appointmentDateTime,
      });

      setMessage({
        type: 'success',
        text: 'Appointment booked successfully! We will contact you to confirm.',
      });

      // Reset form
      setAppointmentData({
        patientName: '',
        phoneNumber: '',
        email: '',
        appointmentDateTime: '',
        reason: '',
        notes: '',
      });
    } catch (error) {
      setMessage({
        type: 'error',
        text: error.response?.data?.message || 'Failed to book appointment. Please try again.',
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="contact">
      <section className="contact-hero">
        <div className="container">
          <h1>Contact Us</h1>
          <p>Get in touch with us or book an appointment</p>
        </div>
      </section>

      <section className="contact-content">
        <div className="container">
          {message.text && (
            <div className={`message ${message.type}`}>
              {message.text}
            </div>
          )}

          <form className="contact-form" onSubmit={handleAppointmentSubmit}>
              <div className="form-group">
                <label htmlFor="patientName">Full Name *</label>
                <input
                  type="text"
                  id="patientName"
                  name="patientName"
                  value={appointmentData.patientName}
                  onChange={handleAppointmentChange}
                  required
                  placeholder="Enter your full name"
                />
              </div>

              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="phoneNumber">Phone Number *</label>
                  <input
                    type="tel"
                    id="phoneNumber"
                    name="phoneNumber"
                    value={appointmentData.phoneNumber}
                    onChange={handleAppointmentChange}
                    required
                    placeholder="+1234567890"
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="email">Email *</label>
                  <input
                    type="email"
                    id="email"
                    name="email"
                    value={appointmentData.email}
                    onChange={handleAppointmentChange}
                    required
                    placeholder="your.email@example.com"
                  />
                </div>
              </div>

              <div className="form-group">
                <label htmlFor="appointmentDateTime">Preferred Date & Time *</label>
                <input
                  type="datetime-local"
                  id="appointmentDateTime"
                  name="appointmentDateTime"
                  value={appointmentData.appointmentDateTime}
                  onChange={handleAppointmentChange}
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="reason">Reason for Visit</label>
                <input
                  type="text"
                  id="reason"
                  name="reason"
                  value={appointmentData.reason}
                  onChange={handleAppointmentChange}
                  placeholder="e.g., General checkup, Consultation"
                />
              </div>

              <div className="form-group">
                <label htmlFor="notes">Additional Notes</label>
                <textarea
                  id="notes"
                  name="notes"
                  value={appointmentData.notes}
                  onChange={handleAppointmentChange}
                  rows="4"
                  placeholder="Any additional information you'd like to share..."
                />
              </div>

              <button type="submit" className="btn btn-primary btn-large" disabled={loading}>
                {loading ? 'Booking...' : 'Book Appointment'}
              </button>
            </form>

          <div className="contact-info">
            <div className="info-card">
              <div className="info-icon">📍</div>
              <h3>Address</h3>
              <p>123 Healthcare Street<br />Medical District, City 12345</p>
            </div>
            <div className="info-card">
              <div className="info-icon">📞</div>
              <h3>Phone</h3>
              <p>+1 (234) 567-8900<br />+1 (234) 567-8901</p>
            </div>
            <div className="info-card">
              <div className="info-icon">✉️</div>
              <h3>Email</h3>
              <p>info@cliniccenter.com<br />appointments@cliniccenter.com</p>
            </div>
            <div className="info-card">
              <div className="info-icon">🕒</div>
              <h3>Hours</h3>
              <p>Mon-Fri: 8:00 AM - 8:00 PM<br />Sat-Sun: 9:00 AM - 5:00 PM</p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Contact;
