package br.unip.util;

import br.unip.model.Usuario;
public class Sessao {
    private static Usuario usuarioLogado;

    public static void setUsuarioLogado(Usuario usuario){
        usuarioLogado = usuario;
    }

    public static Usuario getUsuario(){
        return usuarioLogado;
    }
    public static void encerrar() {
        usuarioLogado = null;
    }
}
