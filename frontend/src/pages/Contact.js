import React from 'react';
import './Contact.css';

const Contact = () => {
  return (
    <div className="contact">
      <section className="contact-hero">
        <div className="container">
          <h1>Contact Us</h1>
          <p>Get in touch with Jagatpur Clinic Center</p>
        </div>
      </section>

      <section className="contact-content">
        <div className="container">
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
