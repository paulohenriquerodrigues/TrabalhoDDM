package br.udesc.acheaqui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    RadioGroup radioGroup;
    Usuario user;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        user = UsuarioSingleton.getInstance().getUsuario();

        text_nome = (EditText) findViewById(R.id.text_nome);
        text_email = (EditText) findViewById(R.id.text_email);
        text_senha = (EditText) findViewById(R.id.text_senha);
        text_cidade = (EditText) findViewById(R.id.text_cidade);
        text_bairro = (EditText) findViewById(R.id.text_bairro);
        rb_masc = (RadioButton) findViewById(R.id.rb_masc);
        rb_fem = (RadioButton) findViewById(R.id.rb_fem);
        bt_alterar = (Button) findViewById(R.id.bt_alterar);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);


        inicializarFirebase();

        text_nome.setText(user.getNome());
        text_cidade.setText(user.getCidade());
        text_bairro.setText(user.getEstado());
        text_email.setText(user.getEmail());
        text_senha.setText(user.getSenha());

        if(user.getSexo() == 1){
            rb_masc.setChecked(true);

        }else{
            rb_fem.setChecked(true);

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

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rb_masc:
                        sexo=1;
                        break;
                    case R.id.rb_fem:
                        sexo=2;
                        break;
                }


                user.setNome(nome);
                user.setEmail(email);
                user.setSenha(senha);
                user.setCidade(cidade);
                user.setEstado(bairro);
                user.setSexo(sexo);

                databaseReference.child("Usuario").child(user.getUId())
                        .setValue(user) .addOnCompleteListener(MyAccountActivity.this, new OnCompleteListener<Void>() {
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