package com.GHSMSystemBE.GHSMSystem.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_role")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Main Data object model for User Role")
public class Role {

    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, unique = true)
    private int id; //Primary key

    @Schema(description = "User role: 1=customer, 2=consultant, 3=admin. Must not be empty")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Schema(description = "Role Description. Must not be empty- must be unique")
    @Column(name="description", nullable = true)
    private String description;
}
