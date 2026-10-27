package br.unip.model;

import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private String status;
    private String dataPedido;

    public static final String PENDENTE  = "pendente";
    public static final String APROVADO  = "aprovado";
    public static final String CANCELADO = "cancelado";

    public Pedido() {}

    public Cliente getCliente()         { return cliente; }
    public List<ItemPedido> getItens()  { return itens; }
    public String getStatus()           { return status; }

    public void setId(int id) {this.id = id;}
    public void setDataPedido(String dataPedido) {this.dataPedido = dataPedido;}
    public void setCliente(Cliente c)          { this.cliente = c; }
    public void setItens(List<ItemPedido> i)   { this.itens = i; }
    public void setStatus(String s)            { this.status = s; }

    public int getId() {
        return this.id;
    }
}
