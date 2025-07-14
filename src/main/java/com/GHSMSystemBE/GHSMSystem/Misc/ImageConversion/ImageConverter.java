package com.GHSMSystemBE.GHSMSystem.Misc.ImageConversion;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class ImageConverter {

    public String convertToBase64(MultipartFile file)throws IOException
    {
        byte[] fileContent = file.getBytes();
        String encoded = Base64.getEncoder().encodeToString(fileContent);
        return encoded;
    }

    public String createDataUrl(String encoded, String mimeType)
    {
        return  "data:"+mimeType+";base64,"+encoded;
    }


}
