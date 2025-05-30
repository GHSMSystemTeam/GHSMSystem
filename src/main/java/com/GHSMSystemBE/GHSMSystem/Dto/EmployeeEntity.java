package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class EmployeeEntity  extends BaseEntity{
    private String citizenID;
    private String employmentDetails;
    private int role;
}
