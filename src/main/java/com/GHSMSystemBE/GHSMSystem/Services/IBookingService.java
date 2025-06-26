package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.BookingDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;

import java.util.List;

public interface IBookingService {

    List<ServiceBooking> getAll();
    List<ServiceBooking> getAllActiveBooking();
    List<ServiceBooking> getAllDeActiveBooking();
    List<ServiceBooking> getAllByCustomerId(String id);
    List<ServiceBooking> getAllByConsultantId(String id);
    List<ServiceBooking> getAllByServiceTypeId(int id);
    ServiceBooking getById(String id);
    ServiceBooking activeBooking(ServiceBooking sb);
    ServiceBooking deActiveBooking(ServiceBooking sb);
    ServiceBooking createServiceBooking(BookingDTO sb);
    ServiceBooking updateBookingStatus(ServiceBooking sb, int sn);
    boolean deleteServiceBooking(ServiceBooking sb);

}
