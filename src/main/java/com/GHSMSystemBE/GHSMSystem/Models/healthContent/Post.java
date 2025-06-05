package com.GHSMSystemBE.GHSMSystem.Models.healthContent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "tbl_post")
public class Post {
    @Id
    @Column(name = "id", nullable = false,unique = true)
    private String id;
    @Column(name = "consultant_id", nullable = false,unique = false)
    private String consultantId;
    @Column(name = "category", nullable = false,unique = false)
    private String category;
    @Column(name = "title", nullable = false,unique = true)
    private String title;
    @Column(name = "content", nullable = false,unique = true)
    private String content;
    @Column(name = "ratings", nullable = true,unique = false)
    private float ratings;
    @Column(name = "feedback", nullable = true,unique = false)
    private String feedback;
}
