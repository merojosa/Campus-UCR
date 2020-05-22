package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters.TemasFavoritosAdapter;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.TemaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.FavoritoViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.TemaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

public class MainForoGeneral extends AppCompatActivity {

    // Se define una futura instancia del FavoritoViewModel
    private FavoritoViewModel mFavoritoViewModel;

    private TemaViewModel mTemaViewModel;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    /**
     * Método que se invoca al iniciar la actividad general del módulo Foro General,
     * muestra una pequeña lista de temas sugeridos y un botón flotante para agregar una pregunta (pantalla en blanco)
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_foro_general);

        RecyclerView recyclerView = findViewById(R.id.listaTemasFavoritos);
        final TemasFavoritosAdapter adapter = new TemasFavoritosAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFavoritoViewModel = new ViewModelProvider(this).get(FavoritoViewModel.class);

        mTemaViewModel = new ViewModelProvider(this).get(TemaViewModel.class);

        mTemaViewModel.getAllTemas().observe(this, new Observer<List<Tema>>() {
            @Override
            public void onChanged(List<Tema> temas) {
                adapter.setTemas(temas);
            }
        });

        mFavoritoViewModel.getAllFavoritos().observe(this, new Observer<List<Favorito>>() {
            @Override
            public void onChanged(@Nullable final List<Favorito> favoritos) {

                // Update the cached copy of the words in the adapter.
                adapter.setFavoritos(favoritos);
            }
        });


        // MÉTODO O FORMA DE OBTENER EL CLIC DE LA LISTA DE RECYCLEWVIEW
        adapter.setOnItemClickListener(new TemasFavoritosAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                // Aquí va el intent hacia la actividad que muestra las preguntas
                String name = Integer.toString(mFavoritoViewModel.getAllFavoritos().getValue().get(position).getIdTema());
                Toast.makeText(MainForoGeneral.this, "Tema favorito con id " + name + " fue seleccionado!", Toast.LENGTH_SHORT).show();
            }

        });

        // Boton flotante de Agregar Preguntas
        FloatingActionButton buttonAgregarPreguntas = findViewById(R.id.buttonAgregarPreguntas);

        // Asocia evento clic al boton
        buttonAgregarPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearPregunta();
            }
        });


        // Llenado de la lista de temas sugeridos
        //rellenarTemasSugeridos();

        // Este código debería ser una llamado y no el código en sí

        //Codigo que maneja la navegacion de izquierda a derecha
        dl = (DrawerLayout)findViewById(R.id.activity_main_foro_general);
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
                        Intent intentForo = new Intent(MainForoGeneral.this, MainForoGeneral.class);
                        startActivity(intentForo);
                        break;
                    case R.id.temas_foro:
                        Intent intent2Foro = new Intent(MainForoGeneral.this, ForoGeneralVerTemas.class);
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
     * Método que crea un Intent para ir a la actividad de CrearPregunta
     */
    private void crearPregunta() {
        Intent intent = new Intent(this, CrearPreguntaForoGeneral.class);
        // Llamada a la actividad de crear pregunta
        startActivity(intent);
    }


    /**
     * Método que realiza un llenado de la lista de temas sugeridos
     */
    private void rellenarTemasSugeridos(){
        // Codigo que realiza el llenado de la lista de temas recomendados
        ListView listaTemasRecomendados = findViewById(R.id.listaTemasFavoritos);
        ForoGeneralVerTemas temas = new ForoGeneralVerTemas();
        AdaptadorTemas adaptadorTemas = new AdaptadorTemas(this);
        //listaTemasRecomendados.setAdapter(adaptadorTemas);
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
