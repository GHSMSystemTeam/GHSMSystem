package com.GHSMSystemBE.GHSMSystem.Controller.HealthContentAPI;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.PostCategory;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthPostCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@Tag(name = "Post Category Management", description = "API endpoints for Post Category management operations")
@RequestMapping("/api/post-category/")
public class PostCategoryAPI {
    @Autowired
    private IHealthPostCategory service;

    @Operation(summary = "Get all post categories",
            description = "Retrieves a list of all post categories regardless of their status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostCategory.class))))
    })
    @GetMapping("")
    public ResponseEntity<List<PostCategory>> getAll()
    {
        List<PostCategory> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get all active post categories",
            description = "Retrieves a list of all active post categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved active categories",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostCategory.class))))
    })
    @GetMapping("active")
    public ResponseEntity<List<PostCategory>> getAllActive()
    {
        List<PostCategory> list = service.getAllActive();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get all inactive post categories",
            description = "Retrieves a list of all inactive post categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved inactive categories",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostCategory.class))))
    })
    @GetMapping("inactive")
    public ResponseEntity<List<PostCategory>> getAllInactive()
    {
        List<PostCategory> list = service.getAllInactive();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get post category by ID",
            description = "Retrieves a specific post category by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the category",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostCategory.class))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<PostCategory> getById(
            @Parameter(description = "ID of the category to retrieve", required = true)
            @PathVariable Integer id)
    {
        PostCategory found = service.getById(id);
        if(found == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return ResponseEntity.ok(found);
        }
    }

    @Operation(summary = "Create a new post category",
            description = "Creates a new post category with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostCategory.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category data provided",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<PostCategory> getById(
            @Parameter(description = "Post category to be created", required = true)
            @RequestBody PostCategory category)
    {
        if(category == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            PostCategory created = service.addCategory(category);
            if(created == null)
            {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else
                return ResponseEntity.ok(created);
        }
    }

    @Operation(summary = "Update an existing post category",
            description = "Updates a post category with the specified ID using the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostCategory.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category data provided",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PutMapping("{id}")
    public ResponseEntity<PostCategory> updateCategory(
            @Parameter(description = "ID of the category to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Updated category information", required = true)
            @RequestBody PostCategory category)
    {
        if(category == null || id == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            PostCategory found = service.getById(id);
            if(found == null)
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                PostCategory updated = service.editCategory(id, category);
                if(updated == null)
                {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return ResponseEntity.ok(updated);
            }
        }
    }

    @Operation(summary = "Delete a post category",
            description = "Deletes a post category with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostCategory.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID provided",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<PostCategory> deleteCategory(
            @Parameter(description = "ID of the category to delete", required = true)
            @PathVariable Integer id)
    {
        if(id == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            PostCategory deleted = service.deleteCategory(id);
            if(deleted == null)
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else
            {
                return ResponseEntity.ok(deleted);
            }
        }
    }
}