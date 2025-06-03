package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "tbl_user")
@Data
@ToString
public class User extends BaseEntity {
    @Column(name = "booking_history", nullable = true, unique = true)
    private String bookingHistory;
    @Column(name = "booking_history", nullable = false, unique = false)
    private float totalSpending= 0;

}
