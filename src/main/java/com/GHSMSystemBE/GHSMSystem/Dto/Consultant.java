package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "tbl_consultant")
@Getter @Setter @ToString @NoArgsConstructor
public class Consultant extends EmployeeEntity {
  private  int Specialization;
  private String licenseDetails;
  private Date expirationDate;
  private String bio;
  private float avgRating;
  private float hourlyRates;
}
