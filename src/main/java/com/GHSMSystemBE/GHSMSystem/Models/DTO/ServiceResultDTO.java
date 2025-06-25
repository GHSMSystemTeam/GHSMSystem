package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.User;
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
