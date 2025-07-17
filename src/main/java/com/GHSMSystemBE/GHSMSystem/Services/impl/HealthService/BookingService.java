package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthService;

import com.GHSMSystemBE.GHSMSystem.Configs.Validation;
import com.GHSMSystemBE.GHSMSystem.Misc.Payment.Transaction;
import com.GHSMSystemBE.GHSMSystem.Misc.Payment.TransactionRepo;
import com.GHSMSystemBE.GHSMSystem.Misc.Payment.VNPayService;
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

import java.math.BigDecimal;
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

    @Autowired
    private VNPayService payService;

    @Autowired
    private TransactionRepo blahajRepo;


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
    public ServiceBooking createServiceBooking(BookingDTO sbdto, String ipAddress) {

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

            // check if that customer have booked that date
            if(repo.findByAppointmentDateAndSlotAndCustomerId(sbdto.getAppointmentDate(), sbdto.getSlot(), u) != null){
                return null;
            }

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
            ServiceBooking saved = repo.save(sb);
            //Initate payment (if orderID is empty sb will still be saved without problems.
            String transactionId = initiateBookingPayment(sb,ipAddress);

            if(transactionId!=null)
            {
                sb.setTransactionId(transactionId);
                return repo.save(saved);
            }
            else
                return  saved;
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

            // check if that customer have booked that date
            if(repo.findByAppointmentDateAndSlotAndCustomerId(sbdto.getAppointmentDate(), sbdto.getSlot(), u) != null){
                return null;
            }

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

   private String initiateBookingPayment(ServiceBooking sb, String ipAddess)
    {
        try {
            // Gen orderId
            String orderId = "GHSM_BOOKING_" + sb.getId().toString();

            // Get payment amount
            BigDecimal price = BigDecimal.valueOf(sb.getServiceTypeId().getPrice());
            if (price == null) {
                price = BigDecimal.ZERO;
            }

            // Order info
            String orderInfo = "Payment for " + sb.getServiceTypeId().getName() +
                    " appointment on " + sb.getAppointmentDate().toString();

            // Payment URL
            String paymentURL = payService.createPaymentUrl(
                    orderId,
                    price.toString(),
                    orderInfo,
                    ipAddess != null ? ipAddess : "127.0.0.1");

            // Create a new Transaction record
            Transaction transaction = new Transaction();
            transaction.setOrderId(orderId);
            transaction.setAmount(price);  // FIX: Set the amount
            transaction.setOrderInfo(orderInfo);
            transaction.setStatus("PENDING");
            transaction.setUserId(sb.getCustomerId().getId().toString());
            transaction.setUserName(sb.getCustomerId().getName());
            transaction.setAppointmentId(sb.getId().toString());  // FIX: Set appointment ID properly
            transaction.setCreatedAt(LocalDateTime.now());  // FIX: Set creation time

            // Save transaction
            blahajRepo.save(transaction);

            // Update booking with payment URL and status
            sb.setPaymentUrl(paymentURL);
            sb.setPaymentStatus("PENDING");

            return orderId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ServiceBooking updateBooking(ServiceBooking booking) {
        System.out.println("Updating booking " + booking.getId() + ", setting payment status to: " + booking.getPaymentStatus());
        ServiceBooking saved = repo.save(booking);
        System.out.println("Booking updated successfully with status: " + saved.getPaymentStatus());
        return saved;
    }

    public boolean updateBookingTransactionStatus(String orderId) {
        try {
            System.out.println("Updating booking status for order: " + orderId);

            Optional<Transaction> oTransaction = blahajRepo.findById(orderId);
            if (!oTransaction.isPresent()) {
                System.out.println("Transaction not found: " + orderId);
                return false;
            }

            Transaction transaction = oTransaction.get();
            String appointmentId = transaction.getAppointmentId();

            if (appointmentId == null || appointmentId.isEmpty()) {
                System.out.println("No appointment ID in transaction: " + orderId);
                // Try to extract from order ID if it follows expected format
                if (orderId.startsWith("GHSM_BOOKING_")) {
                    String[] parts = orderId.substring("GHSM_BOOKING_".length()).split("_");
                    if (parts.length > 0) {
                        appointmentId = parts[0];
                        System.out.println("Extracted appointment ID from order ID: " + appointmentId);
                    }
                }
            }

            if (appointmentId == null) {
                System.out.println("Could not determine appointment ID for order: " + orderId);
                return false;
            }

            // Find booking by ID
            ServiceBooking sb = null;
            try {
                UUID bookingUUID = UUID.fromString(appointmentId);
                sb = repo.findById(bookingUUID);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID format: " + appointmentId);
                return false;
            }

            if (sb == null) {
                System.out.println("Booking not found with ID: " + appointmentId);
                return false;
            }

            // Update booking based on transaction status
            if ("SUCCESS".equals(transaction.getStatus())) {
                sb.setPaymentStatus("PAID");
                sb.setTransactionId(transaction.getTransactionId());  // FIX: Use transaction's ID
                repo.save(sb);
                System.out.println("Booking " + sb.getId() + " payment status updated to PAID");
                return true;
            } else if ("FAILED".equals(transaction.getStatus())) {
                sb.setPaymentStatus("PAYMENT_FAILED");
                sb.setTransactionId(transaction.getTransactionId());  // FIX: Use transaction's ID
                repo.save(sb);
                System.out.println("Booking " + sb.getId() + " payment status updated to PAYMENT_FAILED");
            }

            return false;
        } catch (Exception e) {
            System.err.println("Error updating booking status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}