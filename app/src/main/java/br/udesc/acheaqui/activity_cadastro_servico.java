package br.udesc.acheaqui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class activity_cadastro_servico extends AppCompatActivity {

    Button bt_imagem, bt_cadastrar;
    ImageView job_image;
    EditText serv_nome, serv_cat, serv_desc, serv_tel, serv_valor;
    String image_URI;

    public static final int PICK_IMAGE = 1;
    public static final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);

        bt_imagem = (Button) findViewById(R.id.bt_imagem);
        bt_cadastrar = (Button) findViewById(R.id.bt_cadastrar);
        serv_nome = (EditText) findViewById(R.id.serv_nome);
        serv_cat = (EditText) findViewById(R.id.serv_cat);
        serv_desc = (EditText) findViewById(R.id.serv_desc);
        serv_tel = (EditText) findViewById(R.id.serv_tel);
        serv_valor = (EditText) findViewById(R.id.serv_valor);

        bt_imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions);
                    }else{
                        pickImage();
                    }
                }else{
                    pickImage();
                }
                */
                pickImage();
            }
        });

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = serv_nome.getText().toString();
                String categoria = serv_cat.getText().toString();
                String descricao = serv_desc.getText().toString();
                String telefone = serv_tel.getText().toString();
                String valor = serv_valor.getText().toString();

                if (nome.trim().isEmpty() || categoria.trim().isEmpty() || descricao.trim().isEmpty() || telefone.trim().isEmpty() || valor.trim().isEmpty() || image_URI == null) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(activity_cadastro_servico.this);
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
                    //TODO Salvar no banco!
                    Toast.makeText(activity_cadastro_servico.this, nome + " " + categoria + " " + descricao + " " + telefone + " " + valor + " " + image_URI, Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            image_URI = String.valueOf(data.getData());
        }
    }
}
