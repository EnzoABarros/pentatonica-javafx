package br.pentatonica.guitarrascrud.usuarios;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private String CPF;

    public Usuario() {
        this.nome = "";
        this.email = "";
        this.senha = "";
        this.CPF = "";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
