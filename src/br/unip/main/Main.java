package br.unip.main;


import br.unip.builder.Pedido;
import br.unip.builder.Produto;
import br.unip.builder.Usuario;

import static br.unip.enums.Status.*;
import static br.unip.enums.FormaPagamento.*;
import java.util.List;

public class Main {

    public static void main(String[] args){

        // Criação do usuário usando Builder
        Usuario id001 = new Usuario.Builder()
                .nome("Lucas Lima")
                .email("Lucaslindo@mail.com")
                .idade(21)
                .profissao("Garoto de Programa")
                .telefone("(11) 4002-8922")
                .cidade("São Paulo")
                .ativo(true)
                .build();

        // Declaração de produtos usando o Builder
        Produto caderno = new Produto.Builder()
                .nome("Caderno")
                .categoria("Escolar")
                .preco(40.00d)
                .quantidadeEmEstoque(200).build();

        Produto mochila = new Produto.Builder()
                .nome("Mochila Escolar de Costas Reforçada")
                .categoria("Material Escolar")
                .preco(149.90)
                .quantidadeEmEstoque(15)
                .build();

        Produto lapis = new Produto.Builder()
                .nome("Caixa de Lápis Preto HB nº 2")
                .categoria("Material Escolar")
                .preco(12.50)
                .quantidadeEmEstoque(120)
                .build();

        //Criando a lista de produtos "Comprados"
        List<Produto> listaDeProduto = List.of(
                caderno,
                mochila,
                lapis);

        // Criando/instânciando o pedido usado
        Pedido pedidoNum234 = new Pedido.Builder()
                .cliente(id001)
                .produtosComprados(listaDeProduto)
                .endereco("Rua dos bobos, n°7 - Parque Engraçado")
                .desconto(0d)
                .formaPagamento(CARTAO_DE_CREDITO)
                .status(ATIVO)
                .build();

        // Exibe no console um resumo de tudo
        System.out.printf(pedidoNum234.toString());
    }

}

