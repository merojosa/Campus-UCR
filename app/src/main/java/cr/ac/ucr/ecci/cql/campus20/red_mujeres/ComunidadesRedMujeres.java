package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

/** Actividad para desplegar todas las comunidades que están almacenadas en la BD.
 *  Muestra la lista de comunidades a las que el usuario se puede unir
 * */

import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cr.ac.ucr.ecci.cql.campus20.R;

public class ComunidadesRedMujeres extends AppCompatActivity {

    ArrayList<Comunidad> comunidadList;     //Almacena todas la comunidades

    private RecyclerView mRecyclerView;                 //Contiene el RecyclerView definido en el Layout
    private ComunidadAdapter mAdapter;                  //Instancia del Adaptador que funciona como puente entre el RV y los datos
    private RecyclerView.LayoutManager mLayoutManager;  //Para el manejo del layout, en este caso se usa para el listview de las comunidades

    //Variable para acceder a la base de datos
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidades_red_mujeres);

        // Instancia base de datos
        mDatabase = FirebaseDatabase.getInstance();

        //Se oculta el ActionBar para "reemplezarlo" por el AppBar definido
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        //Se recibe objeto Comunidad
        Intent intent = getIntent();
        ArrayList<String> comunidades = intent.getStringArrayListExtra("comunidadesTotales");

        createComunidadList(comunidades);   //Llamado al método para llenar el la lista de comunidades
    }

    //Método que toma los datos de la BD provenientes del Intent y construye la lista de comunidades
    public void createComunidadList(List<String> comunidades)
    {
        comunidadList = new ArrayList<>(); //Array que almacena las comunidades con la información proveniente de la base de datos

        DatabaseReference ref = mDatabase.getReference("Comunidades");    //Comunidad
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null)
                {
                    for (int i = 0; i < comunidades.size(); ++i)
                    {
                        Map<String, Object> map = (HashMap<String, Object>) dataSnapshot.child(comunidades.get(i)).getValue();
                        String comunidadNombre = map.get("Nombre").toString();
                        String comunidadDescripcion = map.get("Descripcion").toString();
                        String cantidadMiembros = String.valueOf(dataSnapshot.child(comunidades.get(i)).child("IDusuarios").getChildrenCount());
                        ArrayList<String> miembros = new ArrayList<>();

                        for (DataSnapshot idUserSnapshot : dataSnapshot.child(comunidades.get(i)).child("IDusuarios").getChildren()) {
                            miembros.add(idUserSnapshot.getValue().toString());
                        }
                        //Se añade la nueva comunidad a la lista de comunidades a las que pertenece el usuario actual
                        comunidadList.add(new Comunidad(R.drawable.community, comunidadNombre, cantidadMiembros, miembros, comunidadDescripcion));
                    }
                    //Se invoca al método para desplegar en el RecyclerView los Cards donde se despliegan las comunidades y sus detalles
                    buildRecyclerView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
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
