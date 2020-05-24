package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterViewAnimator;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.FavoritoViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.TemaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

public class ForoGeneralVerTemas extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private TemaViewModel mTemaViewModel;
    private FavoritoViewModel mFavoritoViewModel;

    /**
     * Método que se invoca al iniciar la actividad temas en el foro general,
     * muestra una lista de temas, con la imagen y una descripción de los mismos     *
     * @param savedInstanceState almacena información del estado de la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general_ver_temas);

        // Instanciación del RecyclewView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final AdaptadorTemas adapter = new AdaptadorTemas(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTemaViewModel = new ViewModelProvider(this).get(TemaViewModel.class);
        mFavoritoViewModel = new ViewModelProvider(this).get(FavoritoViewModel.class);

        // Obtiene el cambio en la lista de temas, directo desde el ViewModel
        mTemaViewModel.getAllTemas().observe(this, new Observer<List<Tema>>() {
            @Override
            public void onChanged(List<Tema> temas) {
                if (temas != null)
                    adapter.setTemas(temas);        // Se llama al método del adapter
            }
        });

        // Obtiene el cambio en la lista de favoritos, directo desde el ViewModel
        mFavoritoViewModel.getAllFavoritos().observe(this, new Observer<List<Favorito>>() {
            @Override
            public void onChanged( @Nullable final List<Favorito> favoritos) {
                adapter.setFavoritos(favoritos);    // Se llama al método del adapter
            }
        });

        // Recepción de los clicks del adapter
        adapter.setOnItemClickListener(new AdaptadorTemas.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                int idTemaSeleccionado = mTemaViewModel.getAllTemas().getValue().get(position).getId();
                String temaSeleccionado = mTemaViewModel.getAllTemas().getValue().get(position).getTitulo();
                // Llamada a la actividad de ver preguntas
                Intent intent = new Intent(getApplicationContext(), ForoGeneralVerPreguntas.class);
                intent.putExtra("idTemaSeleccionado", idTemaSeleccionado);
                intent.putExtra("temaSeleccionado", temaSeleccionado);
                startActivity(intent);
            }

            @Override
            public void onHeartClick(boolean check, int position) {

                String nombreTema = mTemaViewModel.getAllTemas().getValue().get(position).getTitulo();
                int idTema = mTemaViewModel.getAllTemas().getValue().get(position).getId();

                if (check) {
                    // Se da un mensaje al usuario
                    Toast.makeText(ForoGeneralVerTemas.this, "Tema " + nombreTema +
                            " añadido a Favoritos", Toast.LENGTH_SHORT).show();

                    // Se inserta el tema como Favorito
                    añadirTemaFavorito(idTema);
                } else {
                    // Se da un mensaje al usuario
                    Toast.makeText(ForoGeneralVerTemas.this, "Tema " + nombreTema +
                            " quitado de Favoritos", Toast.LENGTH_SHORT).show();

                    // Se elimina al tema de la lista de Favoritos
                    eliminarTemaFavorito(idTema);
                }
            }
        });


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
     * Método que se encarga de invocar el método para inserción del ViewModel para
     * la inserción de temas favoritos
     * @param identificadorTema, que es el identificador del tema que se va a insertar
     * como favorito
     */
    public void añadirTemaFavorito(int identificadorTema)
    {
        Favorito fav = new Favorito(identificadorTema);
        mFavoritoViewModel.insert(fav);
    }

    /**
     * Método que se encarga de invocar el método para borrado de un tema que está
     * añadido como favorito
     * @param identificadorTema, que es el identificador del tema que se va a eliminar
     */
    public void eliminarTemaFavorito(int identificadorTema)
    {
        mFavoritoViewModel.deleteOneFavorito(identificadorTema);
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

