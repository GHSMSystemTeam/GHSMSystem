package com.GHSMSystemBE.GHSMSystem.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "tbl_consultant")
@Data
@ToString
@Schema(description = "Entity representing a consultant in the system")
public class Consultant extends EmployeeEntity {
  @Schema(description = "Specialization area of the consultant. Must not be empty")
  @Column(name = "specialization", nullable = false, unique = false)
  private int Specialization;

  @Schema(description = "License details of the consultant. Must not be empty- must be unique")
  @Column(name = "license_details", nullable = false, unique = true)
  private String licenseDetails;

  @Schema(description = "Expiration date of the consultant's license. Must not be empty")
  @Column(name = "expiration_date", nullable = false, unique = false)
  private Date expirationDate;

  @Schema(description = "Biographical information about the consultant")
  @Column(name = "bio", nullable = true, unique = false)
  private String bio;

  @Schema(description = "Average rating of the consultant. Must not be empty")
  @Column(name = "avg_rating", nullable = false, unique = false)
  private float avgRating;
}