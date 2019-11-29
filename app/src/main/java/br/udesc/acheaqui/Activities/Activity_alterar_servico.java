package br.udesc.acheaqui.Activities;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.udesc.acheaqui.R;
import br.udesc.acheaqui.model.Servico;
import br.udesc.acheaqui.model.UsuarioSingleton;

public class Activity_alterar_servico extends AppCompatActivity {

    Button bt_imagem, bt_cadastrar, bt_encerrar;
    ImageView job_image;
    EditText text_nome;
    Uri image_URI;
    TextView text_telefone, text_valor, text_descricao;
    Spinner text_categoria;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference mStorageRef;
    String uriDownload;

    Servico s;
    String nome;
    String categoria;
    String descricao;
    String telefone;
    float valor;


    public static final int PICK_IMAGE = 1;
    public static final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_servico);
        final Servico servico = (Servico) getIntent().getSerializableExtra("Service");

        List<String> categoriaItens = new ArrayList<>();
        categoriaItens.add("Jardinagem");
        categoriaItens.add("Pintura");
        categoriaItens.add("Limpeza");
        categoriaItens.add("Doméstico");
        categoriaItens.add("Outros");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoriaItens);
        text_categoria = (Spinner) findViewById(R.id.serv_cat);
        text_categoria.setAdapter(adapter);
        bt_imagem = (Button) findViewById(R.id.bt_imagem);
        bt_cadastrar = (Button) findViewById(R.id.bt_cadastrar);
        bt_encerrar = (Button) findViewById(R.id.bt_encerrar);
        text_nome = (EditText) findViewById(R.id.serv_nome);
        text_categoria = (Spinner) findViewById(R.id.serv_cat);
        text_telefone = (TextView) findViewById(R.id.serv_tel);
        text_valor = (TextView) findViewById(R.id.serv_valor);
        text_descricao = (TextView) findViewById(R.id.serv_desc);
        job_image = (ImageView) findViewById(R.id.job_image);

        Picasso.get().load(servico.getUriImagem()).into(job_image);
        text_nome.setText(servico.getTitulo());
        text_descricao.setText(servico.getDescricao());
        text_telefone.setText(servico.getTelefone());
        text_valor.setText(String.valueOf(servico.getValor()));


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

                nome = text_nome.getText().toString();
                categoria = text_categoria.getSelectedItem().toString();
                descricao = text_descricao.getText().toString();
                telefone = text_telefone.getText().toString();
                valor = Float.parseFloat(text_valor.getText().toString());

                if (nome.trim().isEmpty() || descricao.trim().isEmpty() || telefone.trim().isEmpty() || valor < 0
                ) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Activity_alterar_servico.this);
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

                    Toast.makeText(Activity_alterar_servico.this, "Cadastrando servico, aguarde...", Toast.LENGTH_SHORT).show();
                    s = new Servico();

                    if (image_URI != null) {

                        final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(image_URI));
                        fileReference.putFile(image_URI)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                Uri downloadUrl = uri;
                                                uriDownload = downloadUrl.toString();

                                                s.setUid(servico.getUid());
                                                s.setTitulo(nome);
                                                s.setCategoria(categoria);
                                                s.setStatus(1);
                                                s.setTelefone(telefone);
                                                s.setDescricao(descricao);
                                                s.setValor(valor);
                                                s.setUriImagem(uriDownload);
                                                s.setIdUsuario(UsuarioSingleton.getInstance().getUsuario().getUId());

                                                databaseReference.child("Servico").child(s.getUid()).setValue(s)
                                                        .addOnCompleteListener(Activity_alterar_servico.this, new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(Activity_alterar_servico.this, "Serviço Alterado", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(Activity_alterar_servico.this, "Serviço não alterado", Toast.LENGTH_SHORT).show();

                                                                }
                                                            }
                                                        });
                                            }
                                        });

                                    }
                                });

                    } else {
                        s.setUid(servico.getUid());
                        s.setTitulo(nome);
                        s.setCategoria(categoria);
                        s.setStatus(1);
                        s.setTelefone(telefone);
                        s.setDescricao(descricao);
                        s.setValor(valor);
                        s.setUriImagem(servico.getUriImagem());
                        s.setIdUsuario(UsuarioSingleton.getInstance().getUsuario().getUId());

                        databaseReference.child("Servico").child(s.getUid()).setValue(s)
                                .addOnCompleteListener(Activity_alterar_servico.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Activity_alterar_servico.this, "Serviço Alterado", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Activity_alterar_servico.this, "Serviço não alterado", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                }
            }
        });

        if(servico.getStatus()==2){
            bt_encerrar.setEnabled(false);
        }

        bt_encerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome = text_nome.getText().toString();
                categoria = text_categoria.getSelectedItem().toString();
                descricao = text_descricao.getText().toString();
                telefone = text_telefone.getText().toString();
                valor = Float.parseFloat(text_valor.getText().toString());

                s = new Servico();
                s.setUid(servico.getUid());
                s.setTitulo(nome);
                s.setCategoria(categoria);
                s.setStatus(2);
                s.setTelefone(telefone);
                s.setDescricao(descricao);
                s.setValor(valor);
                s.setUriImagem(servico.getUriImagem());
                s.setIdUsuario(UsuarioSingleton.getInstance().getUsuario().getUId());

                databaseReference.child("Servico").child(s.getUid()).setValue(s)
                        .addOnCompleteListener(Activity_alterar_servico.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Activity_alterar_servico.this, "Serviço Encerrado", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Activity_alterar_servico.this, "Serviço não Encerrado", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }});
    }



    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Activity_alterar_servico.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            job_image = (ImageView) findViewById(R.id.job_image);
            job_image.setImageURI(data.getData());
            image_URI = data.getData();
        }
    }
}
