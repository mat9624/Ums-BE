package com.example.ums.command;

import com.example.ums.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCommand {
    private String nome;
    private String cognome;
    private String email;
    private String password;
}
