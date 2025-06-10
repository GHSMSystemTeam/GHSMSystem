package com.GHSMSystemBE.GHSMSystem.Controller;

import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.userRepo;
import com.GHSMSystemBE.GHSMSystem.Services.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserAPI {


    @Autowired
    private UserService service;

    private static final String CURRENT_USER = "User";
    private static final String CURRENT_DATE = "LOG:DATE";

    //Print user list
    @Operation(summary = "Get a list of user" , description = "Retrieve a list of available users from the Database")
    @GetMapping("/api/user")
    public ResponseEntity<List<User>> getUserList()
    {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all users");
        List<User> userList  = service.getAll();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + userList.size() + " users in the database");
        return ResponseEntity.ok(userList);
    }

    //print out a list of user is active
    @Operation(summary = "Get a list of active user (include customer and consultant)" , description = "Retrieve a list of active users (include customer and consultant)")
    @GetMapping("/api/activeusers")
    public ResponseEntity<List<User>> getActiveUserList(){
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all active users");
        List<User> list = service.getAllActiveUser();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " active users in the database");
        return ResponseEntity.ok(list);
    }

    //print out a list of customer
    @Operation(summary = "Get a list of customer" , description = "Retrieve a list of customer")
    @GetMapping("/api/customers")
    public ResponseEntity<List<User>> getAllCustomers(){
        List<User> list = service.getAllCustomer();
        return ResponseEntity.ok(list);
    }

    //print out a list of consultant
    @Operation(summary = "Get a list of consultant" , description = "Retrieve a list of consultant")
    @GetMapping("/api/consultants")
    public ResponseEntity<List<User>> getAllConsultants(){
        List<User> list = service.getAllConsultant();
        return ResponseEntity.ok(list);
    }

    //print out a list of user with role customer and active status
    @Operation(summary = "Get a list of active customer" , description = "Retrieve a list of active customer")
    @GetMapping("/api/activecustomers")
    public ResponseEntity<List<User>> getActiveCustomerList(){
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all active customers");
        List<User> list = service.getAllActiveCustomer();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " active customers in the database");
        return ResponseEntity.ok(list);
    }

    //print out a list of user with role consultant and active status
    @Operation(summary = "Get a list of active consultants" , description = "Retrieve a list of active consultant")
    @GetMapping("/api/activeconsultants")
    public ResponseEntity<List<User>> getActiveConsultantList(){
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all active consultants");
        List<User> list = service.getAllActiveConsultant();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " active consultants in the database");
        return ResponseEntity.ok(list);
    }

    //print out a list of user with role customer and deactive status
    @Operation(summary = "Get a list of deactive customer" , description = "Retrieve a list of deactive customer")
    @GetMapping("/api/deactivecustomers")
    public ResponseEntity<List<User>> getDeActiveCustomerList(){
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all deactive users");
        List<User> list = service.getAllDeActiveCustomer();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " deactive users in the database");
        return ResponseEntity.ok(list);
    }

    //print out a list of user with role consultant and deactive status
    @Operation(summary = "Get a list of deactive consultants" , description = "Retrieve a list of active consultants")
    @GetMapping("/api/deactiveconsultants")
    public ResponseEntity<List<User>> getDeActiveConsultantList(){
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all deactive consultants");
        List<User> list = service.getAllDeActiveConsultant();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " deactive consultants in the database");
        return ResponseEntity.ok(list);
    }

    //Find user with matching ID
    @Operation(summary = "Search user by matching ID", description = "Retrieve user with matching ID, return a user Object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User with Id found"),
            @ApiResponse(responseCode = "404", description = "User with Id doesn't exit"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/user/{id}")
    public ResponseEntity<User> searchUserById(@PathVariable String id)
    {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Searching for user with ID: " + id);
        User foundUser = service.getById(id);
        if(foundUser == null)
        {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find user with ID: " + id);
            return ResponseEntity.notFound().build();
        }
        else
        {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - User found: " + foundUser.getName());
            return ResponseEntity.ok(foundUser);
        }
    }

    //Add user
    @Operation(summary = "Add new user" , description = "Add new user to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added user"),
            @ApiResponse(responseCode = "400", description = "Failed to add user")
    })
    @PostMapping("/api/user")
    public ResponseEntity<Void> addUser(@RequestBody User user)
    {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to add new user");
        if(user == null)
        {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Failed to add new user. User is null");
            return ResponseEntity.badRequest().build();
        }

            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Creating user with name: " + user.getName());
           User saved = service.createUser(user);
           if(saved == null)
           {
               System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - User with email already exist.");
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
           else {
               System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully added new user: " + user.getName());
               user.setId(null);
               return new ResponseEntity<>(HttpStatus.CREATED);
           }
    }

    //Delete user
    @Operation(summary = "Delete user with matching ID" , description ="Match user with ID and delete user from database" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted user"),
            @ApiResponse(responseCode = "400", description = "Failed to delete user")
    })
    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id)
    {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to delete user with ID: " + id);
        User foundUser = service.getById(id);
        if(foundUser == null)
        {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find user with ID: " + id + " for deletion");
            return ResponseEntity.notFound().build();
        }
        else
        {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Deleting user: " + foundUser.getName() + " with ID: " + id);
            service.deleteUser(foundUser);
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully deleted user with ID: " + id);
            return ResponseEntity.noContent().build();
        }
    }

    //Update user
    @Operation(summary = "Update user with matching ID" , description ="Match user with ID and update user" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user"),
            @ApiResponse(responseCode = "404", description = "Failed to delete user")
    })
    @PutMapping("/api/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user)
    {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting to update user with ID: " + id);
        User foundUser = service.getById(id);
        if(foundUser == null)
        {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Can't find user with ID: " + id + " for update");
            return ResponseEntity.notFound().build();
        }
        else
        {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Updating user: " + foundUser.getName() + " with ID: " + id);
            service.editUser(user);
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully updated user with ID: " + id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    // user Login
    @Operation(summary = "User login" , description ="Match user email with password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully login"),
            @ApiResponse(responseCode = "401", description = "Failed to login")
    })
    @PostMapping("/api/login")
    public ResponseEntity<User> checkLogin(@RequestParam String email,
                                           @RequestParam String password){
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Attempting login");
        User u = service.checkLogin(email, password);
        if(u != null){
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successful login for user: " + u.getName());
            return ResponseEntity.ok(u);
        }
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Failed to login. Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    //////Consultant Service functions///////
    //Deactive user
    @Operation(summary = "DeActive customer" , description ="MDeActive a customer to not login website" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully DeActive customer"),
            @ApiResponse(responseCode = "404", description = "Failed to DeActive customer")
    })
    @PutMapping("/api/deactive/{id}")
    public ResponseEntity<User> deActiveUser(@PathVariable String id){
        User u = service.getById(id);
        if(u != null){
            service.deActiveUser(u);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return ResponseEntity.notFound().build();
    }

    //Active user
    @Operation(summary = "DeActive customer" , description ="MDeActive a customer to not login website" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully DeActive customer"),
            @ApiResponse(responseCode = "404", description = "Failed to DeActive customer")
    })
    @PutMapping("/api/active/{id}")
    public ResponseEntity<User> activeUser(@PathVariable String id){
        User u = service.getById(id);
        if(u != null){
            service.activeUser(u);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return ResponseEntity.notFound().build();
    }

}