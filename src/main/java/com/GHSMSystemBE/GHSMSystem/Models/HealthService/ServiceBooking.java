package com.GHSMSystemBE.GHSMSystem.Models.HealthService;

import com.GHSMSystemBE.GHSMSystem.Models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_service_booking")
@Data
@ToString
@Schema(description = "Entity representing a service appointment order in the system")
public class ServiceBooking {                                            
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id", nullable = false, unique = true)
    private UUID id;

    @Schema(description = "ID of the consultant assigned to this appointment. Must not be empty")
    @ManyToOne
    @JoinColumn(name="consultant_id", nullable = true, unique = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User consultantId;

    @Schema(description = "ID of the customer who requested this appointment. Must not be empty")
    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false, unique = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User customerId;

    @Schema(description = "ID of the service type for this appointment. Must not be empty")
    @ManyToOne
    @JoinColumn(name="service_type_id", nullable = false, unique = false)
    private ServiceType serviceTypeId;

    @Schema(description = "Date scheduled for the appointment. Must not be empty")
    @Column(name="appointment_date", nullable = false, unique = false)
    private Date appointmentDate;

    @Schema(description = "Indicates booking order slot")
    @Column(name="slot", nullable = true)
    private Integer slot=1; // 5 slot between 7h - 17h

    @Schema(description = "Duration of the appointment in hours. Must not be empty")
    @Column(name="duration", nullable = false, unique = false)
    private int duration; //hours

    @Schema(description = "Date and time when the appointment was created. Must not be empty")
    @Column(name="create_date", nullable = false, unique = false)
    private LocalDateTime createDate;

    @Schema(description = "Description of the appointment and related information. Must not be empty")
    @Column(name="description", nullable = false, unique = false)
    private String description;

    @Schema(description = "Indicates whether the appointment is active. Must not be empty")
    @Column(name="is_active", nullable = false, unique = false)
    private boolean isActive;

    @Schema(description = "Indicates booking order status")
    @Column(name="status", nullable = false, unique = false)
    private int status=0;//0-pending 1-in progress 2-ended

    @Schema(description = "Payment URL for online transaction")
    @Column(name="payment_url", length = 2048)  // Increase length to 2048
    private String paymentUrl;

    @Schema(description = "Current payment status")
    @Column(name="payment_status")
    private String paymentStatus="UNPAID";

    @Schema(description = "Transaction reference ID")
    @Column(name="transaction_id")
    private String transactionId;

}