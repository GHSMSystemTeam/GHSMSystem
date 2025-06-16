package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceType;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private String consultantId;

    private String customerId;

    private int serviceTypeId;

    private Date appointmentDate; // YYYY-MM-DD

    private int appointmentSlot;

    private int duration; // hour

}
