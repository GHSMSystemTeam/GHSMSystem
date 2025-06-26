package com.GHSMSystemBE.GHSMSystem.Misc.QRCodePayment;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

@CrossOrigin("*")
@RestController
@RequestMapping("/barcodes")
public class BarCodeGenerator {

    @GetMapping(value = "/MONEYmodule/qr-code/{text}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> qrCodeGen(@PathVariable("text") String text) throws Exception {
        BufferedImage qrImage = QRCodeGenerator.generateQRCode(text);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrImage);
    }
}