package br.unip.model;

public class ItemPedido {
    private Produto produto;
    private int quantidade;

    public ItemPedido() {}

    public Produto getProduto()  { return produto; }
    public int getQuantidade()   { return quantidade; }
    public void setProduto(Produto produto)    { this.produto = produto; }
    public void setQuantidade(int quantidade)  { this.quantidade = quantidade; }
}
