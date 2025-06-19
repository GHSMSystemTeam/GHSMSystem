package com.GHSMSystemBE.GHSMSystem.Controller.HealthServiceAPI;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceType;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthServiceType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Service type Management", description = "API endpoints for Service types management operations")
public class ServiceTypeAPI {

    @Autowired
    private IHealthServiceType service;

    @Operation(summary = "Get all service types", description = "Retrieve a complete list of all service types from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of service types retrieved successfully")
    })
    @GetMapping("/api/servicetypes")
    public ResponseEntity<List<ServiceType>> getServiceTypeList() {
        System.out.println("Retrieving all service types");
        List<ServiceType> serviceTypesList = service.getAll();
        System.out.println("Found " + serviceTypesList.size() + " service types in the database");
        return ResponseEntity.ok(serviceTypesList);
    }

    @Operation(summary = "Get active service types", description = "Retrieve a list of all active service types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active service types retrieved successfully")
    })
    @GetMapping("/api/servicetypes/active")
    public ResponseEntity<List<ServiceType>> getActiveServiceTypeList() {
        System.out.println("Retrieving all active service types");
        List<ServiceType> activeServiceTypesList = service.getAllActiveServiceType();
        System.out.println("Found " + activeServiceTypesList.size() + " active service types in the database");
        return ResponseEntity.ok(activeServiceTypesList);
    }

    @Operation(summary = "Get inactive service types", description = "Retrieve a list of all inactive service types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of inactive service types retrieved successfully")
    })
    @GetMapping("/api/servicetypes/inactive")
    public ResponseEntity<List<ServiceType>> getInActiveServiceTypeList() {
        System.out.println("Retrieving all inactive service types");
        List<ServiceType> inactiveServiceTypesList = service.getAllInactiveServiceType();
        System.out.println("Found " + inactiveServiceTypesList.size() + " inactive service types in the database");
        return ResponseEntity.ok(inactiveServiceTypesList);
    }

    @Operation(summary = "Get service type by ID", description = "Retrieve a service type with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service type found",
                    content = @Content(schema = @Schema(implementation = ServiceType.class))),
            @ApiResponse(responseCode = "400", description = "Service type not found")
    })
    @GetMapping("/api/servicetypes/id/{id}")
    public ResponseEntity<ServiceType> getById(
            @Parameter(description = "Service type ID") @PathVariable int id) {
        System.out.println("Searching for service type with ID: " + id);
        ServiceType foundUser = service.getById(id);
        if(foundUser == null) {
            System.out.println("Service type not found with ID: " + id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("Service type found with ID: " + id);
            return ResponseEntity.ok(foundUser);
        }
    }

    @Operation(summary = "Add new service type", description = "Add a new service type to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created service type"),
            @ApiResponse(responseCode = "400", description = "Failed to create service type - validation error")
    })
    @PostMapping("/api/servicetypes")
    public ResponseEntity<ServiceType> addServiceTypes(
            @org.springframework.web.bind.annotation.RequestBody ServiceType serviceType) {
        System.out.println("Attempting to add new service type");

        if (serviceType == null) {
            System.out.println("Service type creation failed - null data provided");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ServiceType added = service.createServiceType(serviceType);
        if (added == null) {
            System.out.println("Failed to create service type - validation error");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("Successfully added service type with ID: " + added.getId());
            return new ResponseEntity<>(added, HttpStatus.CREATED);
        }
    }

    @Operation(summary = "Delete a service type", description = "Delete a service type by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service type successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Service type not found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied")
    })
    @DeleteMapping("/api/servicetypes/{id}")
    public ResponseEntity<Void> deleteServiceTypes(
            @Parameter(description = "ID of the service type to delete", required = true)
            @PathVariable int id) {
        System.out.println("Attempting to delete service type with ID: " + id);
        ServiceType foundServiceType = service.getById(id);

        if(foundServiceType == null) {
            System.out.println("Service type not found for deletion");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("Deleting service type");
            service.deleteServiceType(foundServiceType.getId());
            System.out.println("Successfully deleted service type");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Operation(summary = "Update service type", description = "Update an existing service type by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully updated service type"),
            @ApiResponse(responseCode = "400", description = "Service type not found or validation error")
    })
    @PutMapping("/api/servicetypes/id/{id}")
    public ResponseEntity<ServiceType> editServiceType(
            @Parameter(description = "Updated service type object", required = true)
            @org.springframework.web.bind.annotation.RequestBody ServiceType serviceType,
            @Parameter(description = "Service type ID to update") @PathVariable int id) {
        System.out.println("Attempting to update service type with ID: " + id);
        ServiceType found = service.getById(id);
        if (found == null) {
            System.out.println("Service type not found for update");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("Updating service type");
            serviceType.setId(id); // Ensure ID is set correctly
            ServiceType updated = service.editServiceType(serviceType);
            if(updated == null) {
                System.out.println("Failed to update service type");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                System.out.println("Successfully updated service type");
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        }
    }

    @Operation(summary = "Activate service type", description = "Activate a service type by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully activated service type"),
            @ApiResponse(responseCode = "400", description = "Service type not found")
    })
    @PutMapping("/api/servicetypes/active/{id}")
    public ResponseEntity<ServiceType> activateServiceType(
            @Parameter(description = "Service type ID to activate") @PathVariable int id) {
        System.out.println("Attempting to activate service type with ID: " + id);
        ServiceType found = service.getById(id);
        if (found == null) {
            System.out.println("Service type not found for activation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("Activating service type");
            service.active(found);
            System.out.println("Successfully activated service type");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Operation(summary = "Deactivate service type", description = "Deactivate a service type by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deactivated service type"),
            @ApiResponse(responseCode = "400", description = "Service type not found")
    })
    @PutMapping("/api/servicetypes/deactive/{id}")
    public ResponseEntity<ServiceType> deactivateServiceType(
            @Parameter(description = "Service type ID to deactivate") @PathVariable int id) {
        System.out.println("Attempting to deactivate service type with ID: " + id);
        ServiceType found = service.getById(id);
        if (found == null) {
            System.out.println("Service type not found for deactivation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("Deactivating service type");
            service.deActive(found);
            System.out.println("Successfully deactivated service type");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}