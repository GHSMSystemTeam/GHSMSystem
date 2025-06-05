package com.GHSMSystemBE.GHSMSystem.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tbl_user")
@Data
@ToString
public class User extends BaseEntity {

    @Column(name = "booking_history", nullable = true, unique = true)
    private String bookingHistory;
    @Column(name = "total_spending", nullable = false, unique = false)
    private float totalSpending= 0;

}
