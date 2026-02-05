package com.clinic.controller;

import com.clinic.dto.LeadRequest;
import com.clinic.model.Lead;
import com.clinic.service.LeadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Leads", description = "API endpoints for managing leads and contact inquiries")
public class LeadController {
    
    private final LeadService leadService;
    
    @Operation(
            summary = "Create a new lead",
            description = "Creates a new lead from contact form submission and optionally submits to Google Forms"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lead created successfully",
                    content = @Content(schema = @Schema(implementation = Lead.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Lead> createLead(@Valid @RequestBody LeadRequest request) {
        Lead lead = leadService.createLead(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(lead);
    }
    
    @Operation(
            summary = "Get all leads",
            description = "Retrieves a list of all leads in the system"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved leads")
    @GetMapping
    public ResponseEntity<List<Lead>> getAllLeads() {
        return ResponseEntity.ok(leadService.getAllLeads());
    }
    
    @Operation(
            summary = "Get lead by ID",
            description = "Retrieves a specific lead by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lead found",
                    content = @Content(schema = @Schema(implementation = Lead.class))),
            @ApiResponse(responseCode = "404", description = "Lead not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Lead> getLeadById(
            @Parameter(description = "Lead ID", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(leadService.getLeadById(id));
    }
    
    @Operation(
            summary = "Update lead status",
            description = "Updates the status of an existing lead (NEW, CONTACTED, CONVERTED, LOST)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully",
                    content = @Content(schema = @Schema(implementation = Lead.class))),
            @ApiResponse(responseCode = "404", description = "Lead not found")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<Lead> updateLeadStatus(
            @Parameter(description = "Lead ID", required = true) @PathVariable Long id,
            @Parameter(description = "New status", required = true) @RequestParam Lead.LeadStatus status) {
        return ResponseEntity.ok(leadService.updateLeadStatus(id, status));
    }
    
    @Operation(
            summary = "Export leads to Excel",
            description = "Exports all leads to an Excel file (.xlsx) for download"
    )
    @ApiResponse(responseCode = "200", description = "Excel file generated successfully",
            content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportLeadsToExcel() {
        byte[] excelData = leadService.exportLeadsToExcel();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "leads_export.xlsx");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(excelData);
    }
}
