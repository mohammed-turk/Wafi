package com.example.wafi;

import com.example.wafi.Service.EmailService;
import com.example.wafi.Service.WhatsAppService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class WafiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WafiApplication.class, args);
    }
    @Bean
    CommandLineRunner testWhatsApp(WhatsAppService whatsAppService) {
        return args -> whatsAppService.sendWhatsApp("966564293818", "Wafi WhatsAppService test - Green API works.");
    }

//    @Bean
//    CommandLineRunner testEmail(EmailService emailService) {
//        return args -> emailService.sendEmail(
//                "mtj6611@gmail.com",
//                "Wafi test",
//                "If you got this, EmailService works."
//        );
//    }

}