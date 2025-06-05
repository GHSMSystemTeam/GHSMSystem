package com.GHSMSystemBE.GHSMSystem.Dto.heathservice;

import jakarta.persistence.Column;
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
    @Column(name="id", nullable = false, unique = true)
    private String id; //Syntax ID CS-001
    @Column(name="rating_id", nullable = false, unique = true)
    private String ratingId;
    @Column(name="name", nullable = false, unique = true)
    private String name;
    @Column(name="description", nullable = false, unique = false)
    private String description;
    @Column(name="price", nullable = false, unique = false)
    private float price ;
    @Column(name="is_active", nullable = false, unique = true)
    private boolean isActive;
}
