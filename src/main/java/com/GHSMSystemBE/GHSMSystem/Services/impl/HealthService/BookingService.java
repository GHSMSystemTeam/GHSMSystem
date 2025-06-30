package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthService;

import com.GHSMSystemBE.GHSMSystem.Configs.Validation;
import com.GHSMSystemBE.GHSMSystem.Models.DTO.BookingDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceType;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.UserRepo;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo.ServiceBookingRepo;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo.ServiceTypeRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IBookingService;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthServiceType;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private ServiceBookingRepo repo;

    @Autowired
    private IHealthServiceType hst;

    @Autowired
    private IUserService us;

    @Autowired
    private UserRepo urepo;

    @Autowired
    private ServiceTypeRepo strepo;

    @Override
    public List<ServiceBooking> getAll() {
        List<ServiceBooking> bookings = repo.findAll();
        for (ServiceBooking booking : bookings) {
            if (booking.getSlot() == null) {
                booking.setSlot(1);
            }
        }
        return bookings;
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
    public ServiceBooking createServiceBooking(BookingDTO sbdto) {

        // prepare to validation
        LocalDate dateCheck = sbdto.getAppointmentDate().toLocalDate();
        LocalTime thisTime = LocalTime.now();
        List<ServiceBooking> listQuery = repo.findByAppointmentDateAndSlot(sbdto.getAppointmentDate(), sbdto.getSlot());
        int listNum = listQuery.size();

        User u, c = new User();
        ServiceType st = new ServiceType();
        ServiceBooking sb = new ServiceBooking();

        Validation val = new Validation();

        if(val.validationSlot(dateCheck, thisTime, sbdto.getSlot(),listNum)){
            u = us.getById(sbdto.getCustomerId());
            st = hst.getById(sbdto.getServiceTypeId());
            c = us.getById(sbdto.getConsultantId());

            // mapping
            sb.setCustomerId(u);
            sb.setAppointmentDate(sbdto.getAppointmentDate());
            sb.setDuration(sbdto.getDuration());
            sb.setServiceTypeId(st);
            sb.setSlot(sbdto.getSlot());
            if(st.getTypeCode() == 1){
                sb.setConsultantId(null);
            }else if(st.getTypeCode() == 0){
                sb.setConsultantId(c);
            }

            // Set the slot field
            sb.setSlot(sbdto.getSlot() != null ? sbdto.getSlot() : 1);

            sb.setCreateDate(LocalDateTime.now());
            sb.setActive(true);
            sb.setDescription(sbdto.getDescription());
            return repo.save(sb);
        }
        return null;
    }

    @Override
    public ServiceBooking createTesting(BookingDTO sbdto) {

        LocalDate dateCheck = sbdto.getAppointmentDate().toLocalDate();

        LocalTime thisTime = LocalTime.now();

        User u = new User();
        ServiceType st = new ServiceType();
        ServiceBooking sb = new ServiceBooking();

        List<ServiceBooking> listQuery = repo.findByAppointmentDateAndSlot(sbdto.getAppointmentDate(), sbdto.getSlot());
        int listNum = listQuery.size();

        Validation val = new Validation();
        if(!val.validationSlot(dateCheck, thisTime, sbdto.getSlot(),listNum)){
            return null;
        }else{
            u = us.getById(sbdto.getCustomerId());
            st = hst.getById(sbdto.getServiceTypeId());
            if(st.getTypeCode() == 1){
                // mapping
                sb.setCustomerId(u);
                sb.setAppointmentDate(sbdto.getAppointmentDate());
                sb.setDuration(sbdto.getDuration());
                sb.setServiceTypeId(st);
                // Set the slot field if null
                sb.setSlot(sbdto.getSlot() != null ? sbdto.getSlot() : 1);
                sb.setConsultantId(null);
                sb.setCreateDate(LocalDateTime.now());
                sb.setDescription(sbdto.getDescription());
                sb.setActive(true);
                return repo.save(sb);
            }
            return null;
        }
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

    @Override
    public ServiceBooking updateTimeSlot(ServiceBooking serviceBooking, int slot) {
        serviceBooking.setSlot(slot);
        repo.save(serviceBooking);
        return serviceBooking;
    }
}