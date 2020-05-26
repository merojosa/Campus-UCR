package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;

public class MisComunidades extends AppCompatActivity {

    ArrayList<Comunidad> comunidadList;

    private RecyclerView mRecyclerView;
    private ComunidadAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_comunidades);

        Intent intent = getIntent();
        ArrayList<String> misComunidades = intent.getStringArrayListExtra("misComunidades");
        ArrayList<String> comunidadesTotales = intent.getStringArrayListExtra("comunidadesTotales");
        createComunidadList(misComunidades);
        buildRecyclerView();

        //Instanciación del botón flotante
        FloatingActionButton fabJoinCommunity = findViewById(R.id.fabJoinCommunity);

        fabJoinCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MisComunidades.this, ComunidadesRedMujeres.class).putStringArrayListExtra("comunidadesTotales", comunidadesTotales));
            }
        });

    }

    public void createComunidadList(List<String> comunidades)
    {
        comunidadList = new ArrayList<>();

        for(int i =0; i< comunidades.size(); ++i)
        {
            //Toast.makeText(getApplicationContext(), "Comunidades[" + i +"]"+ comunidades.get(i), Toast.LENGTH_SHORT).show();
            comunidadList.add(new Comunidad(R.drawable.community,
                    comunidades.get(i),
                    comunidades.size() + " miembros",
                    new ArrayList<String>(),
                    "Descripción genérica de ejemplo creada para la Comunidad "+ comunidades.get(0) + " del sistema"));
        }
    }

    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerViewMyCommunities);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ComunidadAdapter(comunidadList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ComunidadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(MisComunidades.this, ComunidadDetalle.class).putExtra("comunidad", comunidadList.get(position)).putExtra("vis", 0));
            }
        });
    }
}
