package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor
public class BaseEntity {
    @Id
    private String id; //Primary key

    private String name;
    private String password;
    private Date Birthday;
    private String email;
    private String phone;
}
