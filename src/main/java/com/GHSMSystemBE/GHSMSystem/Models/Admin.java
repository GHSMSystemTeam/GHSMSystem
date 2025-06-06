package com.GHSMSystemBE.GHSMSystem.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tbl_admin")
@Data
@Schema(description = "Entity representing an administrator in the system with elevated privileges")
public class Admin extends EmployeeEntity {
}