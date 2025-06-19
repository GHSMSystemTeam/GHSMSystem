package com.GHSMSystemBE.GHSMSystem.Controller.HealthContentAPI;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.QuestionDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Question;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthAnswers;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthQuestion;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@Tag(name = "Question Management", description = "API endpoints for Question management operations")
public class QuestionAPI {

    @Autowired
    private IUserService uservice;
    @Autowired
    private IHealthQuestion hqservice;
    @Autowired
    private IHealthAnswers haservice;
    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Get all questions", description = "Retrieve a complete list of all questions from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of questions retrieved successfully")
    })
    @GetMapping("/api/question")
    public ResponseEntity<List<Question>> getAll() {
        System.out.println("Retrieving all questions");
        List<Question> list = hqservice.getAll();
        System.out.println("Found " + list.size() + " questions in database");
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get active questions", description = "Retrieve a list of all active questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active questions retrieved successfully")
    })
    @GetMapping("/api/question/active")
    public ResponseEntity<List<Question>> getAllActive() {
        System.out.println("Retrieving all active questions");
        List<Question> list = hqservice.getAllActiveQuestions();
        System.out.println("Found " + list.size() + " active questions in database");
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get inactive questions", description = "Retrieve a list of all inactive questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of inactive questions retrieved successfully")
    })
    @GetMapping("/api/question/inactive")
    public ResponseEntity<List<Question>> getAllInactive() {
        System.out.println("Retrieving all inactive questions");
        List<Question> list = hqservice.getAllInactiveQuestions();
        System.out.println("Found " + list.size() + " inactive questions in database");
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get question by ID", description = "Retrieve a question with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question found",
                    content = @Content(schema = @Schema(implementation = Question.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Question not found")
    })
    @GetMapping("/api/question/id/{id}")
    public ResponseEntity<Question> getById(
            @Parameter(description = "Question ID") @PathVariable String id) {
        System.out.println("Searching for question with ID: " + id);
        if (id == null) {
            System.out.println("Null ID provided for question search");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Question found = hqservice.getById(id);
        if (found == null) {
            System.out.println("Question not found with ID: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Question found with ID: " + id);
        return ResponseEntity.ok(found);
    }

    @Operation(summary = "Add new question", description = "Add a new question to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created question"),
            @ApiResponse(responseCode = "400", description = "Failed to create question - validation error"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PostMapping("/api/question")
    public ResponseEntity<Question> createQuestion(
            @Parameter(description = "Question data", required = true)
            @RequestBody QuestionDTO question) {
        System.out.println("Attempting to create new question");
        if (question == null) {
            System.out.println("Question creation failed - null data provided");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        System.out.println("Looking up customer with ID: " + question.getCustomerId());
        User user = uservice.getById(question.getCustomerId());
        if (user == null) {
            System.out.println("Question creation failed - customer not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Question temp = new Question();
        temp.setCustomer(user);
        temp.setTitle(question.getTitle());
        temp.setContent(question.getContent());
        temp.setCreateDate(question.getCreateDate());
        temp.setIsPublic(question.getIsPublic());

        Question saved = hqservice.createQuestion(temp);
        if (saved == null) {
            System.out.println("Failed to save question to database");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("Successfully created question with ID: " + saved.getId());
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }
    }

    @Operation(summary = "Delete a question", description = "Delete a question by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Question not found"),
            @ApiResponse(responseCode = "500", description = "Failed to delete question")
    })
    @DeleteMapping("/api/question/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @Parameter(description = "ID of the question to delete", required = true)
            @PathVariable String id) {
        System.out.println("Attempting to delete question with ID: " + id);
        if (id == null) {
            System.out.println("Null ID provided for question deletion");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Question question = hqservice.getById(id);
        if (question == null) {
            System.out.println("Question not found for deletion");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("Deleting question");
            hqservice.deleteQuestion(question);

            question = hqservice.getById(id);
            if (question == null) {
                System.out.println("Successfully deleted question");
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                System.out.println("Failed to delete question");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Operation(summary = "Update question", description = "Update an existing question by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated question"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Question not found")
    })
    @PutMapping("/api/question/id/{id}")
    public ResponseEntity<Question> editQuestion(
            @Parameter(description = "Updated question object", required = true)
            @RequestBody QuestionDTO question,
            @Parameter(description = "Question ID to update") @PathVariable String id) {
        System.out.println("Attempting to update question with ID: " + id);
        if (question == null || id == null) {
            System.out.println("Null question data or ID provided for update");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Question found = hqservice.getById(id);
        if (found == null) {
            System.out.println("Question not found for update");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("Updating question");
            found.setTitle(question.getTitle());
            found.setContent(question.getContent());
            found.setIsPublic(question.getIsPublic());

            Question saved = hqservice.editQuestion(found);
            System.out.println("Successfully updated question");
            return ResponseEntity.ok(saved);
        }
    }

    @Operation(summary = "Deactivate question", description = "Deactivate a question by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deactivated question"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Question not found")
    })
    @PutMapping("/api/question/deactive/{id}")
    public ResponseEntity<Void> deactiveById(
            @Parameter(description = "Question ID to deactivate") @PathVariable String id) {
        System.out.println("Attempting to deactivate question with ID: " + id);
        if (id == null) {
            System.out.println("Null ID provided for question deactivation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Question found = hqservice.getById(id);
        if (found == null) {
            System.out.println("Question not found for deactivation");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println("Deactivating question");
        hqservice.deActive(found);
        System.out.println("Successfully deactivated question");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Activate question", description = "Activate a question by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully activated question"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Question not found")
    })
    @PutMapping("/api/question/active/{id}")
    public ResponseEntity<Void> activeById(
            @Parameter(description = "Question ID to activate") @PathVariable String id) {
        System.out.println("Attempting to activate question with ID: " + id);
        if (id == null) {
            System.out.println("Null ID provided for question activation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Question found = hqservice.getById(id);
        if (found == null) {
            System.out.println("Question not found for activation");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println("Activating question");
        hqservice.active(found);
        System.out.println("Successfully activated question");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}