package br.udesc.acheaqui.model;

public class UsuarioSingleton {

    private  static UsuarioSingleton instancia;

    private Usuario usuario;


    public UsuarioSingleton() {
    }

    public static synchronized UsuarioSingleton getInstance(){
        if(instancia == null){
            instancia = new UsuarioSingleton();
        }
        return instancia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;

    }
}
