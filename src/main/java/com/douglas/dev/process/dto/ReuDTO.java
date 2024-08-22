package com.douglas.dev.process.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReuDTO {

    @NotBlank(message = "O nome do réu não pode estar em branco")
    private String nome;
}
