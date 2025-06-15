package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceType;

import java.security.Provider;
import java.util.List;

public interface IHealthServiceType {
    List<ServiceType> getAll();
    List<ServiceType> getAllActiveServiceType();
    List<ServiceType> getAllInactiveServiceType();
    ServiceType createServiceType(ServiceType newServiceType);
    ServiceType editServiceType(ServiceType updatedServiceType);
    void deleteServiceType(int id);
    ServiceType getById(int serviceTypeId);
    void active(ServiceType serviceType);
    void deActive(ServiceType serviceType);
}
