import React from 'react';
import './Services.css';

const Services = () => {
  const services = [
    {
      icon: '🩺',
      title: 'General Consultation',
      description: 'Comprehensive health check-ups and consultations with our experienced doctors.',
      features: ['Health screening', 'Diagnosis', 'Treatment planning', 'Follow-up care']
    },
    {
      icon: '💉',
      title: 'Laboratory Services',
      description: 'State-of-the-art lab facilities for accurate diagnostic testing.',
      features: ['Blood tests', 'Urine analysis', 'Pathology', 'Quick results']
    },
    {
      icon: '🫀',
      title: 'Cardiology',
      description: 'Expert cardiac care and heart health monitoring services.',
      features: ['ECG', 'Echocardiography', 'Cardiac consultation', 'Preventive care']
    },
    {
      icon: '🦴',
      title: 'Orthopedics',
      description: 'Specialized care for bone, joint, and muscle-related conditions.',
      features: ['Fracture care', 'Joint replacement', 'Physical therapy', 'Sports medicine']
    },
    {
      icon: '🧠',
      title: 'Neurology',
      description: 'Advanced neurological assessment and treatment services.',
      features: ['Brain imaging', 'Nerve studies', 'Headache treatment', 'Neurological exams']
    },
    {
      icon: '👶',
      title: 'Pediatrics',
      description: 'Comprehensive healthcare services for infants, children, and adolescents.',
      features: ['Child health', 'Vaccinations', 'Growth monitoring', 'Pediatric consultation']
    },
    {
      icon: '👁️',
      title: 'Ophthalmology',
      description: 'Expert eye care and vision health services.',
      features: ['Eye exams', 'Vision correction', 'Cataract surgery', 'Retina care']
    },
    {
      icon: '🦷',
      title: 'Dental Care',
      description: 'Complete dental health services for the whole family.',
      features: ['Cleanings', 'Fillings', 'Root canals', 'Cosmetic dentistry']
    }
  ];

  return (
    <div className="services">
      <section className="services-hero">
        <div className="container">
          <h1>Our Services</h1>
          <p>Comprehensive healthcare solutions tailored to your needs</p>
        </div>
      </section>

      <section className="services-list">
        <div className="container">
          <div className="services-grid">
            {services.map((service, index) => (
              <div key={index} className="service-card">
                <div className="service-icon">{service.icon}</div>
                <h3>{service.title}</h3>
                <p className="service-description">{service.description}</p>
                <ul className="service-features">
                  {service.features.map((feature, idx) => (
                    <li key={idx}>✓ {feature}</li>
                  ))}
                </ul>
              </div>
            ))}
          </div>
        </div>
      </section>

      <section className="services-cta">
        <div className="container">
          <h2>Need More Information?</h2>
          <p>Contact us to learn more about our services or schedule a consultation</p>
          <a href="/contact" className="btn btn-primary btn-large">
            Contact Us
          </a>
        </div>
      </section>
    </div>
  );
};

export default Services;
