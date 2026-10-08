package br.umc.tds.core.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String nome;

    private Set<Role> roles = new HashSet<>();

    private boolean ativo = true;

    private boolean aprovado = false;

    private LocalDateTime criadoEm = LocalDateTime.now();

    public User() {
    }

    public User(String email, String password, String nome) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.roles.add(Role.ALUNO);
    }

    public User(String email, String password, String nome, Role role) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.roles.add(role);
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}