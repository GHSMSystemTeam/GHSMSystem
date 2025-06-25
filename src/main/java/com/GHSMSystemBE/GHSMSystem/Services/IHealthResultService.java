package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.ServiceResultDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceResult;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.util.List;

public interface IHealthResultService {

    List<ServiceResult> getAll();
    List<ServiceResult> getAllByCustomerId(String id);
    ServiceResult getById(String id);
    ByteArrayResource generateMServiceResultPDF(String srId) throws DocumentException, IOException;
    ServiceResult createServiceResult(ServiceResultDTO srDTO);
}
