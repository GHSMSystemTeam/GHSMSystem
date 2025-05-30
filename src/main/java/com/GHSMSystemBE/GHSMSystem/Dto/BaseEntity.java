package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor
public class BaseEntity {
    @Id
    private String id; //Primary key
    private String name;
    private String password;
    private Date birthday;
    private String email;
    private String phone;
    private String address;
    private int gender;
    private boolean isActive;
    private LocalDateTime lastLogin;
    private byte[] profilePicture;
}
