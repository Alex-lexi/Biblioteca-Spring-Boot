package com.biblioteca.biblioteca.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroDto(
    @NotBlank String titulo,
    @NotBlank String autor,
    @NotBlank String editora,
    @NotNull Integer ano,
    @NotNull Boolean disponivel
) {}