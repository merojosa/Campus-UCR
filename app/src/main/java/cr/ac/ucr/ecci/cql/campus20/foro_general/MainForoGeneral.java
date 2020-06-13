package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cr.ac.ucr.ecci.cql.campus20.ConfiguracionActivity;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.LoginActivity;
import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters.TemasFavoritosAdapter;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.TemaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.FavoritoViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.TemaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

public class MainForoGeneral extends AppCompatActivity {

    // Se define una futura instancia de los ViewModel
    private FavoritoViewModel mFavoritoViewModel;
    private TemaViewModel mTemaViewModel;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private List<Integer> idList;

    // Instancia de Firebase para el foro general y sus hijos
    ForoGeneralFirebaseDatabase databaseReference;
    DatabaseReference temasDatabaseReference;
    List<Tema> temasLocales;
    List<Favorito> favoritosLocales;

    int onlyOnce = 0;

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

        // Se instancia el firebaseReference
        databaseReference = new ForoGeneralFirebaseDatabase();

        idList = new ArrayList<>();
        temasLocales = new ArrayList<>();
        favoritosLocales = new ArrayList<>();

        // Se instancia el RecyclerView
        RecyclerView recyclerView = findViewById(R.id.listaTemasFavoritos);
        final TemasFavoritosAdapter adapter = new TemasFavoritosAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFavoritoViewModel = new ViewModelProvider(this).get(FavoritoViewModel.class);
        mTemaViewModel = new ViewModelProvider(this).get(TemaViewModel.class);

        this.databaseReference.getTemasRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Se borra la lista
                MainForoGeneral.this.temasLocales.clear();
                // Se recorre el snapshot para sacar los datos
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    // Esto podría producir NullPointerException
                    int id = ds.child("id").getValue(Integer.class);
                    String titulo = ds.child("titulo").getValue(String.class);
                    String description = ds.child("description").getValue(String.class);
                    int contUsers = ds.child("contadorUsuarios").getValue(Integer.class);
                    int imagen = ds.child("imagen").getValue(Integer.class);

                    // Se crea el tema
                    Tema temita = new Tema(id, titulo, description, contUsers, imagen);
                    MainForoGeneral.this.temasLocales.add(temita);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }
        });

        adapter.setTemas(this.temasLocales);

        // Prueba con el Favorito -> usuario
        this.databaseReference.getFavoritosRef().child(this.databaseReference.obtenerUsuario())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Se borra la lista
                        MainForoGeneral.this.favoritosLocales.clear();

                        // Chequea si existe el snapshot y si el usuario tiene favoritos almacenados en Firebase
                        if (dataSnapshot.exists()) //&& dataSnapshot.hasChild(ForoGeneralVerTemas.this.databaseReference.obtenerUsuario()))
                        {
                            // Se recorre el snapshot para sacar los datos
                            for (DataSnapshot ds : dataSnapshot.getChildren())
                            {
                                // Esto podría producir NullPointerException
                                int id = ds.child("idTema").getValue(Integer.class);
                                String nombreUsuario = ds.child("nombreUsuario").getValue(String.class);

                                // Se crea el tema
                                Favorito fav = new Favorito(id, nombreUsuario);
                                MainForoGeneral.this.favoritosLocales.add(fav);
                            }
                            adapter.setFavoritos(MainForoGeneral.this.favoritosLocales);

                            int count = MainForoGeneral.this.favoritosLocales.size();
                            for (int i = 0; i< count; i++){
                                idList.add(i, MainForoGeneral.this.favoritosLocales.get(i).getIdTema());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Failed to read value
                        Log.w("FIREBASE", "Fallo al leer favoritos", databaseError.toException());
                    }
                });

//        try
//        {
//            Thread.sleep(100);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }

        // Obtiene el cambio en la lista de temas, directo desde el ViewModel
        mTemaViewModel.getAllTemas().observe(this, new Observer<List<Tema>>() {
            @Override
            public void onChanged(List<Tema> temas) {
                adapter.setTemas(MainForoGeneral.this.temasLocales);
                //adapter.setTemas(temas);
                //llenarTemasFirebase(temas);
            }
        });

        // Obtiene el cambio en la lista de favoritos, directo desde el ViewModel
        mFavoritoViewModel.getAllFavoritos().observe(this, new Observer<List<Favorito>>() {
            @Override
            public void onChanged(@Nullable final List<Favorito> favoritos) {

                if (MainForoGeneral.this.temasLocales != null)
                {
                    // Update the cached copy of the words in the adapter.
                    //adapter.setFavoritos(favoritos);

//                    //para considerar cambios
//                    int count = favoritos.size();
//                    for (int i = 0; i< count; i++){
//                        idList.add(i, favoritos.get(i).getIdTema());
//                    }
                    Log.d("FIREBASE", "Entró al viejo adapter");
                }

            }
        });


        // Recepción de los clicks del adapter
        adapter.setOnItemClickListener(new TemasFavoritosAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {


                //conseguir id del tema seleccionado
                int idTemaSeleccionado;
                String temaSeleccionado;
                if(idList.size() != 0){
                    idTemaSeleccionado = idList.get(position);
                }
                else{
                    idTemaSeleccionado = mTemaViewModel.getAllTemas().getValue().get(position).getId();
                }
                int counter = mTemaViewModel.getAllTemas().getValue().size();
                int i = 0 ;
                int fin = 0;
                Tema result = new Tema(0 , "", "", 0,0); //tema comodin
                while (i < counter && fin ==0) {
                    if (mTemaViewModel.getAllTemas().getValue().get(i).id == idTemaSeleccionado) {
                        result = mTemaViewModel.getAllTemas().getValue().get(i);
                        fin = 1;
                    }
                    i++;
                }
                temaSeleccionado = result.getTitulo();

                //int idTemaSeleccionado = (mFavoritoViewModel.getAllFavoritos().getValue().get(position).getIdTema());
                //String temaSeleccionado = mTemaViewModel.getAllTemas().getValue().get(position).getTitulo();
                // Llamada a la actividad de ver preguntas
                Intent intent = new Intent(getApplicationContext(), ForoGeneralVerPreguntas.class);
                intent.putExtra("idTemaSeleccionado", idTemaSeleccionado);
                intent.putExtra("temaSeleccionado", temaSeleccionado);
                startActivity(intent);
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

        //Codigo que maneja la navegacion de izquierda a derecha
        dl = (DrawerLayout)findViewById(R.id.activity_main_foro_general);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

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
                        startActivity(new Intent(MainForoGeneral.this, MainForoGeneral.class));
                        break;
                    case R.id.temas_foro:
                        startActivity(new Intent(MainForoGeneral.this, ForoGeneralVerTemas.class));
                        break;
                    case R.id.pref_foro:
                        startActivity(new Intent(MainForoGeneral.this, ConfiguracionActivity.class));
                        break;
                    case R.id.logout_foro:
                        CampusBD login = new FirebaseBD();
                        login.cerrarSesion();

                        ActivityCompat.finishAffinity(MainForoGeneral.this);
                        startActivity(new Intent(MainForoGeneral.this, LoginActivity.class));
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
