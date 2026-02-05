import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css';

const Home = () => {
  return (
    <div className="home">
      <section className="hero">
        <div className="hero-content">
          <h1>Welcome to Clinic Center</h1>
          <p className="hero-subtitle">Your Trusted Healthcare Partner</p>
          <p className="hero-description">
            We provide comprehensive healthcare services with a focus on patient care,
            modern facilities, and experienced medical professionals.
          </p>
          <div className="hero-buttons">
            <Link to="/contact" className="btn btn-primary">
              Book Appointment
            </Link>
            <Link to="/services" className="btn btn-secondary">
              Our Services
            </Link>
          </div>
        </div>
      </section>

      <section className="features">
        <div className="container">
          <h2>Why Choose Us?</h2>
          <div className="features-grid">
            <div className="feature-card">
              <div className="feature-icon">👨‍⚕️</div>
              <h3>Expert Doctors</h3>
              <p>Experienced and qualified medical professionals dedicated to your health</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">🏥</div>
              <h3>Modern Facilities</h3>
              <p>State-of-the-art equipment and comfortable environment for your care</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">⏰</div>
              <h3>24/7 Availability</h3>
              <p>Round-the-clock emergency services and support when you need us most</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">💊</div>
              <h3>Comprehensive Care</h3>
              <p>Complete healthcare solutions from diagnosis to treatment and follow-up</p>
            </div>
          </div>
        </div>
      </section>

      <section className="cta-section">
        <div className="container">
          <h2>Ready to Take Care of Your Health?</h2>
          <p>Schedule an appointment today and experience quality healthcare</p>
          <Link to="/contact" className="btn btn-primary btn-large">
            Get Started
          </Link>
        </div>
      </section>
    </div>
  );
};

export default Home;
