package com.GHSMSystemBE.GHSMSystem.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tbl_user")
@Data
@ToString
@Schema(description = "Main Data object model for User actor")
public class User extends BaseEntity {

    @Schema(description = "Booking history of a specific user. Can be empty- must be unique")
    @Column(name = "booking_history", nullable = true, unique = true)
    private String bookingHistory;
    @Schema(description = "Total spending of a specific user. Cannot be empty- doesn't need to be unique")
    @Column(name = "total_spending", nullable = false, unique = false)
    private float totalSpending= 0;

}
