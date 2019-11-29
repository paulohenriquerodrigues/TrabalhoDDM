package br.udesc.acheaqui.Activities;


import androidx.annotation.NonNull;
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
import android.widget.RadioGroup;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

import br.udesc.acheaqui.R;
import br.udesc.acheaqui.model.Usuario;

public class RegistrarActivity extends AppCompatActivity {

    EditText text_nome, text_email, text_senha, text_cidade, text_estado;
    RadioButton rb_masc, rb_fem;
    Button bt_registrar;
    RadioGroup radioGroup;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        text_nome = (EditText) findViewById(R.id.text_nome);
        text_email = (EditText) findViewById(R.id.text_email);
        text_senha = (EditText) findViewById(R.id.text_senha);
        text_cidade = (EditText) findViewById(R.id.text_cidade);
        text_estado = (EditText) findViewById(R.id.text_estado);
        rb_masc = (RadioButton) findViewById(R.id.rb_masc);
        rb_fem = (RadioButton) findViewById(R.id.rb_fem);
        bt_registrar = (Button) findViewById(R.id.bt_registrar);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);


        inicializarFirebase();

        bt_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = text_nome.getText().toString();
                String email = text_email.getText().toString();
                String senha = text_senha.getText().toString();
                String cidade = text_cidade.getText().toString();
                String estado = text_estado.getText().toString();
                String sexo = "Masc";

                if (nome.equals("") ||
                        email.equals("") ||
                        senha.equals("") ||
                        cidade.equals("") ||
                        estado.equals("")) {

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

                } else {

                    Usuario u = new Usuario();
                    u.setUId(UUID.randomUUID().toString());
                    u.setNome(nome);
                    u.setEmail(email);
                    u.setSenha(senha);
                    u.setCidade(cidade);
                    u.setEstado(estado);

                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.rb_masc:
                            u.setSexo(1);
                            break;
                        case R.id.rb_fem:
                            u.setSexo(2);
                            break;
                    }

                    databaseReference.child("Usuario").child(u.getUId()).setValue(u)
                            .addOnCompleteListener(RegistrarActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrarActivity.this, "Usuário cadastrado", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(RegistrarActivity.this, MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(RegistrarActivity.this, "Usuário não cadastrado", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(RegistrarActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


    }
}
