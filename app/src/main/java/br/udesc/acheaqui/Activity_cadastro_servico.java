package br.udesc.acheaqui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;


import java.util.UUID;

import br.udesc.acheaqui.model.Servico;

public class Activity_cadastro_servico extends AppCompatActivity {

    Button bt_imagem, bt_cadastrar;
    ImageView job_image;
    EditText text_nome;
    Uri image_URI;
    TextView text_categoria, text_telefone, text_valor, text_descricao;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference mStorageRef;


    public static final int PICK_IMAGE = 1;
    public static final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);


        bt_imagem = (Button) findViewById(R.id.bt_imagem);
        bt_cadastrar = (Button) findViewById(R.id.bt_cadastrar);

        text_nome = (EditText) findViewById(R.id.serv_nome);
        text_categoria = (TextView) findViewById(R.id.serv_cat);
        text_telefone = (TextView) findViewById(R.id.serv_tel);
        text_valor = (TextView) findViewById(R.id.serv_valor);
        text_descricao = (TextView) findViewById(R.id.serv_desc);

        inicializarFirebase();


        bt_imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = text_nome.getText().toString();
                String categoria = text_categoria.getText().toString();
                String descricao = text_descricao.getText().toString();
                String telefone = text_telefone.getText().toString();
                float valor = Float.parseFloat(text_valor.getText().toString());

                if (nome.trim().isEmpty() || categoria.trim().isEmpty() || descricao.trim().isEmpty() || telefone.trim().isEmpty() || valor < 0
                    //        || image_URI == null
                ) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Activity_cadastro_servico.this);
                    alerta.setTitle("Campos em branco");
                    alerta
                            .setMessage("Por favor preencher todos os campos antes de efetuar o cadastro")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog alertDialog = alerta.create();
                    alertDialog.show();
                } else {
                    Servico s = new Servico();
                    s.setUid(UUID.randomUUID().toString());
                    s.setTitulo(nome);
                    s.setCategoria(categoria);
                    s.setStatus(1);
                    s.setTelefone(telefone);
                    s.setDescricao(descricao);
                    s.setValor(valor);
                    // s.seturiImagem(image_URI);

//                    StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
//                            + "." + getFileExtension(image_URI));
//                    fileReference.putFile(image_URI);

//
//                    String uploadId = databaseReference.push().getKey();
//                    s.setUrlImagem(uploadId);

                    databaseReference.child("Servico").child(s.getUid()).setValue(s)
                            .addOnCompleteListener(Activity_cadastro_servico.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Activity_cadastro_servico.this, "Serviço cadastrado", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Activity_cadastro_servico.this, "Serviço não cadastrado", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Activity_cadastro_servico.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            job_image = (ImageView) findViewById(R.id.job_image);
            job_image.setImageURI(data.getData());
            image_URI = data.getData();
        }
    }
}
