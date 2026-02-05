import React, { useState } from 'react';
import { appointmentAPI, leadAPI } from '../services/api';
import './Contact.css';

const Contact = () => {
  const [formType, setFormType] = useState('appointment'); // 'appointment' or 'contact'
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });

  // Appointment form state
  const [appointmentData, setAppointmentData] = useState({
    patientName: '',
    phoneNumber: '',
    email: '',
    appointmentDateTime: '',
    reason: '',
    notes: '',
  });

  // Contact/Lead form state
  const [contactData, setContactData] = useState({
    name: '',
    email: '',
    phoneNumber: '',
    message: '',
  });

  const handleAppointmentChange = (e) => {
    setAppointmentData({
      ...appointmentData,
      [e.target.name]: e.target.value,
    });
  };

  const handleContactChange = (e) => {
    setContactData({
      ...contactData,
      [e.target.name]: e.target.value,
    });
  };

  const handleAppointmentSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage({ type: '', text: '' });

    try {
      const appointmentDateTime = new Date(appointmentData.appointmentDateTime).toISOString();
      const response = await appointmentAPI.create({
        ...appointmentData,
        appointmentDateTime,
      });

      setMessage({
        type: 'success',
        text: 'Appointment booked successfully! You will receive a WhatsApp confirmation shortly.',
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

  const handleContactSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage({ type: '', text: '' });

    try {
      await leadAPI.create({
        ...contactData,
        source: 'WEBSITE',
      });

      setMessage({
        type: 'success',
        text: 'Thank you for contacting us! We will get back to you soon.',
      });

      // Reset form
      setContactData({
        name: '',
        email: '',
        phoneNumber: '',
        message: '',
      });
    } catch (error) {
      setMessage({
        type: 'error',
        text: error.response?.data?.message || 'Failed to send message. Please try again.',
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
          <div className="form-tabs">
            <button
              className={`tab-button ${formType === 'appointment' ? 'active' : ''}`}
              onClick={() => setFormType('appointment')}
            >
              📅 Book Appointment
            </button>
            <button
              className={`tab-button ${formType === 'contact' ? 'active' : ''}`}
              onClick={() => setFormType('contact')}
            >
              💬 Contact Us
            </button>
          </div>

          {message.text && (
            <div className={`message ${message.type}`}>
              {message.text}
            </div>
          )}

          {formType === 'appointment' ? (
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
          ) : (
            <form className="contact-form" onSubmit={handleContactSubmit}>
              <div className="form-group">
                <label htmlFor="contactName">Full Name *</label>
                <input
                  type="text"
                  id="contactName"
                  name="name"
                  value={contactData.name}
                  onChange={handleContactChange}
                  required
                  placeholder="Enter your full name"
                />
              </div>

              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="contactEmail">Email *</label>
                  <input
                    type="email"
                    id="contactEmail"
                    name="email"
                    value={contactData.email}
                    onChange={handleContactChange}
                    required
                    placeholder="your.email@example.com"
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="contactPhone">Phone Number *</label>
                  <input
                    type="tel"
                    id="contactPhone"
                    name="phoneNumber"
                    value={contactData.phoneNumber}
                    onChange={handleContactChange}
                    required
                    placeholder="+1234567890"
                  />
                </div>
              </div>

              <div className="form-group">
                <label htmlFor="contactMessage">Message *</label>
                <textarea
                  id="contactMessage"
                  name="message"
                  value={contactData.message}
                  onChange={handleContactChange}
                  rows="6"
                  required
                  placeholder="Tell us how we can help you..."
                />
              </div>

              <button type="submit" className="btn btn-primary btn-large" disabled={loading}>
                {loading ? 'Sending...' : 'Send Message'}
              </button>
            </form>
          )}

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
