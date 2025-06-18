package com.GHSMSystemBE.GHSMSystem.Controller.HealthContentAPI;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Answer;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Question;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthAnswers;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthQuestion;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    private ModelMapper mapper ;

@GetMapping("/api/Question")
    public ResponseEntity<List<Question>> getAll()
{
    List<Question> list = hqservice.getAll();
    return ResponseEntity.ok(list);
}

@GetMapping("/api/Question/active")
    public ResponseEntity<List<Question>> getAllActive()
{
    List<Question> list = hqservice.getAllActiveQuestions();
    return ResponseEntity.ok(list);
}

@GetMapping("/api/Question/inactive")
    public ResponseEntity<List<Question>> getAllInactive()
    {
        List<Question> list = hqservice.getAllInactiveQuestions();
        return ResponseEntity.ok(list);
    }
}
