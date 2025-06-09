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

    @Schema(description = "User password. Must not be empty")
    @Column(name="password", nullable = false)
    private String password;

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

    @Schema(description = "BirthDate of the user.")
    @Column(name="birthDate")
    private Date birthDate;

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

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }

    public String getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(String bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getExpYear() {
        return expYear;
    }

    public void setExpYear(Integer expYear) {
        this.expYear = expYear;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLicenseDetails() {
        return licenseDetails;
    }

    public void setLicenseDetails(String licenseDetails) {
        this.licenseDetails = licenseDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public float getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(float totalSpending) {
        this.totalSpending = totalSpending;
    }
}