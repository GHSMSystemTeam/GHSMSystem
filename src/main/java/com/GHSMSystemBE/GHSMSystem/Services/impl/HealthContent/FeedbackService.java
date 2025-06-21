package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.CustomerDTO;
import com.GHSMSystemBE.GHSMSystem.Models.DTO.FeedbackDTO;
import com.GHSMSystemBE.GHSMSystem.Models.FeedbackSpecification;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Feedback;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo.HealthFeedbackRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IBookingService;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthFeedback;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FeedbackService implements IHealthFeedback {

    @Autowired
    private HealthFeedbackRepo hfRepo;
    @Autowired
    private IBookingService bService;
    @Autowired
    private IUserService uService;


    @Override
    public List<Feedback> getAll() {
      return hfRepo.findAll();
    }

    @Override
    public List<Feedback> getAllActiveFeedbacks() {
        return hfRepo.findAll(FeedbackSpecification.hasStatusTrue());
    }

    @Override
    public List<Feedback> getAllInactiveFeedbacks() {
        return hfRepo.findAll(FeedbackSpecification.hasStatusFalse());
    }

    @Override
    public Feedback createFeedback(FeedbackDTO dto) {
     Feedback feedback = new Feedback();
     User user = uService.getById(dto.getCustomerId());
     ServiceBooking booking = bService.getById(dto.getServiceBookingId());
     feedback.setCustomerId(user);
     feedback.setServiceBookingId(booking);
     feedback.setTitle(dto.getTitle());
     feedback.setContent(dto.getContent());
     feedback.setCreateDate(dto.getCreateDate());
    feedback.setIsPublic(dto.getIsPublic());
    return hfRepo.save(feedback);
    }

    @Override
    public Feedback editFeedback(FeedbackDTO dto, String id) {
        Feedback old = getById(id) ;
        if(old ==null)
        {
            return null;
        }
        else
        {
            old.setContent(dto.getContent()+"(edited)");
            old.setTitle(dto.getTitle());
            old.setIsPublic(dto.getIsPublic());
            return hfRepo.save(old);
        }

    }

    @Override
    public Feedback deleteFeedback(String id) {
        Feedback found = getById(id);
        if(found!=null) {
            hfRepo.delete(found);
            return found;
        }
        else {
            return null;
        }
    }

    @Override
    public Feedback getById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Feedback> optionalFeedback =hfRepo.findById(uuid);
        if(optionalFeedback ==null)
        {
            return null;
        }
        Feedback found = optionalFeedback.get() ;
        return found;
    }

    @Override
    public Feedback active(String id) {
        Feedback found = getById(id);
        if(found!=null) {
            found.setIsActive(true);
            return found;
        }
        else {
            return null;
        }
    }

    @Override
    public Feedback deActive(String id) {
        Feedback found = getById(id);
        if(found!=null) {
            found.setIsActive(false);
            return found;
        }
        else {
            return null;
        }
    }
}
