package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_consultant")
@Getter @Setter @ToString @NoArgsConstructor
public class Consultant extends EmployeeEntity {
  private  int fieldOfExpertly;
  private String licenseDetails;
  private float avgRating;
    private float hourlyRates;
}
