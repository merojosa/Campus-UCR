package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ConfiguracionActivity;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.LoginActivity;
import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters.TemasFavoritosAdapter;
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
    private ToggleButton boton;

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
        //final AdaptadorTemas adaptadorTemas = new AdaptadorTemas(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFavoritoViewModel = new ViewModelProvider(this).get(FavoritoViewModel.class);
        mTemaViewModel = new ViewModelProvider(this).get(TemaViewModel.class);


//        mTemaViewModel.getAllTemas().observe(this, new Observer<List<Tema>>() {
//            @Override
//            public void onChanged(List<Tema> temas) {
//                adapter.setTemas(MainForoGeneral.this.temasLocales);
//                //adapter.setTemas(temas);
//                //llenarTemasFirebase(temas);
//            }
//        });

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

//        // Obtiene el cambio en la lista de temas, directo desde el ViewModel
//        mTemaViewModel.getAllTemas().observe(this, new Observer<List<Tema>>() {
//            @Override
//            public void onChanged(List<Tema> temas) {
//                adapter.setTemas(MainForoGeneral.this.temasLocales);
//                //adapter.setTemas(temas);
//                //llenarTemasFirebase(temas);
//            }
//        });

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
                    idTemaSeleccionado = MainForoGeneral.this.temasLocales.get(position).getId();
                }
                int counter = MainForoGeneral.this.temasLocales.size();
                int i = 0 ;
                int fin = 0;
                Tema result = new Tema(0 , "", "", 0,0); //tema comodin
                while (i < counter && fin ==0) {
                    if (MainForoGeneral.this.temasLocales.get(i).id == idTemaSeleccionado) {
                        result = MainForoGeneral.this.temasLocales.get(i);
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
                //intent.putExtra("nombreUsuario", firebase.getNombreUsuario());
                startActivity(intent);
            }

//            ID: CI0161-356 - Remover tema favorito desde la pantalla de inicio.
//            Tarea realizada:
//                    * Se despliega un mensaje de confirmación a la hora de eliminar uno de los temas de la lista de favoritos.
//            Participantes:
//                    * Josué Zeledón: Developer o Driver
//                    * Esteban González: Navigator
//                    * Andrés Davidovich: Navigator
            @Override
            public void onHeartClick(boolean check, int position){

                //conseguir id del tema seleccionado
                int idTema;
                String nombreTema;
                if(idList.size() != 0){
                    idTema = idList.get(position);
                }
                else{
                    idTema = MainForoGeneral.this.temasLocales.get(position).getId();
                }
                int counter = MainForoGeneral.this.temasLocales.size();
                int i = 0 ;
                int fin = 0;
                Tema result = new Tema(0 , "", "", 0,0); //tema comodin
                while (i < counter && fin ==0) {
                    if (MainForoGeneral.this.temasLocales.get(i).id == idTema) {
                        result = MainForoGeneral.this.temasLocales.get(i);
                        fin = 1;
                    }
                    i++;
                }
                nombreTema = result.getTitulo();

                if (check) {
                    // Se da un mensaje al usuario
//                    Toast.makeText(ForoGeneralVerTemas.this, "Tema " + nombreTema +
//                            " añadido a Favoritos", Toast.LENGTH_SH

                } else {

                    mensajeConfirmacionEliminar(idTema, nombreTema);

                }
            }
//
//            @Override
//            public void onLongClick(View view, int position){
//                Toast.makeText(MainForoGeneral.this, "HIII", Toast.LENGTH_SHORT).show();
//            }
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
                    case R.id.mis_preguntas_foro:
                        Intent intent = new Intent(MainForoGeneral.this, ForoGeneralVerMisPreguntas.class);
                        intent.putExtra("nombreUsuario", MainForoGeneral.this.databaseReference.obtenerUsuario());
                        // Llamada a la actividad de crear pregunta
                        startActivity(intent);
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
        intent.putExtra("nombreUsuario", this.databaseReference.obtenerUsuario());
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


    /**
     * Método que se encarga de crear el mensaje de confirmación a la hora de eliminar
     * un tema de la lista de favoritos
     * @param idTema que es el id del tema favorito a eliminar de la lista
     * @param nombreTema que es el nombre del tema que se desea eliminar
     */
    public void mensajeConfirmacionEliminar(int idTema, String nombreTema)
    {
        // Se genera el dialog para la confirmación del borrado
        AlertDialog.Builder builder = new AlertDialog.Builder(MainForoGeneral.this);
        builder.setTitle("Eliminar Tema");

        // Se crea la pregunta final
        builder.setMessage("¿Desea eliminar el tema " + nombreTema + " de la lista de Favoritos?");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se elimina al tema de la lista de Favoritos
                eliminarTemaFavorito(idTema, MainForoGeneral.this.databaseReference.obtenerUsuario());

                // Se elimina el tema como Favorito en Firebase
                eliminarTemaFavoritoFirebase(idTema, MainForoGeneral.this.databaseReference.obtenerUsuario());

                // Se da un mensaje al usuario
                Toast.makeText(MainForoGeneral.this, "Tema " + nombreTema +
                        " quitado de Favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No sucede nada
            }
        });

        AlertDialog dialog = builder.create();
        // Desplega el alert en la pantalla
        dialog.show();
    }


    /**
     * Método que se encarga de invocar el método para borrado de un tema que está
     * añadido como favorito
     * @param identificadorTema, que es el identificador del tema que se va a eliminar
     * @param nombreUsuario, que es el nombre del usuario actualmente loggeado
     */
    public void eliminarTemaFavorito(int identificadorTema, String nombreUsuario)
    {
        mFavoritoViewModel.deleteOneFavorito(identificadorTema, nombreUsuario);
    }

    /**
     * Método que se encarga de eliminar el tema de la lista de favoritos del usuario en firebase
     * @param identificadorTema, que es el identificador del tema que se va a eliminar
     * @param nombreUsuario, que es el nombre del usuario actualmente loggeado
     */
    public void eliminarTemaFavoritoFirebase(int identificadorTema, String nombreUsuario)
    {
        this.databaseReference.getFavoritosRef().child(nombreUsuario).child(Integer.toString(identificadorTema)).setValue(null);
    }


//    public void llenarTemasFirebase(List<Tema> temas)
//    {
//        // FORMA INEFICIENTE DE INSERTAR EN FIREBASE
//        if (onlyOnce == 0)
//        {
//            for (int index = 0; index < temas.size(); ++index)
//            {
//                this.databaseReference.getTemasRef().child(Integer.toString(temas.get(index).getId() - 1)).setValue(temas.get(index));
//            }
//        }
//
//        if (temas.size() != 0)
//            onlyOnce = 1;
//
//    }

}
