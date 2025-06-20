package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthService;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceType;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.UserRepo;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo.ServiceBookingRepo;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo.ServiceTypeRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private ServiceBookingRepo repo;

    @Autowired
    private UserRepo urepo;

    @Autowired
    private ServiceTypeRepo strepo;

    @Override
    public List<ServiceBooking> getAll() {
        return repo.findAll();
    }

    @Override
    public List<ServiceBooking> getAllActiveBooking() {
        return repo.findByIsActive(true);
    }

    @Override
    public List<ServiceBooking> getAllDeActiveBooking() {
        return repo.findByIsActive(false);
    }

    @Override
    public List<ServiceBooking> getAllByCustomerId(String id) {
        UUID customerId = UUID.fromString(id);
        Optional<User> u = urepo.findById(customerId);
        return repo.findByCustomerId(u.orElse(null));
    }

    @Override
    public List<ServiceBooking> getAllByConsultantId(String id) {
        UUID consultantId = UUID.fromString(id);
        Optional<User> u = urepo.findById(consultantId);
        return repo.findByConsultantId(u.orElse(null));
    }

    @Override
    public List<ServiceBooking> getAllByServiceTypeId(int id) {
        Optional<ServiceType> st = strepo.findById(id);
        return repo.findByServiceTypeId(st.orElse(null));
    }

    @Override
    public ServiceBooking getById(String id) {
        UUID bookingId = UUID.fromString(id);
        return repo.findById(bookingId);
    }

    @Override
    public ServiceBooking activeBooking(ServiceBooking sb) {
        sb.setActive(true);
        return repo.save(sb);
    }

    @Override
    public ServiceBooking deActiveBooking(ServiceBooking sb) {
        sb.setActive(false);
        return repo.save(sb);
    }

    @Override
    public ServiceBooking createServiceBooking(ServiceBooking sb) {

        // get now date from LocalDate and convert to java.sql.date
        LocalDate localDate = LocalDate.now();
        Date sqlDate = Date.valueOf(localDate.toString());

        if(sb != null){
            // compare if the appointment date is before current date
            // compare where appointment slot between slot 1 and 5
            if(sb.getAppointmentDate().compareTo(sqlDate) > 0 && (sb.getAppointmentSlot() >= 1 && sb.getAppointmentSlot() <= 5)){
                sb.setCreateDate(LocalDateTime.now());
                sb.setActive(true);
                sb.setDescription("some appointment");
                return repo.save(sb);
            }
            return null;
        }
        return null;
    }

    @Override
    public ServiceBooking updateBookingStatus(ServiceBooking sb, int sn) {
        sb.setStatus(sn);
        return repo.save(sb);
    }

    @Override
    public boolean deleteServiceBooking(ServiceBooking sb) {
        if (sb != null) {
            repo.delete(sb);
            return true;
        }
        return false;
    }
}
