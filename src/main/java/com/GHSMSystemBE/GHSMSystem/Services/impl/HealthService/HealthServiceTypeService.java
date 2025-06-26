package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthService;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceType;
import com.GHSMSystemBE.GHSMSystem.Models.ServiceTypeSpecifications;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo.ServiceTypeRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthServiceTypeService implements IHealthServiceType {
    @Autowired
    private ServiceTypeRepo repo;

    private static final String CURRENT_USER = "TranDucHai2123";
    private static final String CURRENT_DATE = "2025-06-15 08:34:19";

    @Override
    public List<ServiceType> getAll() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Getting all service types");
        return repo.findAll();
    }

    @Override
    public List<ServiceType> getAllActiveServiceType() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Getting all active service types");
        return repo.findAll(ServiceTypeSpecifications.hasStatusTrue());
    }

    @Override
    public List<ServiceType> getAllInactiveServiceType() {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Getting all inactive service types");
        return repo.findAll(ServiceTypeSpecifications.hasStatusFalse());
    }

    @Override
    public ServiceType createServiceType(ServiceType newServiceType) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Creating service type with ID: " + newServiceType.getId());

        // Use existsById instead of getById to avoid NoSuchElementException
        boolean exists = repo.existsById(newServiceType.getId());

        if (exists) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Service type with ID " + newServiceType.getId() + " already exists");
            return null; // type existed
        }

        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Saving new service type: " + newServiceType.getName());
        return repo.save(newServiceType);
    }

    @Override
    public ServiceType editServiceType(ServiceType updatedServiceType) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Editing service type with ID: " + updatedServiceType.getId());

        // Use orElse(null) to safely handle non-existent IDs
        ServiceType existingType = repo.findById(updatedServiceType.getId()).orElse(null);

        if(existingType != null) {
            existingType.setName(updatedServiceType.getName());
            existingType.setPrice(updatedServiceType.getPrice());
            existingType.setDescription(updatedServiceType.getDescription());
            existingType.setTypeCode(updatedServiceType.getTypeCode());
            existingType.setActive(updatedServiceType.isActive()); // Match entity field name

            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Saving updated service type: " + existingType.getName());
            return repo.save(existingType);
        }
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Service type with ID " + updatedServiceType.getId() + " not found for update");
        return null;
    }

    @Override
    public void deleteServiceType(int id) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Deleting service type with ID: " + id);

        try {
            // Check if exists first
            boolean exists = repo.existsById(id);
            if (exists) {
                ServiceType serviceType  = repo.findById(id).get();
                repo.delete(serviceType);
                System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Service type successfully deleted");
            } else {
                System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Service type with ID " + id + " not found for deletion");
            }
        } catch (Exception e) {
            System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Error deleting service type: " + e.getMessage());
        }
    }

    @Override
    public ServiceType getById(int serviceTypeId) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Getting service type with ID: " + serviceTypeId);

        // Fix: use orElse(null) instead of get() to avoid NoSuchElementException
        return repo.findById(serviceTypeId).orElse(null);
    }

    @Override
    public void active(ServiceType serviceType) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Activating service type with ID: " + serviceType.getId());

        // Must match the actual field name in the entity
        serviceType.setActive(true);

        // Fix: save the changes to the database
        repo.save(serviceType);
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Service type activated successfully");
    }

    @Override
    public void deActive(ServiceType serviceType) {
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Deactivating service type with ID: " + serviceType.getId());

        // Must match the actual field name in the entity
        serviceType.setActive(false);

        // Fix: save the changes to the database
        repo.save(serviceType);
        System.out.println("LOG: " + CURRENT_DATE + " - " + CURRENT_USER + " - Service type deactivated successfully");
    }
}
