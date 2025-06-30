package com.GHSMSystemBE.GHSMSystem.Controller.HealthContentAPI;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.RatingDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Rating;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthRating;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@Tag(name = "Rating Management", description = "API endpoints for Rating management operations")
public class RatingAPI {

    @Autowired
    private IHealthRating service;

    @Operation(summary = "Get all ratings", description = "Retrieves a list of all ratings in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all ratings")
    })
    @GetMapping("/api/rating")
    public ResponseEntity<List<Rating>> getAll() {
        List<Rating> ratings = service.getAll();
        return ResponseEntity.ok(ratings);
    }

    @Operation(summary = "Get all active ratings", description = "Retrieves all ratings with active status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all active ratings")
    })
    @GetMapping("/api/rating/active")
    public ResponseEntity<List<Rating>> getAllActive() {
        List<Rating> activeRatings = service.getAllActiveRatings();
        return ResponseEntity.ok(activeRatings);
    }

    @Operation(summary = "Get all inactive ratings", description = "Retrieves all ratings with inactive status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all inactive ratings")
    })
    @GetMapping("/api/rating/inactive")
    public ResponseEntity<List<Rating>> getAllInactive() {
        List<Rating> inactiveRatings = service.getAllInactiveRatings();
        return ResponseEntity.ok(inactiveRatings);
    }

    @Operation(summary = "Get rating by ID", description = "Retrieves a specific rating by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the rating"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Rating not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/rating/id/{id}")
    public ResponseEntity<Rating> getById(
            @Parameter(description = "ID of the rating to retrieve", required = true)
            @PathVariable String id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Rating rating = service.getById(id);
            if (rating == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(rating);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Activate a rating", description = "Changes a rating's status to active")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating successfully activated"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/api/rating/activate/id/{id}")
    public ResponseEntity<Rating> activateRating(
            @Parameter(description = "ID of the rating to activate", required = true)
            @PathVariable String id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Rating activate = service.active(id);
            if (activate == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } else {
                return ResponseEntity.ok(activate);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Deactivate a rating", description = "Changes a rating's status to inactive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating successfully deactivated"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/api/rating/deactivate/id/{id}")
    public ResponseEntity<Rating> deactivateRating(
            @Parameter(description = "ID of the rating to deactivate", required = true)
            @PathVariable String id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Rating activate = service.deActive(id);
            if (activate == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } else {
                return ResponseEntity.ok(activate);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get ratings by user ID", description = "Retrieves all ratings submitted by a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ratings"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/rating/userID/{id}")
    public ResponseEntity<List<Rating>> getByUser(
            @Parameter(description = "ID of the user whose ratings to retrieve", required = true)
            @PathVariable String userID) {
        if (userID == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Rating> list = service.findByUser(userID);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get ratings by booking ID", description = "Retrieves all ratings associated with a specific service booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ratings"),
            @ApiResponse(responseCode = "400", description = "Invalid booking ID supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/rating/bookingId/{id}")
    public ResponseEntity<List<Rating>> getByServiceBooking(
            @Parameter(description = "ID of the service booking to retrieve ratings for", required = true)
            @PathVariable String bookingID) {
        if (bookingID == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Rating> list = service.findByServiceBooking(bookingID);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get ratings within a score range",
            description = "Retrieves all ratings with scores between the specified minimum and maximum values")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ratings in the specified range"),
            @ApiResponse(responseCode = "400", description = "Invalid range parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/rating/range")
    public ResponseEntity<List<Rating>> getByRange(
            @Parameter(description = "Minimum score value (must be > 0 and < 6)", required = true)
            @RequestBody Float min,
            @Parameter(description = "Maximum score value (must be > 0 and < 6)", required = true)
            @RequestBody Float max) {
        if (min <= 0 || max <= 0 || min >= 6 || max >= 6) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Rating> list = service.findInRange(min, max);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get ratings for a consultant within a score range",
            description = "Retrieves all ratings for a specific consultant with scores between the specified minimum and maximum values")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ratings"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/rating/range/consultantId/{id}")
    public ResponseEntity<List<Rating>> getByUserId(
            @Parameter(description = "Minimum score value (must be > 0 and < 6)", required = true)
            @RequestBody Float min,
            @Parameter(description = "Maximum score value (must be > 0 and < 6)", required = true)
            @RequestBody Float max,
            @Parameter(description = "ID of the consultant", required = true)
            @PathVariable String consultantId) {
        if (min <= 0 || max <= 0 || min >= 6 || max >= 6 || consultantId == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Rating> list = service.findForConsultantInRange(min, max, consultantId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Create a new rating", description = "Creates a new rating in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/api/rating")
    public ResponseEntity<Rating> createRating(
            @RequestBody RatingDTO dto) {
        System.out.println("\nRATING"+dto);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Rating rating = service.createRating(dto);
            if (rating == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.ok(rating);
        }
    }

    @Operation(summary = "Delete a rating", description = "Deletes a rating by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied")
    })
    @DeleteMapping("/api/rating/id/{id}")
    public ResponseEntity<Rating> deleteRating(
            @Parameter(description = "ID of the rating to delete", required = true)
            @PathVariable String id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(service.deleteRating(id));
        }
    }

    @Operation(summary = "Update a rating", description = "Updates an existing rating by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/api/rating/id/{id}")
    public ResponseEntity<Rating> updateRating(
            @Parameter(description = "ID of the rating to update", required = true)
            @PathVariable String id,
            @Parameter(description = "Updated rating data", required = true)
            @RequestBody RatingDTO dto) {
        if (id == null || dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Rating updated = service.editRating(dto, id);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            return ResponseEntity.ok(updated);
        }
    }
}