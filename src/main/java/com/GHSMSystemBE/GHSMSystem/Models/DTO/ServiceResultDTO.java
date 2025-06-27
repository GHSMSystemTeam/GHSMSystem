package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ServiceResultDTO {

    private String serviceBookingId;

    private String customerID;

    private String content;

}
