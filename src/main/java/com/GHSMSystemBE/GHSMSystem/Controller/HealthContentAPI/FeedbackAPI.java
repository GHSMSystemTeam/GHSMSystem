package com.GHSMSystemBE.GHSMSystem.Controller.HealthContentAPI;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.FeedbackDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Feedback;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthFeedback;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@Tag(name = "Feedback Management", description = "API endpoints for Feedback management operations")
@CrossOrigin("*")
public class FeedbackAPI {

    @Autowired
    private IHealthFeedback service;

    @GetMapping
    @Operation(summary = "Get all feedback entries", description = "Retrieves a list of all feedback entries")
    public ResponseEntity<List<Feedback>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/active")
    @Operation(summary = "Get active feedback entries", description = "Retrieves all active feedback entries")
    public ResponseEntity<List<Feedback>> getAllActive() {
        return ResponseEntity.ok(service.getAllActiveFeedbacks());
    }

    @GetMapping("/inactive")
    @Operation(summary = "Get inactive feedback entries", description = "Retrieves all inactive feedback entries")
    public ResponseEntity<List<Feedback>> getAllInactive() {
        return ResponseEntity.ok(service.getAllInactiveFeedbacks());
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Get feedback by ID", description = "Retrieves a specific feedback entry by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Feedback not found")
    })
    public ResponseEntity<Feedback> getById(@PathVariable String id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Feedback found = service.getById(id);

        if (found == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(found);
        }
    }

    @PostMapping
    @Operation(summary = "Create new feedback", description = "Creates a new feedback entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid feedback data"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<Feedback> createFeedback(@RequestBody FeedbackDTO dto) {
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Feedback created = service.createFeedback(dto);
        Feedback found = service.getById(created.getId().toString());

        if (found == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(found);
    }

    @PutMapping("/id/{id}")
    @Operation(summary = "Update feedback", description = "Updates an existing feedback entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid feedback data or ID"),
            @ApiResponse(responseCode = "404", description = "Feedback not found")
    })
    public ResponseEntity<Feedback> editFeedback(@RequestBody FeedbackDTO dto, @PathVariable String id) {
        if (dto == null || id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Feedback updated = service.editFeedback(dto, id);

        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(updated);
        }
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Delete feedback", description = "Deletes a feedback entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Feedback not found")
    })
    public ResponseEntity<Void> deleteFeedback(@PathVariable String id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Feedback deleted = service.deleteFeedback(id);

        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/activate/{id}")
    @Operation(summary = "Activate feedback", description = "Activates a feedback entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback activated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Feedback not found")
    })
    public ResponseEntity<Void> activateFeedback(@PathVariable String id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Feedback active = service.active(id);

        if (active == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/deactivate/{id}")
    @Operation(summary = "Deactivate feedback", description = "Deactivates a feedback entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback deactivated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Feedback not found")
    })
    public ResponseEntity<Void> deactivateFeedback(@PathVariable String id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Feedback deactive = service.deActive(id);

        if (deactive == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}