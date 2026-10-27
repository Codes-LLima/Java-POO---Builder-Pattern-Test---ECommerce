package br.unip.builder;

import br.unip.model.Entrega;
import br.unip.model.Pedido;
import java.time.LocalDate;

public class EntregaBuilder {
    private Entrega entrega;

    public EntregaBuilder(){
        this.entrega = new Entrega();
    }

    public EntregaBuilder id(int id){
        entrega.setId(id);
        return this;
    }

    public EntregaBuilder pedido(Pedido pedido) {
        entrega.setPedido(pedido);
        return this;
    }

    public EntregaBuilder pedidoId(int id) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        entrega.setPedido(pedido);
        return this;
    }
    public EntregaBuilder status(String status) {
        entrega.setStatus(status);
        return this;
    }

    public EntregaBuilder dataPrevisao(LocalDate data) {
        entrega.setDataPrevisao(data);
        return this;
    }

    public EntregaBuilder dataEntrega(LocalDate data) {
        entrega.setDataEntrega(data);
        return this;
    }

    public Entrega build() {
        if (entrega.getPedido() == null) throw new RuntimeException("Pedido obrigatório!");
        if (entrega.getStatus() == null) throw new RuntimeException("Status obrigatório!");
        return entrega;
    }

}
