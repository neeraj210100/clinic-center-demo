package com.clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lead {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(length = 500)
    private String message;
    
    @Column(length = 100)
    private String source = "WEBSITE";
    
    @Enumerated(EnumType.STRING)
    private LeadStatus status = LeadStatus.NEW;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public enum LeadStatus {
        NEW, CONTACTED, CONVERTED, LOST
    }
}
