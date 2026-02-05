package com.clinic.service;

import com.clinic.model.Appointment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsAppService {
    
    @Value("${whatsapp.api.token}")
    private String apiToken;
    
    @Value("${whatsapp.phone.number}")
    private String phoneNumber;
    
    public String sendAppointmentConfirmation(Appointment appointment) {
        try {
            String message = buildAppointmentMessage(appointment);
            return sendMessage(appointment.getPhoneNumber(), message);
        } catch (Exception e) {
            // Log error but don't fail the appointment creation
            System.err.println("Failed to send WhatsApp message: " + e.getMessage());
            return null;
        }
    }
    
    private String buildAppointmentMessage(Appointment appointment) {
        StringBuilder message = new StringBuilder();
        message.append("🏥 *Appointment Confirmation*\n\n");
        message.append("Dear ").append(appointment.getPatientName()).append(",\n\n");
        message.append("Your appointment has been confirmed:\n");
        message.append("📅 Date & Time: ").append(appointment.getAppointmentDateTime()).append("\n");
        if (appointment.getReason() != null && !appointment.getReason().isEmpty()) {
            message.append("📋 Reason: ").append(appointment.getReason()).append("\n");
        }
        message.append("\nPlease arrive 10 minutes early.\n");
        message.append("If you need to reschedule, please contact us.\n\n");
        message.append("Thank you!");
        return message.toString();
    }
    
    private String sendMessage(String toPhoneNumber, String message) {
        // Note: This is a placeholder implementation
        // For production, you'll need to use WhatsApp Business API
        // or a service like Twilio, MessageBird, etc.
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("messaging_product", "whatsapp");
        payload.put("to", toPhoneNumber);
        payload.put("type", "text");
        
        Map<String, String> text = new HashMap<>();
        text.put("body", message);
        payload.put("text", text);
        
        // This would make the actual API call in production
        // For demo purposes, we'll just return a mock message ID
        System.out.println("WhatsApp Message to " + toPhoneNumber + ": " + message);
        return "mock_message_id_" + System.currentTimeMillis();
    }
    
    public String sendQuickMessage(String phoneNumber, String message) {
        return sendMessage(phoneNumber, message);
    }
}
