package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "tbl_consultant")
@Data
@ToString
public class Consultant extends EmployeeEntity {
  @Column(name = "specialization", nullable = false, unique = false)
  private  int Specialization;
  @Column(name = "license_details", nullable = false, unique = true)
  private String licenseDetails;
  @Column(name = "expiration_date", nullable =false, unique = false)
  private Date expirationDate;
  @Column(name = "bio", nullable = true, unique = false)
  private String bio;
  @Column(name = "avg_rating", nullable = false, unique = false)
  private float avgRating;
}
