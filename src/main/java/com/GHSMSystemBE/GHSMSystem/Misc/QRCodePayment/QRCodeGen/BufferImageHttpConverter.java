package com.GHSMSystemBE.GHSMSystem.Misc.QRCodePayment.QRCodeGen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
@Configuration
public class BufferImageHttpConverter {
    @Bean
    public HttpMessageConverter createConverter()
    {
        return new BufferedImageHttpMessageConverter();
    }
}