package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    @Schema(description = "Full name of the user. Must not be empty")
    @Column(name="name", nullable = false)
    private String name;

    @Schema(description = "Email address of the user. Must not be empty- must be unique")
    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Schema(description = "User password. Must not be empty")
    @Column(name="password", nullable = false)
    private String password;

    @Schema(description = "Gender of the user. 1=male, 2=female, 3=other. Must not be empty")
    @Column(name="gender", nullable = false)
    private int gender;

    @Schema(description = "Phone number of the user. Must not be empty- must be unique")
    @Column(name="phone", nullable = false, unique = true)
    private String phone;

    //getter setter
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

    public String getPhone() {
        return phone;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
