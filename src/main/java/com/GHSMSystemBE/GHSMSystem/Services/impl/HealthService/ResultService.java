package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthService;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.ServiceResultDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceResult;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.UserRepo;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo.ServiceBookingRepo;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo.ServiceResultRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthResultService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResultService implements IHealthResultService {

    @Autowired
    private ServiceResultRepo srrepo;
    @Autowired
    private ServiceBookingRepo sbrepo;
    @Autowired
    private UserRepo urepo;

    @Override
    public List<ServiceResult> getAll() {
        return srrepo.findAll();
    }

    @Override
    public List<ServiceResult> getAllByCustomerId(String id) {
        Optional<User> c = urepo.findById(UUID.fromString(id));
        User u = c.get();
        return srrepo.findByCustomerId(u);
    }

    @Override
    public List<ServiceResult> getAllByConsultantId(String id) {
        Optional<User> c = urepo.findById(UUID.fromString(id));
        User u = c.get();
        return srrepo.findByConsultantId(u);
    }

    @Override
    public ServiceResult getById(String id) {
        return srrepo.findById(UUID.fromString(id));
    }

    @Override
    public ByteArrayResource generateMServiceResultPDF(String srId) throws DocumentException, IOException {
        ServiceResult srOtp = srrepo.findById(UUID.fromString(srId));
        if (srOtp == null) {
            throw new RuntimeException("ServiceResult not found with ID: " + srId);
        }
        ServiceResult sr = srOtp;

        Optional<User> uOtp = urepo.findById(sr.getCustomerId().getId());
        if (uOtp.isEmpty()) {
            throw new RuntimeException("Customer not found with ID: " + sr.getCustomerId().getId());
        }
        User u = uOtp.get();

        ServiceBooking sbOtp = sbrepo.findById(sr.getServiceBookingId().getId());
        if(sbOtp == null){
            throw new RuntimeException("ServiceBooking not found with ID: " + sr.getServiceBookingId().getId());
        }
        ServiceBooking sb = sbOtp;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);

        document.open();

        // Add header
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font subHeaderFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);

        Paragraph header = new Paragraph("Phan Mem Quan Ly Dich Vu Cham Soc Suc Khoe Gioi Tinh", headerFont);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        document.add(new Paragraph(" ")); // Empty line

        Paragraph title = new Paragraph("Ket Qua", subHeaderFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" ")); // Empty line

        // Customer Gender Mapping
        Map<Integer, String> genderMap = Map.of(
                1, "Nam",
                2, "Nu",
                3, "Khac"
        );

        // Customer Information
        document.add(new Paragraph("THONG TIN KHACH HANG", subHeaderFont));
        document.add(new Paragraph("Ten: " + u.getName(), normalFont));
        document.add(new Paragraph("Email: " + u.getEmail(), normalFont));
        document.add(new Paragraph("Phone: " + (u.getPhone() != null ? u.getPhone() : "N/A"), normalFont));
        document.add(new Paragraph("Gioi Tinh: " + genderMap.getOrDefault(u.getGender(), "N/A"), normalFont));
        document.add(new Paragraph("Ngay Sinh: " + (u.getBirthDate() != null ? u.getBirthDate().toString() : "N/A"), normalFont));

        document.add(new Paragraph(" ")); // Empty line

        // Medical Record Details
        document.add(new Paragraph("CHI TIET KET QUA", subHeaderFont));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // format Date
        document.add(new Paragraph("Ngay: " + sb.getAppointmentDate(), normalFont));
        document.add(new Paragraph("ID: " + sb.getId(), normalFont));

        document.add(new Paragraph(" ")); // Empty line

        document.add(new Paragraph("LOI NHAN CUA BAC SY:", subHeaderFont));
        document.add(new Paragraph(sb.getDescription() != null ? sb.getDescription() : "Không có lời nhắn nào được ghi lại.", normalFont));

        document.add(new Paragraph(" ")); // Empty line

        document.add(new Paragraph("LOI NHAN CUA TU VAN VIEN:", subHeaderFont));
        document.add(new Paragraph(sr.getContent() != null ? sr.getContent() : "Không có lời nhắn nào được ghi lại.", normalFont));

        document.add(new Paragraph(" ")); // Empty line

        // Footer
        document.add(new Paragraph("Duoc tao vao ngay: " + java.time.LocalDateTime.now().format(formatter), normalFont));

        document.close();

        byte[] pdfBytes = baos.toByteArray();
        return new ByteArrayResource(pdfBytes);
    }

    @Override
    public ServiceResult createServiceResult(ServiceResultDTO srDTO) {

        ServiceBooking sb = sbrepo.findById(UUID.fromString(srDTO.getServiceBookingId()));
        Optional<User> uOtp = urepo.findById(UUID.fromString(srDTO.getCustomerID()));
        User customer = uOtp.get();

        ServiceResult sr = new ServiceResult();

        sr.setServiceBookingId(sb);
        sr.setCustomerId(customer);
        sr.setContent(srDTO.getContent());
        sr.setActive(true);

        return srrepo.save(sr);
    }
}
