package br.udesc.acheaqui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class activity_cadastro_servico extends AppCompatActivity {

    Button bt_imagem, bt_cadastrar;
    ImageView job_image;

    public static final int PICK_IMAGE = 1;
    public static final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);

        bt_imagem = (Button) findViewById(R.id.bt_imagem);
        bt_cadastrar = (Button) findViewById(R.id.bt_cadastrar);

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
                //cadastrar no banco
            }
        });
    }

    private  void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            job_image = (ImageView) findViewById(R.id.job_image);
            job_image.setImageURI(data.getData());
        }
    }
}
