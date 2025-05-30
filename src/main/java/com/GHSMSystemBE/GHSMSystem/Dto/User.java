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
@Table(name = "tbl_user")
@Getter @Setter @ToString @NoArgsConstructor
public class User extends BaseEntity {
    private String bookingHistory;
    private float totalSpending;
    private String profilePicture;

}
