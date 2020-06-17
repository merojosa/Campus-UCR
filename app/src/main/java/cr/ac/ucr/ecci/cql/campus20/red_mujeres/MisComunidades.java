package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;

public class MisComunidades extends AppCompatActivity {

    ArrayList<Comunidad> comunidadList;     //Arreglo para almacenar las comunidades a las que peretenece el usuario actual del sistema

    //Objetos para el manejo del layout personalizado usando Cards en un RecyclerView con Adapter para la actualización de los diferente elementos de la interfaz
    private RecyclerView mRecyclerView;
    private ComunidadAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String usuarioID;
    private String usuarioNombre;
    FloatingActionButton fabMain;
    FloatingActionButton fabJoinCommunity;
    FloatingActionButton fabCreateCommunity;

    //Variables para el comportamiento de los botones flotantes
    Float translationY = 100f;
    Boolean isOpenMenu = false;
    OvershootInterpolator interpolator = new OvershootInterpolator();

    //Arreglos para el manejo de las comunidades que se reciben por intents
    ArrayList<String> misComunidades =  new ArrayList<>();
    ArrayList<String> comunidadesTotales = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_comunidades);  //Se asocia la actvidad con su correspondiente layout

        //Se oculta el ActionBar para "reemplezarlo" por el AppBar definido
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        //Se reciben las listas de comunidades a las que pertenece un usuario y a las que puede unirse, a través de Intent
        Intent intent = getIntent();
        misComunidades = intent.getStringArrayListExtra("misComunidades");
        comunidadesTotales = intent.getStringArrayListExtra("comunidadesTotales");
        usuarioID = intent.getStringExtra("userID");
        usuarioNombre = intent.getStringExtra("userName");

        //Se pregunta si la persona pertenece a alguna comunidad
        if(misComunidades == null || misComunidades.size()==0)
        {
            TextView textNotJoined = findViewById(R.id.text_Not_Joined);
            textNotJoined.setText("No se ha unido a ningún grupo");
        }
        else
        {
            //Se construyen los objetos de tipo Comunidad con los datos obtenidos de la BD para desplega su información
            createComunidadList(misComunidades);

            //Se invoca al método para desplegar en el RecyclerView los Cards donde se despliegan las comunidades y sus detalles
            buildRecyclerView();
        }

        initBotonesFlotantes();
    }

    //Método que toma los datos de la BD provenientes del Intent y construye la lista de comunidades
    public void createComunidadList(List<String> comunidades)
    {
        comunidadList = new ArrayList<>();

        //Ciclo que toma del arreglo con los datos de la BD para crear cada objeto Comunidad y las almacena en el arreglo de la clase
        for(int i =0; i< comunidades.size(); ++i)
        {
            comunidadList.add(new Comunidad(R.drawable.community,
                    comunidades.get(i),
                    comunidades.size() + " miembros",
                    new ArrayList<String>(),
                    "Descripción genérica de ejemplo creada para la Comunidad "+ comunidades.get(0) + " del sistema."));
        }
    }

    //Método para dibujar los cards de las comunidades a las que pertenece el usuario actual dentro del RecyclerViewer del layout
    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerViewMyCommunities);  //Instanciación del RecyclerView del layout donde se colocarán los cards
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);        //Instanciación del LayoutManager en el contexto actual
        mAdapter = new ComunidadAdapter(comunidadList);                //Instancia del adaptador construído para el manejo de los cards y su contenido

        //Asociación del LayoutManager y del adaptador para el RecyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //Manejo de click sobre un card
        mAdapter.setOnItemClickListener(new ComunidadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
                //Se llama a la actividad para mostrar el detalle de una comunidad. Se envía la lista de comuidades y una bandera para no mostrar el botón de unión y así reutilizar el adapatdor
                startActivity(new Intent(MisComunidades.this, ComunidadDetalle.class)
                        .putExtra("comunidad", comunidadList.get(position)).putExtra("vis", 0)
                        .putExtra("usuarioID", usuarioID)
                        .putExtra("usuarioName", usuarioNombre));
            }
        });
    }

    //Instanciación de los botones flotantes para unirse a otra comunidad o crear una nueva
    public void initBotonesFlotantes()
    {
        fabMain = findViewById(R.id.fabMain);
        fabJoinCommunity = findViewById(R.id.fabJoinCommunity);
        fabCreateCommunity = findViewById(R.id.fabCreateCommunity);

        fabJoinCommunity.setAlpha(0f);
        fabCreateCommunity.setAlpha(0f);

        fabJoinCommunity.setTranslationY(translationY);
        fabCreateCommunity.setTranslationY(translationY);

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(isOpenMenu)
                    closeMenu();
                else
                    openMenu();
            }
        });

        // Acción al pulsar el botón flotante para unirse a una comunidad
        fabJoinCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Llamado a la actividad para desplegar todas las comunidades existentes a las cuales el usuario actual puede unirse
                startActivity(new Intent(MisComunidades.this, ComunidadesRedMujeres.class).putStringArrayListExtra("comunidadesTotales", comunidadesTotales));
            }
        });

        // Acción al pulsar el botón flotante para crear una comunidad
        fabCreateCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
            }
        });
    }

    public void openMenu()
    {
        isOpenMenu = !isOpenMenu;
        fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(250).start();
        fabJoinCommunity.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(275).start();
        fabCreateCommunity.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(275).start();
    }

    public void closeMenu()
    {
        isOpenMenu = !isOpenMenu;
        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(250).start();
        fabJoinCommunity.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(275).start();
        fabCreateCommunity.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(275).start();
    }
}
