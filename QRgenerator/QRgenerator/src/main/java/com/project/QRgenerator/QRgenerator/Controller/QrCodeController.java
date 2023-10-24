package com.project.QRgenerator.QRgenerator.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.project.QRgenerator.QRgenerator.Service.QrCodeService;

@RestController
public class QrCodeController {

    @Autowired
    private QrCodeService qrCodeService;

    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode(@RequestHeader("url") String url) {
        try {
            // URL encode the content
            //String encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString());

            // Generate QR code
            byte[] qrCodeBytes = qrCodeService.generateQrCode(url, 300, 300);

            // Set the Content-Disposition header to trigger download
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=qr-code.png");

            return ResponseEntity.ok().headers(headers).body(qrCodeBytes);
        } catch (WriterException | IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return ResponseEntity.status(500).body(new byte[0]); // Internal Server Error
        }
    }
}
