package br.udesc.acheaqui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    ListView servicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        servicesList = (ListView) findViewById(R.id.servicesList);

        List<String> testes = new ArrayList();
        testes.add("ITEM");
        testes.add("ITEM");
        testes.add("ITEM");
        testes.add("ITEM");
        testes.add("ITEM");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, testes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        servicesList.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addservice:
                Intent i = new Intent(HomeActivity.this, activity_cadastro_servico.class);
                startActivity(i);
                return true;
            default:
                return true;
        }
    }
}
