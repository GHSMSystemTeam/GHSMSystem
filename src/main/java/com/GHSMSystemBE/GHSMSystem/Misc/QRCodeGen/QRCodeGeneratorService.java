package com.GHSMSystemBE.GHSMSystem.Misc.QRCodeGen;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

@CrossOrigin("*")
@RestController
@RequestMapping("/barcodes")
public class QRCodeGeneratorService {

    @GetMapping(value = "/MONEYmodule/qr-code/{text}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> qrCodeGen(@PathVariable("text") String text) throws Exception {
        BufferedImage qrImage = QRCodeGenerator.generateQRCode(text);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrImage);
    }
}