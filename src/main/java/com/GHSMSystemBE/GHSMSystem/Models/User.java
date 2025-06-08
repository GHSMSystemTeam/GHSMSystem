package com.GHSMSystemBE.GHSMSystem.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_user")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Main Data object model for User actor")
public class User implements Serializable {

    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @Column(name="id", nullable = false, unique = true)
    private String id; //Primary key

    @Schema(description = "User role: 1=customer, 2=consultant, 3=admin. Must not be empty")
    @Column(name = "role", nullable = false)
    private int role;

    @Schema(description = "Email address of the user. Must not be empty- must be unique")
    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Schema(description = "User password hash. Must not be empty")
    @Column(name="password_hash", nullable = false)
    private String passwordHash;

    @Schema(description = "Full name of the user. Must not be empty")
    @Column(name="name", nullable = false)
    private String name;

    @Schema(description = "Gender of the user. 1=male, 2=female, 3=other. Must not be empty")
    @Column(name="gender", nullable = false)
    private int gender;

    @Schema(description = "Phone number of the user. Must not be empty- must be unique")
    @Column(name="phone", nullable = false, unique = true)
    private String phone;

    @Schema(description = "Date and time when the user account was created. Must not be empty")
    @Column(name="create_date", nullable = false)
    private LocalDateTime createDate;

    @Schema(description = "Indicates whether the user account is active or not. Must not be empty")
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Schema(description = "Profile picture of the user stored as binary data")
    @Lob
    @Column(name="profile_picture")
    private byte[] profilePicture;

    @Schema(description = "Booking history of a specific user. Can be empty")
    @Column(name = "booking_history")
    private String bookingHistory;

    @Schema(description = "Total spending of a specific user. Cannot be empty")
    @Column(name = "total_spending", nullable = false)
    private float totalSpending = 0;

    // Consultant specific fields

    @Schema(description = "Specialization area of the consultant. Required for consultants")
    @Column(name = "specialization")
    private String specialization;

    @Schema(description = "License details of the consultant. Required for consultants")
    @Column(name = "license_details")
    private String licenseDetails;

    @Schema(description = "Years of experience for consultant")
    @Column(name = "exp_year")
    private Integer expYear;

    @Schema(description = "Average rating of the consultant")
    @Column(name = "avg_rating")
    private Float avgRating;

    @Schema(description = "Description (bio) of the consultant")
    @Column(name = "description")
    private String description;
    
}