package com.GHSMSystemBE.GHSMSystem.Controller;

import com.GHSMSystemBE.GHSMSystem.Dto.User;
import com.GHSMSystemBE.GHSMSystem.Repos.userRepo;
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
    private userRepo userCRUDRepo;

    //Print user list
    @Operation(summary = "Get a list of user" , description = "Retrieve a list of available users from the Database")
    @GetMapping("/api/user")
    public ResponseEntity<List<User>> getUserList()
    {
        List<User> userList  = userCRUDRepo.findAll();
        return ResponseEntity.ok(userList);
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
        if(!userCRUDRepo.existsById(id))
        {
            System.out.println("LOG: can't found user with id: "+id);
            return ResponseEntity.notFound().build();
        }
        else
        {

            Optional<User> oUser= userCRUDRepo.findById(id);
            User foundUser = oUser.get();
            System.out.println("LOG: User found: "+ foundUser);
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
        if(user==null)
        {
            System.out.println("LOG:Failed to add new user. User null");
            return ResponseEntity.notFound().build();
        }
        else
        {
            System.out.println("LOG: added new user: " +  userCRUDRepo.save(user));
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
        if(!userCRUDRepo.existsById(id))
        {
            System.out.println("LOG: can't found user with id: "+id);
            return ResponseEntity.notFound().build();
        }
        else
        {
            userCRUDRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
    //Update user
    /*
    @Operation(summary = "Update user with matching ID" , description ="Match user with ID and update user" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user"),
            @ApiResponse(responseCode = "404", description = "Failed to delete user")
    })
    @PutMapping("/api/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user)
    {
        if(!userCRUDRepo.existsById(id))
        {
            System.out.println("LOG: can't found user with id: "+id);
            return ResponseEntity.notFound().build();
        }
        else
        {
            Optional<User> oUser = userCRUDRepo.findById(id);
            User existedUser = oUser.get();
            existedUser.setName(user.getName());
            existedUser.setPassword(user.getPassword());
            existedUser.setEmail(user.getEmail());
            existedUser.setPhone(user.getPhone());
            existedUser.setBirthDate(user.getBirthDate());
            return  ResponseEntity.ok(userCRUDRepo.save(existedUser));
        }
    }
    */



}
