package com.GHSMSystemBE.GHSMSystem.Models.HealthService;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_service_type")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(description = "Entity representing a type of service offered in the system")
public class ServiceType {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "Name of the service type. Must not be empty- must be unique")
    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Schema(description = "Detailed description of what this service type offers. Must not be empty")
    @Column(name="description", nullable = false, unique = false)
    private String description;

    @Schema(description = "Price of the service in the system's currency. Must not be empty")
    @Column(name="price", nullable = false, unique = false)
    private float price;

    @Schema(description = "Service type Code")
    @Column(name="service_type_code", nullable=false, unique = false)
    private Integer typeCode;
    
    @Schema(description = "Indicates whether this service type is currently active. Must not be empty- must be unique")
    @Column(name="is_active", nullable = false)
    private boolean isActive;
}