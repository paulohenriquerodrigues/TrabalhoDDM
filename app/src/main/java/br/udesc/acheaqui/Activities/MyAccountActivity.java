package br.udesc.acheaqui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.udesc.acheaqui.R;
import br.udesc.acheaqui.model.Usuario;
import br.udesc.acheaqui.model.UsuarioSingleton;

public class MyAccountActivity extends AppCompatActivity {

    EditText text_nome, text_email, text_senha, text_cidade, text_bairro;
    RadioButton rb_masc, rb_fem;
    Button bt_alterar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        text_nome = (EditText) findViewById(R.id.text_nome);
        text_email = (EditText) findViewById(R.id.text_email);
        text_senha = (EditText) findViewById(R.id.text_senha);
        text_cidade = (EditText) findViewById(R.id.text_cidade);
        text_bairro = (EditText) findViewById(R.id.text_bairro);
        rb_masc = (RadioButton) findViewById(R.id.rb_masc);
        rb_fem = (RadioButton) findViewById(R.id.rb_fem);
        bt_alterar = (Button) findViewById(R.id.bt_alterar);

        inicializarFirebase();

        text_nome.setText(UsuarioSingleton.getInstance().getUsuario().getNome());
        text_cidade.setText(UsuarioSingleton.getInstance().getUsuario().getCidade());
        text_bairro.setText(UsuarioSingleton.getInstance().getUsuario().getEstado());
        text_email.setText(UsuarioSingleton.getInstance().getUsuario().getEmail());
        text_senha.setText(UsuarioSingleton.getInstance().getUsuario().getSenha());

        if(UsuarioSingleton.getInstance().getUsuario().getSexo() == 1){
            rb_masc.setSelected(true);
            rb_fem.setSelected(false);

        }else{
            rb_masc.setSelected(false);
            rb_fem.setSelected(true);

        }

        bt_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = text_nome.getText().toString();
                String email = text_email.getText().toString();
                String senha = text_senha.getText().toString();
                String cidade = text_cidade.getText().toString();
                String bairro = text_bairro.getText().toString();
                int sexo = 0;

                if(rb_masc.isSelected()){
                    sexo = 1;
                }
                if(rb_fem.isSelected()){
                    sexo = 2;
                }

                UsuarioSingleton.getInstance().getUsuario().setNome(nome);
                UsuarioSingleton.getInstance().getUsuario().setEmail(email);
                UsuarioSingleton.getInstance().getUsuario().setSenha(senha);
                UsuarioSingleton.getInstance().getUsuario().setCidade(cidade);
                UsuarioSingleton.getInstance().getUsuario().setEstado(bairro);
                UsuarioSingleton.getInstance().getUsuario().setSexo(sexo);


                databaseReference.child("Usuario").child(UsuarioSingleton.getInstance().getUsuario().getUId())
                        .setValue(UsuarioSingleton.getInstance().getUsuario()) .addOnCompleteListener(MyAccountActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MyAccountActivity.this, "Usuário Alterado", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MyAccountActivity.this, HomeActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(MyAccountActivity.this, "Usuário não Alterado", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
            }
        );}

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MyAccountActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


    }
}