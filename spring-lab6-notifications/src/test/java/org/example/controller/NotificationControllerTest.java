package org.example.controller;

import java.time.LocalDateTime;

import org.example.model.entity.Notification;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.security.JwtAuthenticationFilter;
import org.example.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        controllers = NotificationController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@AutoConfigureMockMvc(addFilters = false)
class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Test
    void shouldGetNotificationById() throws Exception {
        Long id = 1L;
        Notification mockNotification = new Notification();
        mockNotification.setId(id);
        mockNotification.setTitle("Test Title");
        mockNotification.setMessage("Test Message");
        mockNotification.setStatus(NotificationStatus.PENDING);
        mockNotification.setChannel(NotificationChannel.EMAIL);
        mockNotification.setRecipientId(100L);
        mockNotification.setCreatedAt(LocalDateTime.now());

        when(notificationService.getNotificationById(id)).thenReturn(mockNotification);

        mockMvc.perform(get("/notifications/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.message").value("Test Message"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.channel").value("EMAIL"));

        verify(notificationService, times(1)).getNotificationById(id);
    }
}
