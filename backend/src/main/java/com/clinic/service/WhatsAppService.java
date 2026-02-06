package com.clinic.service;

import com.clinic.model.Appointment;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {
    
    @Value("${twilio.account.sid:}")
    private String accountSid;
    
    @Value("${twilio.auth.token:}")
    private String authToken;
    
    @Value("${twilio.whatsapp.from:}")
    private String serverNumber;
    
    @Value("${twilio.enabled:false}")
    private boolean twilioEnabled;
    
    @PostConstruct
    public void init() {
        if (twilioEnabled && accountSid != null && !accountSid.isEmpty() 
            && authToken != null && !authToken.isEmpty()) {
            Twilio.init(accountSid, authToken);
            System.out.println("Twilio initialized successfully");
        } else {
            System.out.println("Twilio not configured. WhatsApp messages will be logged only.");
        }
    }
    
    public String sendAppointmentConfirmation(Appointment appointment) {
        try {
            String message = buildAppointmentMessage(appointment);
            return sendMessage(false,appointment.getPhoneNumber(), message);
        } catch (Exception e) {
            // Log error but don't fail the appointment creation
            System.err.println("Failed to send WhatsApp message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public String sendAppointment(Appointment appointment) {
        try {
            String message = buildAppointmentMessage(appointment);
            return sendMessage(true,appointment.getPhoneNumber(), message);
        } catch (Exception e) {
            // Log error but don't fail the appointment creation
            System.err.println("Failed to send WhatsApp message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    private String buildAppointmentMessage(Appointment appointment) {
        StringBuilder message = new StringBuilder();
        message.append("🏥 *Appointment Booking*\n\n");
        message.append("Patient Name ").append(appointment.getPatientName()).append(",\n\n");
        message.append("📅 Date & Time: ").append(appointment.getAppointmentDateTime()).append("\n");
        if (appointment.getReason() != null && !appointment.getReason().isEmpty()) {
            message.append("📋 Reason: ").append(appointment.getReason()).append("\n");
        }
        return message.toString();
    }
    
    private String sendMessage(Boolean toServer,String phoneNumber,String messageText) {

        // If Twilio is not enabled or not configured, just log and return mock ID
        if (!twilioEnabled || serverNumber == null || serverNumber.isEmpty()) {
            System.out.println("Twilio not enabled. Message logged only.");
            return "mock_message_id_" + System.currentTimeMillis();
        }
        
        try {
            // Format from number (should be like whatsapp:+14155238886)
            String formattedFrom="";
            String formattedTo="";
            if(toServer) {
                 formattedFrom = formatPhoneNumber(phoneNumber);
                 formattedTo = formatPhoneNumber(serverNumber);
            }else{
                formattedFrom = formatPhoneNumber(serverNumber);
                formattedTo = formatPhoneNumber(phoneNumber);
            }
            // Send message via Twilio
            Message message = Message.creator(
                    new PhoneNumber(formattedTo),
                    new PhoneNumber(formattedFrom),
                    messageText
            ).create();
            
            System.out.println("WhatsApp message sent successfully. SID: " + message.getSid());
            return message.getSid();
            
        } catch (Exception e) {
            System.err.println("Error sending WhatsApp message via Twilio: " + e.getMessage());
            e.printStackTrace();
            // Return null to indicate failure, but don't throw exception
            return null;
        }
    }
    
    /**
     * Format phone number for Twilio WhatsApp
     * Ensures the number is in format: whatsapp:+1234567890
     */
    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        
        // Remove any spaces, dashes, or parentheses
        String cleaned = phoneNumber.replaceAll("[\\s\\-\\(\\)]", "");
        
        // If it doesn't start with whatsapp:, add it
        if (!cleaned.startsWith("whatsapp:")) {
            // If it doesn't start with +, add it
            if (!cleaned.startsWith("+")) {
                cleaned = "+" + cleaned;
            }
            cleaned = "whatsapp:" + cleaned;
        }
        
        return cleaned;
    }
}
