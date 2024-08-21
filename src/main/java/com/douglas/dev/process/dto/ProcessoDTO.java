package com.douglas.dev.process.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessoDTO {

    @NotBlank(message = "O número do processo não pode estar em branco")
    @Size(max = 255, message = "O número do processo não pode ter mais que 255 caracteres")
    private String numero;
}
