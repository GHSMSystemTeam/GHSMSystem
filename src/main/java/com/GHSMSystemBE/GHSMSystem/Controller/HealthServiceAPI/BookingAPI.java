package com.GHSMSystemBE.GHSMSystem.Controller.HealthServiceAPI;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.BookingDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Services.IBookingService;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthServiceType;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Booking Service", description = "API endpoints for Booking Service management operations")
public class BookingAPI {

    @Autowired
    private IBookingService service;

    @Autowired
    private IUserService userservice;

    @Autowired
    private IHealthServiceType hstservice;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Get all service bookings", description = "Retrieve a complete list of all service bookings from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of service booking retrieved successfully")
    })
    @GetMapping("/api/servicebookings")
    public ResponseEntity<List<ServiceBooking>> getServiceBookingList() {

        List<ServiceBooking> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get all active service bookings", description = "Retrieve a complete list of all active service bookings from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active service booking retrieved successfully")
    })
    @GetMapping("/api/activebookings")
    public ResponseEntity<List<ServiceBooking>> getActiveBookingList() {

        List<ServiceBooking> list = service.getAllActiveBooking();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get all deactive service bookings", description = "Retrieve a complete list of all deactive service bookings from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of deactive service booking retrieved successfully")
    })
    @GetMapping("/api/deactivebookings")
    public ResponseEntity<List<ServiceBooking>> getDeActiveBookingList() {

        List<ServiceBooking> list = service.getAllDeActiveBooking();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get all active service bookings by customer id", description = "Retrieve a complete list of service bookings from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of service booking retrieved successfully")
    })
    @GetMapping("/api/servicebookings/customer/{id}")
    public ResponseEntity<List<ServiceBooking>> getServiceBookingListByCustomerId(
            @PathVariable String id) {

        List<ServiceBooking> list = service.getAllByCustomerId(id);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get all active service bookings by consultant id", description = "Retrieve a complete list of service bookings from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of service booking retrieved successfully")
    })
    @GetMapping("/api/servicebookings/consultant/{id}")
    public ResponseEntity<List<ServiceBooking>> getServiceBookingListByConsultantId(
            @PathVariable String id) {

        List<ServiceBooking> list = service.getAllByConsultantId(id);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get all active service bookings by ServiceType id", description = "Retrieve a complete list of service bookings from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of service booking retrieved successfully")
    })
    @GetMapping("/api/servicebookings/servicetype/{id}")
    public ResponseEntity<List<ServiceBooking>> getServiceBookingListByServiceTypeId(
            @PathVariable int id) {

        List<ServiceBooking> list = service.getAllByServiceTypeId(id);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get a service bookings by id", description = "Retrieve a service bookings from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A service booking retrieved successfully")
    })
    @GetMapping("/api/servicebooking/{id}")
    public ResponseEntity<ServiceBooking> getServiceBookingById(
            @PathVariable String id) {

        ServiceBooking sb = service.getById(id);
        return ResponseEntity.ok(sb);
    }

    @Operation(summary = "Set active to a service booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Set active successfully")
    })
    @PutMapping("/api/servicebooking/active/{id}")
    public ResponseEntity<ServiceBooking> activeServiceBooking(
            @PathVariable String id) {

        ServiceBooking sb = service.getById(id);
        if(service.activeBooking(sb) != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>((HttpStatus.BAD_REQUEST));
    }

    @Operation(summary = "Set deactive to a service booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Set deactive successfully")
    })
    @PutMapping("/api/servicebooking/deactive/{id}")
    public ResponseEntity<ServiceBooking> deactiveServiceBooking(
            @PathVariable String id) {

        ServiceBooking sb = service.getById(id);
        if(service.deActiveBooking(sb) != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>((HttpStatus.BAD_REQUEST));
    }

    @Operation(summary = "Set status to a service booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Set status successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid status")
    })
    @PutMapping("/api/servicebooking/status/{id}/{sn}")
    public ResponseEntity<ServiceBooking> updateBookingStatus(
            @Parameter(description = "Booking ID") @PathVariable String id,
            @Parameter(description = "Status number") @PathVariable int sn) {
        ServiceBooking sb = service.getById(id);
        if(service.updateBookingStatus(sb, sn) != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>((HttpStatus.BAD_REQUEST));
    }

    @Operation(summary = "Add new service booking", description = "Add a new service booking to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created service booking"),
            @ApiResponse(responseCode = "400", description = "Failed to create service booking - your slot booking maybe full")
    })
    @PostMapping("/api/servicebooking")
    public ResponseEntity<ServiceBooking> addServiceBooking(
            @Parameter(description = "Booking data to create") @RequestBody BookingDTO sbDTO,
            HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();
        if (sbDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ServiceBooking sb = service.createServiceBooking(sbDTO, ipAddress);
        if (sb != null) {
            return new ResponseEntity<>(sb, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Add new service testing", description = "Add a new service testing to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created service testing"),
            @ApiResponse(responseCode = "400", description = "Failed to create service booking - your slot booking maybe full")
    })
    @PostMapping("/api/servicetesting")
    public ResponseEntity<ServiceBooking> addServiceTesting(
            @Parameter(description = "Booking data to create") @RequestBody BookingDTO sbDTO) {

        if (sbDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ServiceBooking sb = service.createTesting(sbDTO);
        if (sb != null) {
            return new ResponseEntity<>(sb, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete service booking by ID", description = "Match service booking with ID and delete it from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted service booking"),
            @ApiResponse(responseCode = "404", description = "Service booking with ID not found")
    })
    @DeleteMapping("/api/booking/{id}")
    public ResponseEntity<Void> deleteBookingById(
            @Parameter(description = "Booking's UUID to delete") @PathVariable String id) {

        ServiceBooking sb = service.getById(id);
        if (service.deleteServiceBooking(sb)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/api/booking/slot/{id}")
    public ResponseEntity<ServiceBooking> updateSlot(@PathVariable String id, @RequestBody Integer slot)
    {
        ServiceBooking sb = service.getById(id);
        if(id==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        service.updateTimeSlot(sb,slot);
        if(sb==null)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else
            return ResponseEntity.ok(sb);
    }
}