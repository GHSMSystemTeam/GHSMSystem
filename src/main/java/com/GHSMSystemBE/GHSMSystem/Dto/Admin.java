package com.GHSMSystemBE.GHSMSystem.Dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tbl_admin")
@Data
public class Admin extends EmployeeEntity {
}
