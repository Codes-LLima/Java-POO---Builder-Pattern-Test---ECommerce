package br.unip.builder;

import br.unip.model.Produto;


public class ProdutoBuilder {
    private Produto produto;

    public ProdutoBuilder(){
        this.produto = new Produto();
    }

    public ProdutoBuilder id(int id){
        produto.setId(id);
        return this;
    }

    public ProdutoBuilder nome(String nome){
        produto.setNome(nome);
        return this;
    }

    public ProdutoBuilder preco(double preco){
        produto.setPreco(preco);
        return this;
    }

    public ProdutoBuilder quantidadeEstoque(int quantidade){
        produto.setQuantidadeEstoque(quantidade);
        return this;
    }

    public Produto build(){
        if (produto.getNome() == null){ throw new RuntimeException("Nome do produto obrigatório!");}
        if (produto.getPreco() == 0.0) {throw new RuntimeException("");}
        return produto;
    }
}
