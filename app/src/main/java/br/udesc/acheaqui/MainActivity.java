package br.udesc.acheaqui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.udesc.acheaqui.conexao.Conexao;


public class MainActivity extends AppCompatActivity {


    Button bt_login, bt_regitrar;
    EditText text_email, text_senha;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

                login(email, senha);
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

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Logado com Sucesso", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(i);
                    }else{
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
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
    }


}
