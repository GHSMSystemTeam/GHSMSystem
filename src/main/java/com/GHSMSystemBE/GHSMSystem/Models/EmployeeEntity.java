package com.GHSMSystemBE.GHSMSystem.Models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import java.io.Serializable;

@MappedSuperclass
@Data
public class EmployeeEntity  extends BaseEntity implements Serializable {
    @Column(name = "citizen_id", nullable = false,unique = true)
    private String citizenID;
    @Column(name = "employment_details", nullable = false,unique = true)
    private String employmentDetails;
}
