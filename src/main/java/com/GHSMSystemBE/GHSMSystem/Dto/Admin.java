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
@Table(name = "tbl_admin")
@Getter @Setter @ToString @NoArgsConstructor
public class Admin extends BaseEntity{
    private String citizenID;
    private String employmentDetails;
    private String profilePicture;

}
