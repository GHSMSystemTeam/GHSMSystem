package com.GHSMSystemBE.GHSMSystem.Dto.healthContent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "tbl_post_category")
public class PostCategory {
    @Id
    private String id;

    @Column(name = "name")
    private String name;
    

    @Column(name = "is_active")
    private Boolean isActive;
}