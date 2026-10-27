package br.unip.model;

public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;

    public Cliente() {}

    public int getId() {return id;}
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }

    public void setId(int id) {this.id = id;}

    public void setNome(String nome) { this.nome = nome;}

    public void setEmail(String email) {this.email = email;}

    public void setTelefone(String telefone) {this.telefone = telefone;}

    public void setEndereco(String endereco) {this.endereco = endereco;}

    @Override
    public String toString() {return nome;}


}

