package br.udesc.acheaqui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.udesc.acheaqui.model.Servico;

public class ServiceActivity extends AppCompatActivity {

    private TextView titulo,preco,categoria,telefone,descricao;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Servico servico = (Servico) getIntent().getSerializableExtra("Service");

        image = this.findViewById(R.id.serviceImage);
        titulo = this.findViewById(R.id.serviceName);
        preco = this.findViewById(R.id.servicePrice);
        categoria = this.findViewById(R.id.serviceCategory);
        telefone = this.findViewById(R.id.servicePhone);
        descricao = this.findViewById(R.id.serviceDescription);

        titulo.setText(servico.getTitulo());
        preco.setText("Valor do serviço: " + servico.getValor());
        categoria.setText("Categoria: " + servico.getCategoria());
        telefone.setText("Telefone para contato: " + servico.getTelefone());
        descricao.setText("Descrição: " + servico.getDescricao());
        image.setImageURI(servico.getUriImagem());

    }

}
