package org.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/ping")
    public String ping() {
        return "Только для администратора";
    }

    @GetMapping("/secure-action")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String secureAction() {
        return "Администратор успешно выполнил защищенное действие через @PreAuthorize";
    }
}