package com.clinic.service;

import com.clinic.dto.LeadRequest;
import com.clinic.model.Lead;
import com.clinic.repository.LeadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeadService {
    
    private final LeadRepository leadRepository;
    private final ExcelExportService excelExportService;
    
    @Value("${google.forms.webhook.url:}")
    private String googleFormsWebhookUrl;
    
    private final WebClient webClient = WebClient.builder().build();
    
    @Transactional
    public Lead createLead(LeadRequest request) {
        Lead lead = new Lead();
        lead.setName(request.getName());
        lead.setEmail(request.getEmail());
        lead.setPhoneNumber(request.getPhoneNumber());
        lead.setMessage(request.getMessage());
        lead.setSource(request.getSource() != null ? request.getSource() : "WEBSITE");
        lead.setStatus(Lead.LeadStatus.NEW);
        
        Lead savedLead = leadRepository.save(lead);
        
        // Submit to Google Forms webhook if configured
        if (googleFormsWebhookUrl != null && !googleFormsWebhookUrl.isEmpty()) {
            submitToGoogleForms(savedLead);
        }
        
        return savedLead;
    }
    
    private void submitToGoogleForms(Lead lead) {
        try {
            // Google Forms webhook integration
            // This would typically be a POST request to a Google Apps Script webhook
            webClient.post()
                    .uri(googleFormsWebhookUrl)
                    .bodyValue(lead)
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(
                            result -> System.out.println("Lead submitted to Google Forms: " + result),
                            error -> System.err.println("Failed to submit to Google Forms: " + error.getMessage())
                    );
        } catch (Exception e) {
            System.err.println("Error submitting to Google Forms: " + e.getMessage());
        }
    }
    
    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }
    
    public Lead getLeadById(Long id) {
        return leadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not found with id: " + id));
    }
    
    @Transactional
    public Lead updateLeadStatus(Long id, Lead.LeadStatus status) {
        Lead lead = getLeadById(id);
        lead.setStatus(status);
        return leadRepository.save(lead);
    }
    
    public byte[] exportLeadsToExcel() {
        try {
            List<Lead> leads = getAllLeads();
            return excelExportService.exportLeadsToExcel(leads);
        } catch (Exception e) {
            throw new RuntimeException("Failed to export leads to Excel", e);
        }
    }
}
