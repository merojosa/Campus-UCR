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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.ConfiguracionActivity;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.LoginActivity;
import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters.RVAdapterRespuesta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.RespuestaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

public class ForoGeneralVerRespuestas extends AppCompatActivity {

    LiveData<List<Respuesta>> respuestas;
    RespuestaViewModel mRespuestaViewModel;
    private TextView tituloPregunta;
    private RecyclerView recyclerViewRespuestas;
    private RVAdapterRespuesta adapterRespuesta;
    private List<Respuesta> listaRespuestas;
    FloatingActionButton buttonAgregarRespuestas;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    ForoGeneralFirebaseDatabase databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general_ver_respuestas);
        Intent mIntent = getIntent();

        // Se instancia el firebaseReference
        this.databaseReference = new ForoGeneralFirebaseDatabase();

        // Boton flotante de Agregar Respuestas
        buttonAgregarRespuestas = findViewById(R.id.buttonAgregarRespuestas);

        PreguntaCard preguntaSeleccionada = mIntent.getParcelableExtra("preguntaSeleccionada");

        int idPreguntaSeleccionada = preguntaSeleccionada.getId();
        int idTemaSeleccionado = preguntaSeleccionada.getTemaID();

        //sacar los elementos de la vista desde firebase
        //pasarlos a respuestas



        recyclerViewRespuestas = (RecyclerView) findViewById(R.id.verRespuestasRV);

        recyclerViewRespuestas.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewRespuestas.setHasFixedSize(true);

        adapterRespuesta = new RVAdapterRespuesta(this, listaRespuestas);
        recyclerViewRespuestas.setAdapter(adapterRespuesta);

        mRespuestaViewModel = new ViewModelProvider(this).get(RespuestaViewModel.class);

        //respuestas = mRespuestaViewModel.getRespuestasDePregunta(idPreguntaSeleccionada);
        respuestas = mRespuestaViewModel.getRespuestasDePreguntaYTema(idPreguntaSeleccionada, idTemaSeleccionado);

        tituloPregunta = (TextView) findViewById(R.id.preguntaSeleccionada);
        tituloPregunta.setText(preguntaSeleccionada.getTexto());

        respuestas.observe(this, new Observer<List<Respuesta>>() {
            @Override
            public void onChanged(List<Respuesta> respuestas) {
                if (respuestas.size() > 0) {
                    adapterRespuesta.setRespuestas(respuestas);
                } else {
                    tituloPregunta.setText(preguntaSeleccionada.getTexto() + ": " + "No hay respuestas aun.");
                }
            }
        });

        // Asocia evento clic al boton
        buttonAgregarRespuestas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearRespuesta(preguntaSeleccionada);
            }
        });

        //Codigo que maneja la navegacion de izquierda a derecha
        dl = (DrawerLayout)findViewById(R.id.activity_foro_general_ver_respuestas);
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
                        startActivity(new Intent(ForoGeneralVerRespuestas.this, MainForoGeneral.class));
                        break;
                    case R.id.temas_foro:
                        startActivity(new Intent(ForoGeneralVerRespuestas.this, ForoGeneralVerTemas.class));
                        break;
                    case R.id.pref_foro:
                        startActivity(new Intent(ForoGeneralVerRespuestas.this, ConfiguracionActivity.class));
                        break;
                    case R.id.logout_foro:
                        CampusBD login = new FirebaseBD();
                        login.cerrarSesion();

                        ActivityCompat.finishAffinity(ForoGeneralVerRespuestas.this);
                        startActivity(new Intent(ForoGeneralVerRespuestas.this, LoginActivity.class));
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });
    }

    /**
     * Este metodo dirige a la actividad de crear respuesta
     *
     * @param pregunta indica la pregunta que será enviada mediante el intent para poder usarla
     * para crear la respuesta asociada a esta pregunta
     */
    private void crearRespuesta(PreguntaCard pregunta) {
        Intent intent = new Intent(this, CrearRespuestaForoGeneral.class);
        intent.putExtra("preguntaSeleccionada", pregunta);
        intent.putExtra("nombreUsuario", this.databaseReference.obtenerUsuario());

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
