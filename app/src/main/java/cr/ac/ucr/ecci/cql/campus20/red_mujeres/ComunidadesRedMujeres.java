package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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

        //Se recibe objeto Comunidad
        Intent intent = getIntent();
        ArrayList<String> comunidades = intent.getStringArrayListExtra("comunidadesTotales");
        createComunidadList(comunidades);
        buildRecyclerView();
    }

    public void createComunidadList(List<String> comunidades)
    {
        comunidadList = new ArrayList<>();

        for(int i =0; i< comunidades.size(); ++i)
        {
//            Toast.makeText(getApplicationContext(), "Comunidades[" + i +"]"+ comunidades.get(i), Toast.LENGTH_SHORT).show();
            comunidadList.add(new Comunidad(R.drawable.community,
                    comunidades.get(i),
                    comunidades.size() + " miembros",
                    new ArrayList<String>(),
                    "Descripción genérica de ejemplo creada para la Comunidad "+ comunidades.get(0) + " del sistema"));
        }
    }

    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerViewCommunities);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ComunidadAdapter(comunidadList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //Button buttonJoinCommunity = mRecyclerView.findViewById(R.id.button_Join_Community);

        mAdapter.setOnItemClickListener(new ComunidadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(ComunidadesRedMujeres.this, ComunidadDetalle.class).putExtra("comunidad", comunidadList.get(position)));
            }
        });
    }
}