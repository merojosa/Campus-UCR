package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

/** Actividad para desplegar todas las comunidades que están almacenadas en la BD.
 *  Muestra la lista de comunidades a las que el usuario se puede unir
 * */

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

    ArrayList<Comunidad> comunidadList;     //Almacena todas la comunidades

    private RecyclerView mRecyclerView;                 //Contiene el RecyclerView definido en el Layout
    private ComunidadAdapter mAdapter;                  //Instancia del Adaptador que funciona como puente entre el RV y los datos
    private RecyclerView.LayoutManager mLayoutManager;  //Para el manejo del layout, en este caso se usa para el listview de las comunidades

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidades_red_mujeres);

        //Se oculta el ActionBar para "reemplezarlo" por el AppBar definido
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        //Se recibe objeto Comunidad
        Intent intent = getIntent();
        ArrayList<String> comunidades = intent.getStringArrayListExtra("comunidadesTotales");

        createComunidadList(comunidades);   //Llamado al método para llenar el la lista de comunidades
        buildRecyclerView();                //Llamado al método para desplegar en el RecyclerView los Cards donde se despliegan las comunidades

    }

    //Método que toma los datos de la BD provenientes del Intent y construye la lista de comunidades
    public void createComunidadList(List<String> comunidades)
    {
        comunidadList = new ArrayList<>();

        //Se toman los datos provenientes de la BD y se almacenan como objetos de tipo Comunidad
        for(int i =0; i< comunidades.size(); ++i)
        {
            comunidadList.add(new Comunidad(R.drawable.community,
                    comunidades.get(i),
                    comunidades.size() + " miembros",
                    new ArrayList<String>(),
                    "Descripción genérica de ejemplo creada para la Comunidad "+ comunidades.get(0) + " del sistema"));
        }
    }

    //Método para dibujar los cards de las comunidades a las que pertenece el usuario actual dentro del RecyclerViewer del layout
    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerViewCommunities); //Instanciación del RecyclerView del layout donde se colocarán los cards
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);      //Instanciación del LayoutManager en el contexto actual
        mAdapter = new ComunidadAdapter(comunidadList);             //Instancia del adaptador construído para el manejo de los cards y su contenido

        //Asociación del LayoutManager y del adaptador para el RecyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //Determina la acción por ejecutar cuando se pulsa un card
        mAdapter.setOnItemClickListener(new ComunidadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Llamado a la actividad que permite ver con detalle la comunidad a la que el usuario pretende unirse
                startActivity(new Intent(ComunidadesRedMujeres.this, ComunidadDetalle.class).putExtra("comunidad", comunidadList.get(position)));
            }
        });
    }
}
