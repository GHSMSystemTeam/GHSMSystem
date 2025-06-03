package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {
    @Id
    @Column(name="id", nullable = false, unique = true)
    private String id; //Primary key
    @Column(name="name", nullable = false, unique = false)
    private String name;
    @Column(name="email", nullable = false, unique = true)
    private String email;
    @Column(name="password", nullable = false, unique = false)
    private String password;
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
}
