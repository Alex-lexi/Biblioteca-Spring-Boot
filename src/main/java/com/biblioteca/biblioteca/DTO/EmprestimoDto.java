package com.biblioteca.biblioteca.DTO;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EmprestimoDto(
    @NotNull Long usuarioId,
    @NotNull Long livroId,
    LocalDate dataEmprestimo,
    LocalDate dataDevolucao
) {}