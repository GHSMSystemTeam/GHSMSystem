package com.GHSMSystemBE.GHSMSystem.Controller.HealthServiceAPI;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceType;
import com.GHSMSystemBE.GHSMSystem.Services.impl.HealthService.ServiceTypeService;
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

@RestController
@RequestMapping("/api/v1/service-types")
@Tag(name = "Service type Management", description = "API endpoints for Service types management operations")
public class ServiceTypeAPI {
    @Autowired
    private ServiceTypeService service;

    private static final String CURRENT_USER = "TranDucHai2123";
    private static final String CURRENT_DATE = "2025-06-15 07:11:39";

    @Operation(summary = "Get all service types", description = "Retrieve a complete list of all service types from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of service types retrieved successfully")
    })
    @GetMapping("/api/servicetypes")
    public ResponseEntity<List<ServiceType>> getServiceTypeList() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all service types");
        List<ServiceType> serviceTypesList = service.getAll();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + serviceTypesList.size() + " service types in the database");
        return ResponseEntity.ok(serviceTypesList);
    }

    @Operation(summary = "Get active service types", description = "Retrieve a list of all active service types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active service types retrieved successfully")
    })
    @GetMapping("/api/activeservicetypes")
    public ResponseEntity<List<ServiceType>> getActiveServiceTypeList() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all active service types");
        List<ServiceType> activeServiceTypesList = service.getAllActiveServiceType();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + activeServiceTypesList.size() + " active service types in the database");
        return ResponseEntity.ok(activeServiceTypesList);
    }

    @Operation(summary = "Get inactive service types", description = "Retrieve a list of all inactive service types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of inactive service types retrieved successfully")
    })
    @GetMapping("/api/inactiveservicetypes")
    public ResponseEntity<List<ServiceType>> getInActiveServiceTypeList() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all inactive service types");
        List<ServiceType> inactiveServiceTypesList = service.getAllInactiveServiceType();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + inactiveServiceTypesList.size() + " inactive service types in the database");
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
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Searching for service type with ID: " + id);
        ServiceType foundUser = service.getById(id);
        if(foundUser == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find service type with ID: " + id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Service type found with ID: " + id);
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
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to add new service type");

        if (serviceType == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Failed to add service type. Service type is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ServiceType added = service.createServiceType(serviceType);
        if (added == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Failed to create service type - duplicate or validation error");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully added service type with ID: " + added.getId());
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

        System.out.println("LOG: 2025-06-15 08:44:08 - TranDucHai2123 - Attempting to delete service type with ID: " + id);

        ServiceType foundServiceType = service.getById(id);

        if(foundServiceType == null) {
            System.out.println("LOG: 2025-06-15 08:44:08 - TranDucHai2123 - Can't find service type with ID: " + id + " for deletion");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("LOG: 2025-06-15 08:44:08 - TranDucHai2123 - Deleting service type with ID: " + foundServiceType.getId() +
                    ", name: " + foundServiceType.getName());
            service.deleteServiceType(foundServiceType.getId());
            System.out.println("LOG: 2025-06-15 08:44:08 - TranDucHai2123 - Successfully deleted service type with ID: " + foundServiceType.getId());
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
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to update service type with ID: " + id);
        ServiceType found = service.getById(id);
        if (found == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find service type with ID: " + id + " for update");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Updating service type with ID: " + id);
            serviceType.setId(id); // Ensure ID is set correctly
            ServiceType updated = service.editServiceType(serviceType);
            if(updated == null) {
                System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Failed to update service type with ID: " + id);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully updated service type with ID: " + id);
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
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to activate service type with ID: " + id);
        ServiceType found = service.getById(id);
        if (found == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find service type with ID: " + id + " for activation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Activating service type with ID: " + id);
            service.active(found);
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully activated service type with ID: " + id);
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
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to deactivate service type with ID: " + id);
        ServiceType found = service.getById(id);
        if (found == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find service type with ID: " + id + " for deactivation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Deactivating service type with ID: " + id);
            service.deActive(found);
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully deactivated service type with ID: " + id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}