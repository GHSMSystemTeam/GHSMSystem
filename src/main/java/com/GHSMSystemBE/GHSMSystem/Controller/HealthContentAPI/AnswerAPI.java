package com.GHSMSystemBE.GHSMSystem.Controller.HealthContentAPI;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.AnswerDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Answer;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@Tag(name = "Answer Management", description = "API endpoints for Answer management operations")
public class AnswerAPI {

    @Autowired
    private IHealthAnswers haservice;
    @Autowired
    private IUserService uservice;
    @Autowired
    private IHealthQuestion hqservice;

    @Operation(summary = "Get all answers", description = "Retrieves a list of all answers in the system regardless of their active status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all answers",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Answer.class)))
    })
    @GetMapping("/api/answer")
    public ResponseEntity<List<Answer>> getAll() {
        return ResponseEntity.ok(haservice.getAll());
    }

    @Operation(summary = "Get all active answers", description = "Retrieves a list of all active answers in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all active answers",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Answer.class)))
    })
    @GetMapping("/api/answer/active")
    public ResponseEntity<List<Answer>> getAllActive() {
        return ResponseEntity.ok(haservice.getAllActiveAnswers());
    }

    @Operation(summary = "Get all inactive answers", description = "Retrieves a list of all inactive answers in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all inactive answers",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Answer.class)))
    })
    @GetMapping("/api/answer/deactive")
    public ResponseEntity<List<Answer>> getAllInActive() {
        return ResponseEntity.ok(haservice.getAllInactiveAnswers());
    }

    @Operation(summary = "Get an answer by ID", description = "Retrieves a specific answer by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the answer",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Answer.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Answer not found",
                    content = @Content)
    })
    @GetMapping("/api/answer/id/{id}")
    public ResponseEntity<Answer> getById(
            @Parameter(description = "ID of the answer to retrieve", required = true)
            @PathVariable String id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Answer found = haservice.getById(id);
        if(found == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(found);
        }
    }

    @Operation(summary = "Create a new answer", description = "Creates a new answer for a specific question from a consultant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the answer",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Answer.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/api/answer")
    public ResponseEntity<Answer> createAnswer(
            @Parameter(description = "Answer data to create", required = true)
            @RequestBody AnswerDTO answer) {
        if(answer == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = uservice.getById(answer.getConsultantId());
        Question question = hqservice.getById(answer.getQuestionId());
        Answer newAnswer = new Answer();
        newAnswer.setQuestion(question);
        newAnswer.setUser(user);
        newAnswer.setTitle(answer.getTitle());
        newAnswer.setAnswerContent(answer.getContent());
        newAnswer.setCreateDate(answer.getCreateDate());
        newAnswer.setIsPublic(answer.getIsPublic());
        newAnswer.setRating(answer.getRating());
        Answer saved = haservice.createAnswer(newAnswer);
        if (saved == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return ResponseEntity.ok(saved);
        }
    }

    @Operation(summary = "Update an existing answer", description = "Updates an existing answer with new information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the answer",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Answer.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Answer not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PutMapping("/api/answer/id/{id}")
    public ResponseEntity<Answer> updateAnswer(
            @Parameter(description = "ID of the answer to update", required = true)
            @PathVariable String id,
            @Parameter(description = "Updated answer data", required = true)
            @RequestBody AnswerDTO answer) {
        if(answer == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Answer old = haservice.getById(id);
        if(old == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        old.setTitle(answer.getTitle());
        old.setAnswerContent(answer.getContent());
        old.setIsPublic(answer.getIsPublic());
        Answer updated = haservice.editAnswer(old);
        if(updated == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return ResponseEntity.ok(updated);
        }
    }

    @Operation(summary = "Delete an answer", description = "Permanently removes an answer from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the answer"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Answer not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Failed to delete the answer",
                    content = @Content)
    })
    @DeleteMapping("/api/answer/id/{id}")
    public ResponseEntity<Void> deleteAnswer(
            @Parameter(description = "ID of the answer to delete", required = true)
            @PathVariable String id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Answer delete = haservice.getById(id);
        if(delete == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        haservice.deleteAnswer(delete);
        delete = haservice.getById(id);
        if(delete == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Activate an answer", description = "Sets an answer's status to active, making it visible in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully activated the answer"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Answer not found",
                    content = @Content)
    })
    @PutMapping("/api/answer/active/{id}")
    public ResponseEntity<Void> activateAnswer(
            @Parameter(description = "ID of the answer to activate", required = true)
            @PathVariable String id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Answer activate = haservice.getById(id);
        if(activate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        haservice.active(activate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Deactivate an answer", description = "Sets an answer's status to inactive, hiding it from normal view in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deactivated the answer"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Answer not found",
                    content = @Content)
    })
    @PutMapping("/api/answer/dective/{id}")
    public ResponseEntity<Void> deactivateAnswer(
            @Parameter(description = "ID of the answer to deactivate", required = true)
            @PathVariable String id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Answer deactivate = haservice.getById(id);
        if(deactivate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        haservice.deActive(deactivate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}