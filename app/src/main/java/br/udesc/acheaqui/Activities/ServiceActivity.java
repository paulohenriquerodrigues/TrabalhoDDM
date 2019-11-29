package br.udesc.acheaqui.Activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;

import br.udesc.acheaqui.R;
import br.udesc.acheaqui.model.Servico;

import static android.content.Intent.*;

public class ServiceActivity extends AppCompatActivity {

    private TextView titulo,preco,categoria,descricao;
    private ImageView image;
    private ImageButton telefone, whats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Servico servico = (Servico) getIntent().getSerializableExtra("Service");

        image = this.findViewById(R.id.serviceImage);
        titulo = this.findViewById(R.id.serviceName);
        preco = this.findViewById(R.id.servicePrice);
        categoria = this.findViewById(R.id.serviceCategory);
        telefone = this.findViewById(R.id.ButtonPhone);
        whats = this.findViewById(R.id.buttonWhats);
        descricao = this.findViewById(R.id.serviceDescription);

        titulo.setText(servico.getTitulo());
        preco.setText("Valor do serviço: " + servico.getValor());
        categoria.setText("Categoria: " + servico.getCategoria());
        descricao.setText("Descrição: " + servico.getDescricao());
        Picasso.get().load(servico.getUriImagem()).into(image);
        telefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:"+servico.getTelefone());
                Intent i = new Intent(ACTION_CALL, uri);
                if(ActivityCompat.checkSelfPermission(ServiceActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ServiceActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
                startActivity(i);
            }
        });

        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = servico.getTelefone().replaceAll("\\(", "");
                numero = numero.replaceAll("\\)", "");
                numero = numero.replace("+", "");
                numero = numero.replaceAll(" ","");
                Uri uri = Uri.parse("smsto:"+numero);
                System.out.println(numero);
                Intent i = new Intent(ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(i, ""));
            }
        });



    }

}
