package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan; 

@Configuration
@ComponentScan("org.example")
public class AppConfig {
}

// public class AppConfig {
    
//     @Bean
//     public MessageService messageService() {
//         return new SmsService(); 
//     }

//     @Bean
//     public HeaderService headerService() {
//         return new HeaderService();
//     }

//     @Bean
//     public NotificationManager notificationManager(MessageService ms, HeaderService hs) {
//         return new NotificationManager(ms, hs);
//     }
// }
