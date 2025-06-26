package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthService;

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
    public ServiceBooking createServiceBooking(BookingDTO sbdto) {
        // get now date from LocalDate and convert to java.sql.date
        LocalDate localDate = LocalDate.now();
        Date sqlDate = Date.valueOf(localDate.toString());

        User u, c = new User();
        ServiceType st = new ServiceType();
        ServiceBooking sb = new ServiceBooking();

        if(sbdto.getAppointmentDate().compareTo(sqlDate) > 0){
            u = us.getById(sbdto.getCustomerId());
            st = hst.getById(sbdto.getServiceTypeId());
            c = us.getById(sbdto.getConsultantId());

            // mapping
            sb.setCustomerId(u);
            sb.setAppointmentDate(sbdto.getAppointmentDate());
            sb.setDuration(sbdto.getDuration());
            sb.setServiceTypeId(st);
            if(st.getTypeCode() == 1){
                sb.setConsultantId(null);
            }else{
                sb.setConsultantId(c);
            }

            sb.setCreateDate(LocalDateTime.now());
            sb.setActive(true);
            sb.setDescription(sbdto.getDescription());
            return repo.save(sb);
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
