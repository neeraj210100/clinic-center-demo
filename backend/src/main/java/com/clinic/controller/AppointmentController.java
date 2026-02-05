package com.clinic.controller;

import com.clinic.dto.AppointmentRequest;
import com.clinic.model.Appointment;
import com.clinic.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Appointments", description = "API endpoints for managing patient appointments")
public class AppointmentController {
    
    private final AppointmentService appointmentService;
    
    @Operation(
            summary = "Create a new appointment",
            description = "Creates a new appointment and sends WhatsApp confirmation to the patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment created successfully",
                    content = @Content(schema = @Schema(implementation = Appointment.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        Appointment appointment = appointmentService.createAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }
    
    @Operation(
            summary = "Get all appointments",
            description = "Retrieves a list of all appointments in the system"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved appointments")
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }
    
    @Operation(
            summary = "Get appointment by ID",
            description = "Retrieves a specific appointment by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment found",
                    content = @Content(schema = @Schema(implementation = Appointment.class))),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(
            @Parameter(description = "Appointment ID", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }
    
    @Operation(
            summary = "Update appointment status",
            description = "Updates the status of an existing appointment (PENDING, CONFIRMED, CANCELLED, COMPLETED)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully",
                    content = @Content(schema = @Schema(implementation = Appointment.class))),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(
            @Parameter(description = "Appointment ID", required = true) @PathVariable Long id,
            @Parameter(description = "New status", required = true) @RequestParam Appointment.AppointmentStatus status) {
        return ResponseEntity.ok(appointmentService.updateAppointmentStatus(id, status));
    }
    
    @Operation(
            summary = "Get appointments by status",
            description = "Retrieves all appointments filtered by status"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved appointments")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatus(
            @Parameter(description = "Appointment status", required = true) @PathVariable Appointment.AppointmentStatus status) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(status));
    }
}
