package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor
public class BaseEntity {
    @Id
    private String id; //Primary key
    private String name;
    private String email;
    private String password;
    private int gender;
    private String address;
    private String phone;
    private Date birthDate;
    private Date createdDate;
    private LocalDateTime lastLoginDate;
    private byte[] profilePicture;
    private boolean isActive;
}
