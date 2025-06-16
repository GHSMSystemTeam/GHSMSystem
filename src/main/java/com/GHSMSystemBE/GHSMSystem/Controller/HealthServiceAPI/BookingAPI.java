package com.GHSMSystemBE.GHSMSystem.Controller.HealthServiceAPI;

import com.GHSMSystemBE.GHSMSystem.Services.IBookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@Tag(name = "Booking Service", description = "API endpoints for Booking Servuce management operations")
public class BookingAPI {
}
