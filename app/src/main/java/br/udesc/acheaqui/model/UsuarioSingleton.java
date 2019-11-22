package br.udesc.acheaqui.model;

public class UsuarioSingleton {

    private  static UsuarioSingleton instancia;

    public UsuarioSingleton() {
    }

    public static synchronized UsuarioSingleton getInstance(){
        if(instancia == null){
            instancia = new UsuarioSingleton();
        }
        return instancia;
    }
}
