package com.clinic.service;

import com.clinic.dto.AppointmentRequest;
import com.clinic.model.Appointment;
import com.clinic.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final WhatsAppService whatsAppService;
    
    @Transactional
    public Appointment createAppointment(AppointmentRequest request) {
        Appointment appointment = new Appointment();
        appointment.setPatientName(request.getPatientName());
        appointment.setPhoneNumber(request.getPhoneNumber());
        appointment.setEmail(request.getEmail());
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setReason(request.getReason());
        appointment.setNotes(request.getNotes());
        appointment.setStatus(Appointment.AppointmentStatus.PENDING);
        
        Appointment savedAppointment = appointmentRepository.save(appointment);
        
        // Send WhatsApp confirmation
        String messageId = whatsAppService.sendAppointmentConfirmation(savedAppointment);
        if (messageId != null) {
            savedAppointment.setWhatsappMessageId(messageId);
            savedAppointment = appointmentRepository.save(savedAppointment);
        }
        
        return savedAppointment;
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }
    
    @Transactional
    public Appointment updateAppointmentStatus(Long id, Appointment.AppointmentStatus status) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
    
    public List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }
}
