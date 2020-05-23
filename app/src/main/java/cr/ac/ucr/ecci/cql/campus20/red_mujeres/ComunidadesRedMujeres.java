package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.R;

public class ComunidadesRedMujeres extends AppCompatActivity {

    ArrayList<Comunidad> comunidadList;

    private RecyclerView mRecyclerView;
    private ComunidadAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidades_red_mujeres);

        createComunidadList();
        buildRecyclerView();
    }

    public void createComunidadList()
    {
        comunidadList = new ArrayList<>();
        comunidadList.add(new Comunidad(R.drawable.community,
                "Comunidad 1",
                "192 miembros",
                "Descripción genérica de ejemplo creada para la Comunidad 1 del sistema"));
        comunidadList.add(new Comunidad(R.drawable.community,
                "Comunidad 2",
                "25 miembros",
                "Descripción genérica de ejemplo creada para la Comunidad 2 del sistema"));
        comunidadList.add(new Comunidad(R.drawable.community,
                "Comunidad 3",
                "3 miembros",
                "Descripción genérica de ejemplo creada para la Comunidad 3 del sistema"));
        comunidadList.add(new Comunidad(R.drawable.community,
                "Comunidad 4",
                "192 miembros",
                "Descripción genérica de ejemplo creada para la Comunidad 4 del sistema"));
    }

    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerViewCommunities);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ComunidadAdapter(comunidadList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ComunidadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(ComunidadesRedMujeres.this, ComunidadDetalle.class).putExtra("comunidad", comunidadList.get(position)));
                //Toast.makeText(getApplicationContext(), comunidadList.get(position).getCommunityName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
