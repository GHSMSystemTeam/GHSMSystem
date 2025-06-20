package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import java.time.LocalDateTime;

public class RatingDTO {
    private String customerId;
    private String consultantId;
    private String serviceBookingId;
    private String title;
    private float rating;
    private String content;
    private LocalDateTime createDate;
    private boolean isPublic;
}
