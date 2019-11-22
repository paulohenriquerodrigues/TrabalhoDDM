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


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.udesc.acheaqui.model.Usuario;


public class MainActivity extends AppCompatActivity {

    boolean valido = false;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<Usuario> usuarios = new ArrayList<>();


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

        inicializarFirebase();
    }

    private void login(final String email, final String senha) {


        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarios.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Usuario s = objSnapshot.getValue(Usuario.class);
                    usuarios.add(s);
                }

                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i).getEmail().equals(email) &&
                            usuarios.get(i).getSenha().equals(senha)) {
                        valido = true;
                    }

                }
                if (valido) {
                    System.out.println(valido);
                    Toast.makeText(MainActivity.this, "Logado com Sucesso", Toast.LENGTH_SHORT).show();
                    Intent x = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(x);

                    valido = false;
                    text_email.setText("");
                    text_senha.setText("");

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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
