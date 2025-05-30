package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "tbl_consultant")
@Getter @Setter @ToString @NoArgsConstructor
public class Consultant extends BaseEntity {
  private  int fieldOfExperty;
  private String citizenID;
  private String employmentDetails;
  private String licesenseDetails;
  private float avgRating;
    private String profilePicture;
    private float hourlyRates;

}
