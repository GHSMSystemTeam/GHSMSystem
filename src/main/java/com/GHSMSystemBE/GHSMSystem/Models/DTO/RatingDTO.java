package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@ToString
@NoArgsConstructor
public class RatingDTO {
    private String customerId;
    private String consultantId;
    private String serviceBookingId;
    private String title;
    private Float rating;
    private String content;
    private LocalDateTime createDate;
    private Boolean isPublic;
}
