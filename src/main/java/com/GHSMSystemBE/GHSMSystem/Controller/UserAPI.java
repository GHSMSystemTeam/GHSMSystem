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

    private static final String CURRENT_USER = "TranDucHai2123";
    private static final String CURRENT_DATE = "2025-06-09 06:34:44";

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
    @Operation(summary = "Get a list of active user" , description = "Retrieve a list of active users from the Database")
    @GetMapping("/api/activeuser")
    public ResponseEntity<List<User>> getActiveUserList(){
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Retrieving all active users");
        List<User> list = service.getAllActiveUser();
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Found " + list.size() + " active users in the database");
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
        else
        {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Creating user with name: " + user.getName());
            service.createUser(user);
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Successfully added new user: " + user.getName());
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
}