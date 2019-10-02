package br.udesc.acheaqui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button bt_login, bt_regitrar;
    EditText text_email, text_senha;

    DB_Usuario db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB_Usuario(this);

        text_email = (EditText) findViewById(R.id.text_email);
        text_senha = (EditText) findViewById(R.id.text_senha);


        bt_regitrar = (Button) findViewById(R.id.bt_registrar);
        bt_login = (Button) findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, RegistrarActivity.class);
                startActivity(i);

            }
        });

        bt_login = (Button) findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = text_email.getText().toString();
                String senha = text_senha.getText().toString();

                if (email.length() > 0 && senha.length() > 0) {
                    String resposta = db.login(email, senha);
                    if (resposta.equals("ok")) {
                        Toast.makeText(MainActivity.this, "Logado com Sucesso", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(in);
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                        alerta.setTitle("Aviso");
                        alerta
                                .setMessage("E-mail e senha não conferem")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        AlertDialog alertDialog = alerta.create();
                        alertDialog.show();
                    }

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                    alerta.setTitle("Aviso");
                    alerta
                            .setMessage("E-mail ou senha não informados")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                }
                            });
                    AlertDialog alertDialog = alerta.create();
                    alertDialog.show();
                }
            }
        });


        bt_regitrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, RegistrarActivity.class);
                startActivity(i);

            }
        });
    }
}
