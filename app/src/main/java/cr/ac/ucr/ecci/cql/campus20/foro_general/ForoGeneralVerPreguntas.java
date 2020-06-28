package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ConfiguracionActivity;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.LoginActivity;
import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters.RVAdapterPregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.PreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;

public class ForoGeneralVerPreguntas extends AppCompatActivity implements RVAdapterPregunta.OnPreguntaListener {
    private LiveData<List<Pregunta>> preguntas;
    private List<Pregunta> preguntasFireBase;
    ForoGeneralFirebaseDatabase databaseReference;
    private PreguntaViewModel mPreguntaViewModel;
    private TextView tituloTema;
    private RecyclerView recyclerViewPreguntas;
    private RVAdapterPregunta preguntasAdapter;
    private List<PreguntaCard> preguntaCards = null;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general_ver_preguntas);

        // Intent para obtener el id del tema seleccionado en la pantalla anterior
        // Ademas, el nombre del tema seleccionado en la pantalla anterior
        Intent mIntent = getIntent();
        int idTemaSeleccionado = mIntent.getIntExtra("idTemaSeleccionado", 0);
        String temaSeleccionado = mIntent.getStringExtra("temaSeleccionado");

        this.preguntasFireBase = new ArrayList<Pregunta>();

        // Se instancia el firebaseReference
        this.databaseReference = new ForoGeneralFirebaseDatabase();

        this.databaseReference.getPreguntasRef().child(String.valueOf(idTemaSeleccionado)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Se recorre el snapshot para sacar los datos
                ForoGeneralVerPreguntas.this.preguntasFireBase.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    int id = ds.child("id").getValue(Integer.class);
                    String nombreUsuario = ds.child("nombreUsuario").getValue(String.class);
                    int temaID = ds.child("temaID").getValue(Integer.class);
                    String texto = ds.child("texto").getValue(String.class);
                    int contadorLikes = ds.child("contadorLikes").getValue(Integer.class);
                    int contadorDisLikes = ds.child("contadorDisLikes").getValue(Integer.class);

                    // Se crea la pregunta
                    Pregunta pregunta = new Pregunta(id, nombreUsuario, temaID, texto, contadorLikes, contadorDisLikes);
                    ForoGeneralVerPreguntas.this.preguntasFireBase.add(pregunta);
                }

                // Setear el view para las preguntas
                recyclerViewPreguntas = (RecyclerView)findViewById(R.id.verPreguntasRV);
                recyclerViewPreguntas.setLayoutManager(new LinearLayoutManager(ForoGeneralVerPreguntas.this));
                // Si no se cambia el tamanno, hacer esto mejora el performance
                recyclerViewPreguntas.setHasFixedSize(true);

                // Setear el adaptador para las preguntas
                ForoGeneralVerPreguntas.this.preguntasAdapter = new RVAdapterPregunta(ForoGeneralVerPreguntas.this, preguntaCards, ForoGeneralVerPreguntas.this);
                recyclerViewPreguntas.setAdapter(preguntasAdapter);

                // Setea el titulo del tema seleccionado en la pantalla de ver preguntas
                tituloTema = (TextView) findViewById(R.id.temaSeleccionado);
                tituloTema.setText(temaSeleccionado);

                if(ForoGeneralVerPreguntas.this.preguntasFireBase.size() > 0){
                    preguntaCards = preguntasAdapter.convertToPreguntaCards(ForoGeneralVerPreguntas.this.preguntasFireBase);
                    preguntasAdapter.setPreguntaCards(ForoGeneralVerPreguntas.this.preguntasFireBase);
                }else{
                    tituloTema.setText(temaSeleccionado + ": No tiene preguntas!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }

        });

        // Este código debería ser una llamado y no el código en sí

        //Codigo que maneja la navegacion de izquierda a derecha
        dl = (DrawerLayout)findViewById(R.id.activity_foro_general_ver_preguntas);
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
                        startActivity(new Intent(ForoGeneralVerPreguntas.this, MainForoGeneral.class));
                        break;
                    case R.id.temas_foro:
                        startActivity(new Intent(ForoGeneralVerPreguntas.this, ForoGeneralVerTemas.class));
                        break;
                    case R.id.mis_preguntas_foro:
                        Intent intent = new Intent(ForoGeneralVerPreguntas.this, ForoGeneralVerMisPreguntas.class);
                        intent.putExtra("nombreUsuario", ForoGeneralVerPreguntas.this.databaseReference.obtenerUsuario());
                        // Llamada a la actividad de crear pregunta
                        startActivity(intent);
                        break;
                    case R.id.pref_foro:
                        startActivity(new Intent(ForoGeneralVerPreguntas.this, ConfiguracionActivity.class));
                        break;
                    case R.id.logout_foro:
                        CampusBD login = new FirebaseBD();
                        login.cerrarSesion();

                        ActivityCompat.finishAffinity(ForoGeneralVerPreguntas.this);
                        startActivity(new Intent(ForoGeneralVerPreguntas.this, LoginActivity.class));
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });

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
     * Metodo que agrega el listener de click en las preguntas para enviar a la actividad de ver respuestas
     * @param position
     */
    @Override
    public void onPreguntaClick(int position) {
        PreguntaCard preguntaSeleccionada = preguntaCards.get(position);

        Intent intent = new Intent(getApplicationContext(), ForoGeneralVerRespuestas.class);
        intent.putExtra("preguntaSeleccionada", preguntaSeleccionada);
        intent.putExtra("nombreUsuario", this.databaseReference.obtenerUsuario());
        startActivity(intent);
    }
}
