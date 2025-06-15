package com.GHSMSystemBE.GHSMSystem.Controller;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.CustomerDTO;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.userRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "User Management", description = "API endpoints for user management operations")
public class UserAPI {

    @Autowired
    private IUserService service;

    @Autowired
    private ModelMapper mapper;

    private static final String CURRENT_USER = "TranDucHai2123";
    private static final String CURRENT_DATE = "2025-06-13 07:17:44";

    //Print user list
    @Operation(summary = "Get a list of all users", description = "Retrieve a complete list of available users from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @GetMapping("/api/user")
    public ResponseEntity<List<User>> getUserList() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all users");
        List<User> userList = service.getAll();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + userList.size() + " users in the database");
        return ResponseEntity.ok(userList);
    }

    //print out a list of user is active
    @Operation(summary = "Get a list of active users", description = "Retrieve all active users including both customers and consultants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active users retrieved successfully")
    })
    @GetMapping("/api/activeusers")
    public ResponseEntity<List<User>> getActiveUserList() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all active users");
        List<User> list = service.getAllActiveUser();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " active users in the database");
        return ResponseEntity.ok(list);
    }

    //print out a list of customer
    @Operation(summary = "Get a list of customers", description = "Retrieve all users with customer role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of customers retrieved successfully")
    })
    @GetMapping("/api/customers")
    public ResponseEntity<List<User>> getAllCustomers() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all customers");
        List<User> list = service.getAllCustomer();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " customers in the database");
        return ResponseEntity.ok(list);
    }

    //print out a list of consultant
    @Operation(summary = "Get a list of consultants", description = "Retrieve all users with consultant role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of consultants retrieved successfully")
    })
    @GetMapping("/api/consultants")
    public ResponseEntity<List<User>> getAllConsultants() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all consultants");
        List<User> list = service.getAllConsultant();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " consultants in the database");
        return ResponseEntity.ok(list);
    }

    //print out a list of user with role customer and active status
    @Operation(summary = "Get a list of active customers", description = "Retrieve all active users with customer role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active customers retrieved successfully")
    })
    @GetMapping("/api/activecustomers")
    public ResponseEntity<List<User>> getActiveCustomerList() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all active customers");
        List<User> list = service.getAllActiveCustomer();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " active customers in the database");
        return ResponseEntity.ok(list);
    }

    //print out a list of user with role consultant and active status
    @Operation(summary = "Get a list of active consultants", description = "Retrieve all active users with consultant role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active consultants retrieved successfully")
    })
    @GetMapping("/api/activeconsultants")
    public ResponseEntity<List<User>> getActiveConsultantList() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all active consultants");
        List<User> list = service.getAllActiveConsultant();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " active consultants in the database");
        return ResponseEntity.ok(list);
    }

    //print out a list of user with role customer and deactive status
    @Operation(summary = "Get a list of inactive customers", description = "Retrieve all inactive users with customer role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of inactive customers retrieved successfully")
    })
    @GetMapping("/api/deactivecustomers")
    public ResponseEntity<List<User>> getDeActiveCustomerList() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all deactive users");
        List<User> list = service.getAllDeActiveCustomer();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " deactive users in the database");
        return ResponseEntity.ok(list);
    }

    //print out a list of user with role consultant and deactive status
    @Operation(summary = "Get a list of inactive consultants", description = "Retrieve all inactive users with consultant role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of inactive consultants retrieved successfully")
    })
    @GetMapping("/api/deactiveconsultants")
    public ResponseEntity<List<User>> getDeActiveConsultantList() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all deactive consultants");
        List<User> list = service.getAllDeActiveConsultant();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " deactive consultants in the database");
        return ResponseEntity.ok(list);
    }

    //Find user with matching ID
    @Operation(summary = "Search user by ID", description = "Retrieve user with matching ID, return a user Object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User with ID found",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User with ID doesn't exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/user/id/{id}")
    public ResponseEntity<User> searchUserById(
            @Parameter(description = "User's UUID") @PathVariable String id) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Searching for user with ID: " + id);
        User foundUser = service.getById(id);
        if (foundUser == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find user with ID: " + id);
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - User found: " + foundUser.getName());
            return ResponseEntity.ok(foundUser);
        }
    }

    //Find user with matching Email
    @Operation(summary = "Search user by email", description = "Retrieve user with matching email, return a user Object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User with email found",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User with email doesn't exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/user/email/{email}")
    public ResponseEntity<User> searchUserByEmail(
            @Parameter(description = "User's email address") @PathVariable String email) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Searching for user with email: " + email);
        User foundUser = service.getByEmail(email);
        if (foundUser == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find user with email: " + email);
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - User found: " + foundUser.getName());
            return ResponseEntity.ok(foundUser);
        }
    }

    //Add user
    @Operation(summary = "Add new user", description = "Add new user to the database with proper validation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added user"),
            @ApiResponse(responseCode = "400", description = "Failed to add user - validation error or duplicate email")
    })
    @PostMapping("/api/register")
    public ResponseEntity<Void> registerUser(
            @Parameter(description = "User object to be created/register new", required = true) @RequestBody CustomerDTO cusDTO) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to add new user");

        // mapping DTO -> model
        User uTmp = mapper.map(cusDTO, User.class);

        if (uTmp == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Failed to add new user. User is null");
            return ResponseEntity.badRequest().build();
        }

        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Creating user with name: " + uTmp.getName());
        User saved = service.createUser(uTmp);
        if (saved == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - User with email already exist.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully added new user: " + uTmp.getName());
            uTmp.setId(null);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    //Delete user by ID
    @Operation(summary = "Delete user by ID", description = "Match user with ID and delete user from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted user"),
            @ApiResponse(responseCode = "404", description = "User with ID not found")
    })
    @DeleteMapping("/api/user/id/{id}")
    public ResponseEntity<Void> deleteUserById(
            @Parameter(description = "User's UUID to delete") @PathVariable String id) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to delete user with ID: " + id);
        User foundUser = service.getById(id);
        if (foundUser == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find user with ID: " + id + " for deletion");
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Deleting user: " + foundUser.getName() + " with ID: " + id);
            service.deleteUser(foundUser);
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully deleted user with ID: " + id);
            return ResponseEntity.noContent().build();
        }
    }

    //Delete user by email
    @Operation(summary = "Delete user by email", description = "Match user with email and delete user from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted user"),
            @ApiResponse(responseCode = "404", description = "User with email not found")
    })
    @DeleteMapping("/api/user/email/{email}")
    public ResponseEntity<Void> deleteUserByEmail(
            @Parameter(description = "User's email to delete") @PathVariable String email) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to delete user with email: " + email);
        User foundUser = service.getByEmail(email);
        if (foundUser == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find user with email: " + email + " for deletion");
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Deleting user: " + foundUser.getName() + " with email: " + email);
            service.deleteUser(foundUser);
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully deleted user with email: " + email);
            return ResponseEntity.noContent().build();
        }
    }

    //Update user by email
    @Operation(summary = "Update user by email", description = "Match user with email and update user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully updated user"),
            @ApiResponse(responseCode = "404", description = "User with email not found")
    })
    @PutMapping("/api/user/email/{email}")
    public ResponseEntity<User> updateUserByEmail(
            @Parameter(description = "User's email to update") @PathVariable String email,
            @Parameter(description = "Updated user object") @RequestBody User user) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to update user with email: " + email);
        User foundUser = service.getByEmail(email);
        if (foundUser == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find user with email: " + email + " for update");
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Updating user: " + foundUser.getName() + " with email: " + email);
            service.editUser(user);
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully updated user with email: " + email);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    //Update user by ID
    @Operation(summary = "Update user by ID", description = "Match user with ID and update user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully updated user"),
            @ApiResponse(responseCode = "404", description = "User with ID not found")
    })
    @PutMapping("/api/user/id/{id}")
    public ResponseEntity<User> updateUserById(
            @Parameter(description = "User's UUID to update") @PathVariable String id,
            @Parameter(description = "Updated user object") @RequestBody User user) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to update user with ID: " + id);
        User foundUser = service.getById(id);
        if (foundUser == null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find user with ID: " + id + " for update");
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Updating user: " + foundUser.getName() + " with ID: " + id);
            service.editUser(user);
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully updated user with ID: " + id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    // user Login
    @Operation(summary = "User login", description = "Authenticate user with email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/api/login")
    public ResponseEntity<User> checkLogin(
            @Parameter(description = "User's email address", required = true) @RequestParam String email,
            @Parameter(description = "User's password", required = true) @RequestParam String password) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting login");
        User u = service.checkLogin(email, password);
        if (u != null) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successful login for user: " + u.getName());
            return ResponseEntity.ok(u);
        }
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Failed to login. Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    //Deactive user
    @Operation(summary = "Deactivate user", description = "Deactivate a user account to prevent login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully deactivated user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/api/deactive/{id}")
    public ResponseEntity<User> deActiveUser(
            @Parameter(description = "User's UUID to deactivate") @PathVariable String id) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to deactivate user with ID: " + id);
        User u = service.getById(id);
        if (u != null) {
            service.deActiveUser(u);
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully deactivated user: " + u.getName());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Failed to deactivate user - not found");
        return ResponseEntity.notFound().build();
    }

    //Active user
    @Operation(summary = "Activate user", description = "Activate a user account to allow login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully activated user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/api/active/{id}")
    public ResponseEntity<User> activeUser(
            @Parameter(description = "User's UUID to activate") @PathVariable String id) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to activate user with ID: " + id);
        User u = service.getById(id);
        if (u != null) {
            service.activeUser(u);
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully activated user: " + u.getName());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Failed to activate user - not found");
        return ResponseEntity.notFound().build();
    }
}
