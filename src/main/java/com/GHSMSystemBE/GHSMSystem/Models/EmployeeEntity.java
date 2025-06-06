package com.GHSMSystemBE.GHSMSystem.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import java.io.Serializable;

@MappedSuperclass
@Data
@Schema(description = "Base entity for all employee types in the system")
public class EmployeeEntity extends BaseEntity implements Serializable {
    @Schema(description = "Citizen identification number. Must not be empty- must be unique")
    @Column(name = "citizen_id", nullable = false, unique = true)
    private String citizenID;

    @Schema(description = "Employment details including contract information. Must not be empty- must be unique")
    @Column(name = "employment_details", nullable = false, unique = true)
    private String employmentDetails;
}