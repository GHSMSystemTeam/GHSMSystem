package com.GHSMSystemBE.GHSMSystem.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    @Id
    @Column(name="id", nullable = false, unique = true)
    private String id; //Primary key

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="name", nullable = false, unique = false)
    private String name;

    @Column(name="gender", nullable = true, unique = false)
    private int gender;

    @Column(name="address", nullable = true, unique = false)
    private String address;

    @Column(name="phone", nullable = false, unique = true)
    private String phone;

    @Column(name="birth_date", nullable = true, unique = false)
    private Date birthDate;

    @Column(name="create_date", nullable = false, unique =false)
    private LocalDateTime createDate;

    @Column(name="last_login_date", nullable = false, unique =false)
    private LocalDateTime lastLoginDate;

    @Lob
    @Column(name="profile_picture", nullable = true, unique = false)
    private byte[] profilePicture;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
