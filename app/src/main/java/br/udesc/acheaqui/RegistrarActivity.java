package br.udesc.acheaqui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegistrarActivity extends AppCompatActivity {

    EditText text_nome, text_email, text_senha, text_cidade, text_bairro;
    RadioButton rb_masc, rb_fem;
    Button bt_registrar;

    DB_Usuario db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        db = new DB_Usuario(this);

        text_nome = (EditText) findViewById(R.id.text_nome);
        text_email = (EditText) findViewById(R.id.text_email);
        text_senha = (EditText) findViewById(R.id.text_senha);
        text_cidade = (EditText) findViewById(R.id.text_cidade);
        text_bairro = (EditText) findViewById(R.id.text_bairro);
        rb_masc = (RadioButton) findViewById(R.id.rb_masc);
        rb_fem = (RadioButton) findViewById(R.id.rb_fem);
        bt_registrar = (Button) findViewById(R.id.bt_registrar);

        bt_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = text_nome.getText().toString();
                String email = text_email.getText().toString();
                String senha = text_senha.getText().toString();
                String cidade = text_cidade.getText().toString();
                String bairro = text_bairro.getText().toString();
                String sexo = "Masc";

                if(nome.equals("") ||
                   email.equals("") ||
                   senha.equals("") ||
                   cidade.equals("") ||
                   bairro.equals("")){

                    AlertDialog.Builder alerta = new AlertDialog.Builder(RegistrarActivity.this);
                    alerta.setTitle("Aviso");
                    alerta
                            .setMessage("Campo Obrigatório Não Preenchido")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog alertDialog = alerta.create();
                    alertDialog.show();

                }else{
                    long resp = db.addUsuario(nome, email,senha,cidade,bairro,sexo);
                    if(resp > 0) {
                        Toast.makeText(RegistrarActivity.this, "Usuário cadastrado", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegistrarActivity.this, MainActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(RegistrarActivity.this, "Usuário não cadastrado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
