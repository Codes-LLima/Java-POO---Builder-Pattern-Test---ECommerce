package br.unip.builder;

public class Usuario {
    private final String nome;
    private final String email;
    private final int idade;
    private final String telefone;
    private final String cidade;
    private final String profissao;
    private final Boolean ativo;

    public Usuario(Builder builder){
        this.nome = builder.nome;
        this.email = builder.email;
        this.idade = builder.idade;
        this.telefone = builder.telefone;
        this.cidade = builder.cidade;
        this.profissao = builder.profissao;
        this.ativo = builder.ativo;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getIdade() {
        return idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public String getProfissao() {
        return profissao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public String toString(){
        return " --- Usuario ---\n"+
                "Nome: "+ getNome() + "\n" +
                "E-mail: " + getEmail() + "\n" +
                "Idade: " + getIdade() + "\n" +
                "Profissão: " + getProfissao() + "\n" +
                "Telefone: " + getTelefone() + "\n" +
                "Cidade: " + getCidade() + "\n" +
                "Situação: " + getAtivo() + "\n";

    }
    public static class Builder{
        private String nome;
        private String email;
        private int idade;
        private String telefone;
        private String cidade;
        private String profissao;
        private Boolean ativo;


        public Builder nome(String nome){
            this.nome = nome;
            return this;
        }

        public Builder email(String email)
        {
            this.email = email;
            return this;
        }

        public Builder idade(int idade){
            this.idade = idade;
            return this;
        }

        public Builder telefone(String telefone)
        {
            this.telefone = telefone;
            return this;
        }

        public Builder cidade(String cidade){
            this.cidade = cidade;
            return this;
        }

        public Builder profissao(String profissao){
            this.profissao = profissao;
            return this;
        }

        public Builder ativo(Boolean ativo)
        {
            this.ativo = ativo;
            return this;
        }

        public Usuario build(){
            if (nome == null || nome.isBlank()){
                throw new IllegalArgumentException("Nome é obrigatório!! ");
            }
            if (email == null || email.isBlank()){
                throw new IllegalArgumentException("E-mail é obrigatório!!");
            }

            return new Usuario(this);
        }
    }

}
