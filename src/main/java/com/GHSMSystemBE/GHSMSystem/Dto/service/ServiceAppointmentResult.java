package com.GHSMSystemBE.GHSMSystem.Dto.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_service_appointment_result")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ServiceAppointmentResult
{
 @Id
   private String id; //auto-generated
    private String consultantId;
    private String  customerId;
    private String servicetypeId;
    private String content;
    private boolean isActive;
}
