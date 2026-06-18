package org.example.service;

import org.springframework.stereotype.Component;

@Component
public class HeaderService {
    public String getHeader() {
        return "[LAB-3-SPRING]: ";
    }
}