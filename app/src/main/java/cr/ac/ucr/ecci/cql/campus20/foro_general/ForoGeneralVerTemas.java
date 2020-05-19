package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import androidx.drawerlayout.widget.DrawerLayout;
import cr.ac.ucr.ecci.cql.campus20.R;

public class ForoGeneralVerTemas extends AppCompatActivity {
    // definimos la lista de datos
    private ListView lvItems;
    private AdaptadorTemas adaptadorTemas;
    //private List<Temas> mTemas;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    /**
     * Método que se invoca al iniciar la actividad temas en el foro general,
     * muestra una lista de temas, con la imagen y una descripción de los mismos     *
     * @param savedInstanceState almacena información del estado de la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general_ver_temas);

        //se establece la vista de la lista de temas
        lvItems = findViewById(R.id.lvItems);
        adaptadorTemas = new AdaptadorTemas(this, getArrayItems());
        lvItems.setAdapter(adaptadorTemas);

        /*
        // ListView Item Click Listener para las llamadas a las opciones de los items
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;
                // ListView Clicked item value
                Temas item = (Temas)lvItems.getItemAtPosition(position);
                // Show Alert
                Toast.makeText(getApplicationContext(), "Position: " + itemPosition +
                        "  ListItem: " + item.toString(), Toast.LENGTH_LONG).show();
            }
        });
        */


/*
        // Inicialización de la barra de navegación general
        BottomNavigationView generalNavigationView = findViewById(R.id.bottom_navigation);

        // Dejar por defecto el ícono de foro seleccionado
        generalNavigationView.setSelectedItemId(R.id.foro);

        // Ejecución del listener para cambiar de actividad
        generalNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // Dependiendo del item seleccionado, ejecutará el Intent del módulo específico
                switch(menuItem.getItemId()){
                    case R.id.ucreats:
                        break;
                    case R.id.mujeres:
                        break;
                    case R.id.foro:
                        Intent intentForo = new Intent(ForoGeneralVerTemas.this, MainForoGeneral.class);
                        startActivity(intentForo);
                        break;
                    case R.id.lugares:
                        break;
                }
                return false;
            }
        });*/

        //Codigo que maneja la navegacion de izquierda a derecha
        dl = (DrawerLayout)findViewById(R.id.activity_main_foro_general_ver_temas);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Se lanza cada actividad, dependiendo de la selección del usuario
        nv = (NavigationView)findViewById(R.id.nv_foro);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home_foro:
                        Intent intentForo = new Intent(ForoGeneralVerTemas.this, MainForoGeneral.class);
                        startActivity(intentForo);
                        break;
                    case R.id.temas_foro:
                        Intent intent2Foro = new Intent(ForoGeneralVerTemas.this, ForoGeneralVerTemas.class);
                        startActivity(intent2Foro);
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });

    }

    /**
     * Método que retorna los Temas para mostrar en la actividad
     * @return un Array con todos los Temas para mostrar en la actividad y visualizarlos todos en una lista
     */
    private ArrayList<Temas> getArrayItems(){
        ArrayList<Temas> mTemas = new ArrayList<>();

        mTemas.add(new Temas("General/Noticias", R.drawable.foro1, "Lo más nuevo"));
        mTemas.add(new Temas("Escuelas", R.drawable.foro_escuelas, "Información sobre distintas escuelas"));
        mTemas.add(new Temas("Profesores", R.drawable.foro_profesores, "Información sobre distintos profesores"));
        mTemas.add(new Temas("Becas", R.drawable.foro_becas, "Desde como aplicar hasta como gastar"));
        mTemas.add(new Temas("Residencias", R.drawable.foro_residencias, "Más barato que alquilar ..."));
        mTemas.add(new Temas("Buses", R.drawable.foro_buses, "Todo sobre el interno, algo sobre los externos"));
        mTemas.add(new Temas("Mejores calificadas", R.drawable.foro_calificado, "Las preguntas más valoradas por la comunidad"));

        return mTemas;
    }

    /**
     * Método que retorna los Temas Sugeridos para mostrar en la actividad principal
     * @return un array de Temas Sugeridos para mostrar en la actividad principal
     */
    public ArrayList<Temas> getTemasSugeridos(){
        ArrayList<Temas> mTemas = new ArrayList<>();

        mTemas.add(new Temas("General/Noticias", R.drawable.foro1, "Lo más nuevo"));
        mTemas.add(new Temas("Escuelas", R.drawable.foro_escuelas, "Información sobre distintas escuelas"));
        mTemas.add(new Temas("Profesores", R.drawable.foro_profesores, "Información sobre distintos profesores"));

        return mTemas;
    }

    /**
     * Este método realiza una actividad cuando un objeto específico de la lista es seleccionado
     * @param item funciona para indicar el objeto de la lista que se selecionó
     * @return un booleano, ya que aún no se ha implementado el llamado a la base de datos
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
