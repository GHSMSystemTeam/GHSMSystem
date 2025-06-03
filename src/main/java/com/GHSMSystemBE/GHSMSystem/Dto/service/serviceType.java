package com.GHSMSystemBE.GHSMSystem.Dto.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_service_type")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class serviceType {
    @Id
    private String id; //Syntax ID CS-001
    private String ratingId;
    private String name;
    private String description;
    private float price ;
    private boolean isActive;
}
