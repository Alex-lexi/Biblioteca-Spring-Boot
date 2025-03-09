package com.biblioteca.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.biblioteca.biblioteca.DTO.EmprestimoDto;
import com.biblioteca.biblioteca.entity.Emprestimo;
import com.biblioteca.biblioteca.entity.Livro;
import com.biblioteca.biblioteca.entity.Usuario;
import com.biblioteca.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.biblioteca.repository.LivroRepository;
import com.biblioteca.biblioteca.repository.UsuarioRepository;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    @PostMapping
    public ResponseEntity<String> inserirEmprestimo(@RequestBody @Valid EmprestimoDto dados) {
        Usuario usuario = usuarioRepository.findById(dados.usuarioId()).orElse(null);
        Livro livro = livroRepository.findById(dados.livroId()).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
        
        if (livro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado.");
        }

        if (!livro.isDisponivel()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Livro não está disponível para empréstimo.");
        }

        Emprestimo emprestimo = new Emprestimo(usuario, livro, LocalDate.now(), null);
        emprestimoRepository.save(emprestimo);
        
        // Atualiza a disponibilidade do livro
        livro.setDisponivel(false);
        livroRepository.save(livro);
        
        return ResponseEntity.ok("Empréstimo realizado com sucesso!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarEmprestimoPorId(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElse(null);
        if (emprestimo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(emprestimo);
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarTodosEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/historico/{usuarioId}")
    public ResponseEntity<List<Emprestimo>> listarHistoricoEmprestimos(@PathVariable Long usuarioId) {
        List<Emprestimo> emprestimos = emprestimoRepository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(emprestimos);
    }

    @PutMapping("/{id}/devolucao")
    public ResponseEntity<String> atualizarDataDevolucao(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElse(null);

        if (emprestimo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empréstimo não encontrado.");
        }

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimoRepository.save(emprestimo);

        // Atualiza a disponibilidade do livro
        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepository.save(livro);
        
        return ResponseEntity.ok("Devolução registrada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEmprestimo(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElse(null);

        if (emprestimo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empréstimo não encontrado.");
        }

        // Atualiza a disponibilidade do livro antes de deletar o empréstimo
        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepository.save(livro);

        emprestimoRepository.delete(emprestimo);
        return ResponseEntity.ok("Empréstimo deletado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarEmprestimo(@PathVariable Long id, @RequestBody @Valid EmprestimoDto dados) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElse(null);

        if (emprestimo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empréstimo não encontrado.");
        }

        // Atualiza os dados do empréstimo
        Usuario usuario = usuarioRepository.findById(dados.usuarioId()).orElse(null);
        Livro livro = livroRepository.findById(dados.livroId()).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        if (livro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado.");
        }

        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimoRepository.save(emprestimo);

        return ResponseEntity.ok("Empréstimo atualizado com sucesso!");
    }
}