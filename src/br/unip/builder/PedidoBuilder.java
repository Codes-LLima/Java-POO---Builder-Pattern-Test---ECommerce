package br.unip.builder;

import br.unip.model.*;

import java.util.ArrayList;
import java.util.List;

public class PedidoBuilder {
    private Pedido pedido;
    private List<ItemPedido> itens;

    public PedidoBuilder(){
        this.pedido = new Pedido();
        this.itens = new ArrayList<>();
    }

    public PedidoBuilder cliente(Cliente cliente){
        pedido.setCliente(cliente);
        return this;
    }

    public PedidoBuilder adicionarItem(Produto produto, int quantidade){
        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        itens.add(item);
        return this;
    }

    public ItemPedido buscarItem(int indice){
        return itens.get(indice);
    }

    public PedidoBuilder status(String status){
        pedido.setStatus(status);
        return this;
    }

    public PedidoBuilder removerItem(int indice) {
        itens.remove(indice);
        return this;
    }

    public Pedido build(){
        if (pedido.getCliente() == null)  throw new RuntimeException("Cliente obrigatório!");
        if (itens.isEmpty())              throw new RuntimeException("Pedido sem itens!");
        pedido.setItens(itens);
        return pedido;
    }
}
