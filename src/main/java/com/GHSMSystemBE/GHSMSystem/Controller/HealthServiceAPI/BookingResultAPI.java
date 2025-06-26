package com.GHSMSystemBE.GHSMSystem.Controller.HealthServiceAPI;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.BookingDTO;
import com.GHSMSystemBE.GHSMSystem.Models.DTO.ServiceResultDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceResult;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Booking Result", description = "API endpoints for Booking Result management operations")
public class BookingResultAPI {

    @Autowired
    private IHealthResultService hrservice;

    @Operation(summary = "Get all service results", description = "Retrieve a complete list of service results from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of service result retrieved successfully")
    })
    @GetMapping("/api/results")
    public ResponseEntity<List<ServiceResult>> getServiceResultList() {

        List<ServiceResult> list = hrservice.getAll();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get all service results by customer id", description = "Retrieve a complete list of service results from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of service result retrieved successfully")
    })
    @GetMapping("/api/result/customer/{id}")
    public ResponseEntity<List<ServiceResult>> getServiceResultListByCustomerId(
            @PathVariable String id) {

        List<ServiceResult> list = hrservice.getAllByCustomerId(id);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get all service results by consultant id", description = "Retrieve a complete list of service results from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of service result retrieved successfully")
    })
    @GetMapping("/api/result/consultant/{id}")
    public ResponseEntity<List<ServiceResult>> getServiceResultListByConsultantId(
            @PathVariable String id) {

        List<ServiceResult> list = hrservice.getAllByConsultantId(id);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get service results by service booking id", description = "Retrieve a service results from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A service result retrieved successfully")
    })
    @GetMapping("/api/result/{id}")
    public ResponseEntity<ServiceResult> getServiceResultListById(
            @PathVariable String id) {

        ServiceResult sr = hrservice.getById(id);
        return ResponseEntity.ok(sr);
    }

    @GetMapping("/api/result/{id}/pdf")
    @Operation(summary = "Download single booking result record as PDF",
            description = "Generate and download a specific booking result as a PDF document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PDF generated successfully"),
            @ApiResponse(responseCode = "404", description = "Medical record not found"),
            @ApiResponse(responseCode = "500", description = "Error generating PDF")
    })
    public ResponseEntity<ByteArrayResource> downloadMedicalRecordPDF(
            @Parameter(description = "ID of the medical record to download", required = true)
            @PathVariable String id) {

        try {
            ByteArrayResource resource = hrservice.generateMServiceResultPDF(id);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=medical_record_" + id + ".pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Add new service result", description = "Add a new service result to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created service result"),
            @ApiResponse(responseCode = "400", description = "Failed to create service result - validation error")
    })
    @PostMapping("/api/result")
    public ResponseEntity<ServiceResult> addServiceResult(
            @Parameter(description = "Result data to create") @RequestBody ServiceResultDTO srDTO) {

        if (srDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ServiceResult sb = hrservice.createServiceResult(srDTO);

        if (sb != null) {
            return new ResponseEntity<>(sb, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
