package com.GHSMSystemBE.GHSMSystem.Controller.HealthContentAPI;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.PostDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Post;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthPost;
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

@RestController
@RequestMapping("/api/health-post")
@Tag(name = "Blog Post Management", description = "API endpoints for Blog Post management operations")
@CrossOrigin("*")
public class PostAPI {
    @Autowired
    private IHealthPost postService;

    @Operation(summary = "Get all blog posts",
            description = "Retrieves a list of all health blog posts regardless of their status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all posts",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class))))
    })
    @GetMapping("")
    public ResponseEntity<List<Post>> getAll(){
        List<Post> postList = postService.getAll();
        return ResponseEntity.ok(postList);
    }

    @Operation(summary = "Get all active blog posts",
            description = "Retrieves a list of all active health blog posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved active posts",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class))))
    })
    @GetMapping("/active")
    public ResponseEntity<List<Post>> getAllActive(){
        List<Post> postList = postService.getAllActive();
        return ResponseEntity.ok(postList);
    }

    @Operation(summary = "Get all inactive blog posts",
            description = "Retrieves a list of all inactive health blog posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved inactive posts",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class))))
    })
    @GetMapping("/inactive")
    public ResponseEntity<List<Post>> getAllInactive(){
        List<Post> postList = postService.getAllInactive();
        return ResponseEntity.ok(postList);
    }

    @Operation(summary = "Get posts by consultant ID",
            description = "Retrieves all blog posts authored by a specific consultant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts by consultant",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid consultant ID provided",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No posts found for the consultant",
                    content = @Content)
    })
    @GetMapping("/consultant/{id}")
    public ResponseEntity<List<Post>> getByConsultant(
            @Parameter(description = "ID of the consultant to filter posts by", required = true)
            @PathVariable String id){
        if(id== null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Post> found = postService.getByConsultant(id);
        if(found == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return ResponseEntity.ok(found);
        }
    }

    @Operation(summary = "Get posts by category ID",
            description = "Retrieves all blog posts belonging to a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts by category",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid category ID provided",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No posts found for the category",
                    content = @Content)
    })
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Post>> getByCategory(
            @Parameter(description = "ID of the category to filter posts by", required = true)
            @PathVariable Integer id){
        if(id== null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Post> found = postService.getByCategory(id);
        if(found == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return ResponseEntity.ok(found);
        }
    }

    @Operation(summary = "Create a new blog post",
            description = "Creates a new health blog post with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog post successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Invalid blog post data provided",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<Post> addPost(
            @Parameter(description = "Blog post data to be created", required = true)
            @RequestBody PostDTO dto)
    {
        if(dto == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            Post created = postService.addPost(dto);
            if (created == null)
            {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else
            {
                return ResponseEntity.ok(created);
            }
        }
    }

    @Operation(summary = "Update an existing blog post",
            description = "Updates a blog post with the specified ID using the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog post successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Invalid blog post data or ID provided",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Blog post not found",
                    content = @Content)
    })
    @PutMapping("/id/{id}")
    public ResponseEntity<Post> editPost(
            @Parameter(description = "ID of the blog post to update", required = true)
            @PathVariable String id,
            @Parameter(description = "Updated blog post information", required = true)
            @RequestBody PostDTO dto)
    {
        if(id == null || dto ==null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Post updated = postService.editPost(id,dto);
        if (updated == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return ResponseEntity.ok(updated);
        }
    }

    @Operation(summary = "Delete a blog post",
            description = "Deletes a blog post with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog post successfully deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID provided",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Blog post not found",
                    content = @Content)
    })
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Post> deletePost(
            @Parameter(description = "ID of the blog post to delete", required = true)
            @PathVariable String id)
    {
        if(id == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Post deleted = postService.deletePost(id);
        if (deleted == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return ResponseEntity.ok(deleted);
        }
    }

    @Operation(summary = "Activate a blog post",
            description = "Changes the status of a blog post to active")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog post successfully activated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID provided",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Blog post not found",
                    content = @Content)
    })
    @PutMapping("/activate/id/{id}")
    public ResponseEntity<Post> activatePost(
            @Parameter(description = "ID of the blog post to activate", required = true)
            @PathVariable String id)
    {
        if(id==null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Post activated = postService.activatePost(id);
        if (activated ==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return ResponseEntity.ok(activated);
        }
    }

    @Operation(summary = "Deactivate a blog post",
            description = "Changes the status of a blog post to inactive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog post successfully deactivated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID provided",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Blog post not found",
                    content = @Content)
    })
    @PutMapping("/deactivate/id/{id}")
    public ResponseEntity<Post> deactivatePost(
            @Parameter(description = "ID of the blog post to deactivate", required = true)
            @PathVariable String id)
    {
        if(id==null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Post deactivated = postService.deactivatePost(id);
        if (deactivated ==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return ResponseEntity.ok(deactivated);
        }
    }
}