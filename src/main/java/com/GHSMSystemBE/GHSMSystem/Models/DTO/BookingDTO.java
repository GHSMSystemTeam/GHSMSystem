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

    private User consultantId;

    private User customerId;

    private ServiceType serviceTypeId;

    private Date appointmentDate;

    private String appointmentSlot;

    private int duration;

}
