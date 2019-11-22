package br.udesc.acheaqui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.udesc.acheaqui.model.Servico;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    ListView servicesList;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Servico> servicos = new ArrayList<Servico>();
    private ArrayAdapter<Servico> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        servicesList = (ListView) findViewById(R.id.servicesList);
        servicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = servicesList.getItemAtPosition(position);
                    Servico servico = (Servico)o;
                    Intent i = new Intent(HomeActivity.this, ServiceActivity.class);
                    i.putExtra("Service", servico);
                    startActivity(i);
            }
        });
        inicializarFirebase();
        eventoDataBase();


    }

    private void eventoDataBase() {
        databaseReference.child("Servico").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicos.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                    Servico s = objSnapshot.getValue(Servico.class);
                    servicos.add(s);
                }
                adapter = new ArrayAdapter<Servico>(HomeActivity.this,
                        android.R.layout.simple_list_item_1, servicos);
                servicesList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(HomeActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.addservice:
                i = new Intent(HomeActivity.this, Activity_cadastro_servico.class);
                startActivity(i);
                return true;
            case R.id.myAccount:
                i = new Intent(HomeActivity.this, MyAccountActivity.class);
                startActivity(i);
                return true;
            default:
                return true;
        }
    }

}