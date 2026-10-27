package br.unip.model;

import java.time.LocalDate;

public class Entrega {
    private int id;
    private Pedido pedido;
    private String status;
    private LocalDate dataPrevisao;
    private LocalDate dataEntrega;

    public static final String PENDENTE    = "pendente";
    public static final String EM_ROTA     = "em_rota";
    public static final String ENTREGUE    = "entregue";
    public static final String CANCELADO   = "cancelado";

    public Entrega() {}

    public int getId()                    { return id; }
    public Pedido getPedido()             { return pedido; }
    public String getStatus()             { return status; }
    public LocalDate getDataPrevisao()    { return dataPrevisao; }
    public LocalDate getDataEntrega()     { return dataEntrega; }

    public void setId(int id)                        { this.id = id; }
    public void setPedido(Pedido pedido)             { this.pedido = pedido; }
    public void setStatus(String status)             { this.status = status; }
    public void setDataPrevisao(LocalDate data)      { this.dataPrevisao = data; }
    public void setDataEntrega(LocalDate data)       { this.dataEntrega = data; }
}
