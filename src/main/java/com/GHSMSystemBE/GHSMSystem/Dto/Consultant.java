package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
//@Table(name = "")
@Getter @Setter @ToString
public class Consultant {
    @Id
    private String id; //Primary key

    private String name;
    private String password;
    private Date Birthday;
    private String email;
    private String phone;

    public Consultant(String id, String name, String password, Date birthday, String email, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        Birthday = birthday;
        this.email = email;
        this.phone = phone;
    }

    public Consultant(String id, String name, Date birthday, String email, String phone) {
        this.id = id;
        this.name = name;
        Birthday = birthday;
        this.email = email;
        this.phone = phone;
    }

    public Consultant() {
    }
}
