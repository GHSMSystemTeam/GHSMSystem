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
    @Schema(description = "ID field with syntax format CS-001. Must not be empty- must be unique")
    @Id
    @Column(name="id", nullable = false, unique = true)
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

    @Schema(description = "Indicates whether this service type is currently active. Must not be empty- must be unique")
    @Column(name="is_active", nullable = false, unique = true)
    private boolean isActive;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}