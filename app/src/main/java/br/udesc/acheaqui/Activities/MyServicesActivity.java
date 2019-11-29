package br.udesc.acheaqui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.udesc.acheaqui.R;
import br.udesc.acheaqui.adapter.ServicoAdapter;
import br.udesc.acheaqui.model.Servico;
import br.udesc.acheaqui.model.UsuarioSingleton;

public class MyServicesActivity extends AppCompatActivity {


    ListView servicesList;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Servico> servicos = new ArrayList<Servico>();
    private ServicoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_services);

        servicesList = (ListView) findViewById(R.id.servicesList);
        servicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = servicesList.getItemAtPosition(position);
                Servico servico = (Servico) o;
                Intent i = new Intent(MyServicesActivity.this, Activity_alterar_servico.class);
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
                    if(s.getIdUsuario().equals(UsuarioSingleton.getInstance().getUsuario().getUId())){
                        servicos.add(s);
                    }

                }

                adapter = new ServicoAdapter(MyServicesActivity.this,
                        servicos);
                servicesList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MyServicesActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}