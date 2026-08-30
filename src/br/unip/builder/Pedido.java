package br.unip.builder;

import br.unip.enums.FormaPagamento;
import br.unip.enums.Status;
import java.util.List;

public class Pedido {
    private final Usuario cliente;
    private final List<Produto> produtosComprados;
    private final String endereco;
    private final FormaPagamento formaPagamento;
    private final double desconto;
    private final Status status;

    public Pedido(Builder builder){
        this.cliente = builder.cliente;
        this.produtosComprados = builder.produtosComprados;
        this.endereco = builder.endereco;
        this.formaPagamento = builder.formaPagamento;
        this.desconto = builder.desconto;
        this.status = builder.status;
    }

    @Override
    public String toString() {
        String listaCompra = "(";

        for (int i = 0; i < produtosComprados.size(); i++){
            if (i == produtosComprados.size() - 1){
                listaCompra += produtosComprados.get(i).getNome() + ")";
                break;
            }
            listaCompra += produtosComprados.get(i).getNome() + " | ";

        }
        return "Pedido {" + "\n" +
                "Cliente: " + cliente.getNome() + "\n" +
                "Itens: " + listaCompra + "\n" +
                "Endereço: " + endereco + "\n" +
                "Pagamento: " + formaPagamento + "\n" +
                "Desconto: R$" + desconto + "\n" +
                "Status: " + status +
                '}';
    }

    public static class Builder{
        private Usuario cliente;
        private List<Produto> produtosComprados;
        private String endereco;
        private FormaPagamento formaPagamento;
        private double desconto;
        private Status status;

        public Builder cliente(Usuario cliente){
            this.cliente = cliente;
            return this;
        }

        public Builder produtosComprados(List<Produto> produtosComprados){
            this.produtosComprados = produtosComprados;
            return this;
        }

        public Builder endereco(String endereco){
            this.endereco = endereco;
            return this;
        }

        public Builder formaPagamento(FormaPagamento formaPagamento){
            this.formaPagamento = formaPagamento;
            return this;
        }

        public Builder desconto(double desconto){
            this.desconto = desconto;
            return this;
        }

        public Builder status(Status status){
            this.status = status;
            return this;
        }

        public Pedido build(){

            if (this.cliente == null){
                throw new IllegalArgumentException("AVISO! \nCliente não encontrado ou não informado. ");
            }

            if (endereco == null || endereco.isBlank()){
                throw new IllegalArgumentException("AVISO! \nEndereço vazio.");
            }

            if (formaPagamento == null) {
                throw new IllegalArgumentException("AVISO! \nForma de pagamento não informada. ");
            }

            if (produtosComprados == null || produtosComprados.isEmpty()){
                throw new IllegalArgumentException("AVISO! \nLista de compras vazia. ");
            }
            return new Pedido(this);
        }
    }



}
