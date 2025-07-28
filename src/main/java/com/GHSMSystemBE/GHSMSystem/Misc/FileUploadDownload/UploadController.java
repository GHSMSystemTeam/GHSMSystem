package com.GHSMSystemBE.GHSMSystem.Misc.FileUploadDownload;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin("*")
@RestController
@Tag(name = "Image upload Management", description = "API endpoints for Image uploadS operations")
public class UploadController {

    public static String UPLOAD_DIRECTORY = "uploads";

    private final Path fileStorageLocation;

    public UploadController() {
        this.fileStorageLocation = Paths.get(UPLOAD_DIRECTORY).toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(this.fileStorageLocation);
            System.out.println("File upload storage created: " + this.fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    @GetMapping("/uploadImage")
    public String displayUploadForm() {
        return "imageUpload/uploadImg";
    }

    @PostMapping("/uploadImage")
    public String uploadImage(Model model, MultipartFile file) throws IOException {
        StringBuilder fileName = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileName.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded Image: " + fileName.toString());
        return "imageUpload/uploadImg";
    }
}