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

import com.biblioteca.biblioteca.DTO.UsuarioDto;
import com.biblioteca.biblioteca.entity.Usuario;
import com.biblioteca.biblioteca.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    // insere o repositorio de usuários
    @Autowired
    private UsuarioRepository UsuarioRepository;

    // Busca todos os usuários
    @GetMapping()
    public ResponseEntity<List<Usuario>> buscarUsuarios() {

        List<Usuario> usuarios = this.UsuarioRepository.findAll();
        return ResponseEntity.ok(usuarios); // Retorna a lista de usuários
    }

    // Busca o usuário pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = this.UsuarioRepository.findById(id).orElse(null);

        if (usuario == null) { // Verifica se o usuário nao foi encontrado
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);

    }

    // É usado para criar um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid UsuarioDto dados) {
        Usuario usuarioCriado = new Usuario(dados); // Cria um novo usuário a partir dos dados recebidos

        this.UsuarioRepository.save(usuarioCriado); // Salva o novo usuário no repositório
        return ResponseEntity.ok(usuarioCriado);
    }

    // Serve para atualizar um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDto dados) {
        Usuario usuario = this.UsuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setCpf(dados.cpf());

        this.UsuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    // Serve para remover um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        Usuario usuario = this.UsuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.notFound().build();

        }

        this.UsuarioRepository.delete(usuario); // Remove o usuário do repositório

        return ResponseEntity.ok("Usuário" + " " + usuario.getNome() + " " + "deletado com sucesso! ");
    }

}
