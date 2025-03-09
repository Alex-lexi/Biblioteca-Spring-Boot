package com.biblioteca.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca.DTO.LivroDto;
import com.biblioteca.biblioteca.entity.Livro;
import com.biblioteca.biblioteca.repository.LivroRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long id) {
        Livro livro = livroRepository.findById(id).orElse(null);

        if (livro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(livro);
    }

    @PostMapping
    public ResponseEntity<Livro> cadastrarNovoLivro(@RequestBody @Valid LivroDto dados) {
        Livro livroNovo = new Livro(dados);
        livroRepository.save(livroNovo);
        return ResponseEntity.ok(livroNovo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, @RequestBody @Valid LivroDto dados) {
        Livro livro = livroRepository.findById(id).orElse(null);

        if (livro == null) {
            return ResponseEntity.notFound().build();
        }
        livro.setTitulo(dados.titulo());
        livro.setAutor(dados.autor());
        livro.setEditora(dados.editora());
        livro.setAno(dados.ano());
        livro.setDisponivel(dados.disponivel());
        livroRepository.save(livro);
        return ResponseEntity.ok(livro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarLivro(@PathVariable Long id) {
        Livro livro = livroRepository.findById(id).orElse(null);

        if (livro == null) {
            return ResponseEntity.notFound().build();
        }

        livroRepository.delete(livro);
        return ResponseEntity.ok("Livro '" + livro.getTitulo() + "' deletado com sucesso!");
    }

    @GetMapping("/buscar/{titulo}")
    public ResponseEntity<List<Livro>> buscarLivroPorTitulo(@PathVariable String titulo) {
        List<Livro> livros = livroRepository.findByTituloContainingIgnoreCase(titulo);
        return ResponseEntity.ok(livros);
    }
}
