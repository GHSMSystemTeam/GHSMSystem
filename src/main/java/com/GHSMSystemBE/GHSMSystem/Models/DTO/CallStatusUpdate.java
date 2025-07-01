package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallStatusUpdate {

    private Long callId;
    private String status;
    private String message;
    private Long timestamp;
}
