package com.example.pixeltek.DTO.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCommand {
    private String name;
    private String surname;
    private String email;
    private String password;
}