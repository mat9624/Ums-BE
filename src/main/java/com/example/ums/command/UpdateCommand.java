package com.example.ums.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommand {
    private String nome;
    private String cognome;
    private String email;
    private String password;
}
