package com.GHSMSystemBE.GHSMSystem.Misc.QRCodePayment.QRCodeGen;

import io.nayuki.qrcodegen.QrCode;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class QRCodeGenerator {
    public static BufferedImage generateQRCode(String barcodeText) throws Exception {
        QrCode qrCode = QrCode.encodeText(barcodeText, QrCode.Ecc.MEDIUM);
        BufferedImage image = toImage(qrCode, 4, 10);
        return image;
    }

    public static BufferedImage toImage(QrCode qr, int scale, int border) {
        return toImage(qr, scale, border, 0xFFFFFF, 0x000000);
    }

    public static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0) {
            throw new IllegalArgumentException("Value out of range");
        }
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale) {
            throw new IllegalArgumentException("Scale or border too large");
        }

        // Changed from TYPE_INT_BGR to TYPE_INT_RGB
        BufferedImage img = new BufferedImage(
                (qr.size + border * 2) * scale,
                (qr.size + border * 2) * scale,
                BufferedImage.TYPE_INT_RGB
        );

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                // Added safer boundary checking
                int qrX = x / scale - border;
                int qrY = y / scale - border;
                boolean color = qrX >= 0 && qrY >= 0 && qrX < qr.size && qrY < qr.size && qr.getModule(qrX, qrY);
                img.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return img;
    }
}