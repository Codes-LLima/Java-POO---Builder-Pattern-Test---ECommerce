package br.unip.builder;

import br.unip.model.Cliente;

public class ClienteBuilder {
    private Cliente cliente;

    public ClienteBuilder() {
        this.cliente = new Cliente();
    }

    public ClienteBuilder id(int id) {
        cliente.setId(id);
        return this;
    }

    public ClienteBuilder nome(String nome) {
        cliente.setNome(nome);
        return this;
    }

    public ClienteBuilder email(String email) {
        cliente.setEmail(email);
        return this;
    }

    public ClienteBuilder telefone(String telefone) {
        cliente.setTelefone(telefone);
        return this;
    }

    public ClienteBuilder endereco(String endereco) {
        cliente.setEndereco(endereco);
        return this;
    }

    public Cliente build() {
        if (cliente.getNome() == null) throw new RuntimeException("Nome obrigatório!");
        return cliente;
    }
}
