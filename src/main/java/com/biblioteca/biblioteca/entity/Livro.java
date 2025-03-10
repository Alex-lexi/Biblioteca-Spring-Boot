package com.biblioteca.biblioteca.entity;

import com.biblioteca.biblioteca.DTO.LivroDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Livro {
    // Define o campo id como a chave primária
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o valor do id automaticamente
    private Long id;

    @Column(nullable = false) // Define que a coluna não pode ser nula
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String editora;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private boolean disponivel;

    // Construtor vazio
    public Livro() {

    }

    // Construtor que recebe todos os atributos
    public Livro(Long id, String titulo, String autor, String editora, int ano, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.ano = ano;
        this.disponivel = disponivel;
    }

    // Construtor para o dto
    public Livro(LivroDto livroDto) {
        this.titulo = livroDto.titulo();
        this.autor = livroDto.autor();
        this.editora = livroDto.editora();
        this.ano = livroDto.ano();
        this.disponivel = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public boolean isDisponivel() {
        return disponivel;

    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
