package br.unip.model;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private int quantidadeEstoque;

    public Produto() {}

    // Getters
    public int getId()                  { return id; }
    public String getNome()             { return nome; }
    public double getPreco()            { return preco; }
    public int getQuantidadeEstoque()   { return quantidadeEstoque; }

    // Setters
    public void setId(int id)                        { this.id = id; }
    public void setNome(String nome)                 { this.nome = nome; }
    public void setPreco(double preco)               { this.preco = preco; }
    public void setQuantidadeEstoque(int quantidade) { this.quantidadeEstoque = quantidade; }

    @Override
    public String toString() {
        return nome + " (Estoque: " + quantidadeEstoque + ")";
    }
}