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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

import cr.ac.ucr.ecci.cql.campus20.ConfiguracionActivity;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.LoginActivity;
import cr.ac.ucr.ecci.cql.campus20.LoginBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters.RVAdapterPregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.PreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;

public class ForoGeneralVerPreguntas extends AppCompatActivity implements RVAdapterPregunta.OnPreguntaListener {
    private LiveData<List<Pregunta>> preguntas;
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

        // Setear el view para las preguntas
        recyclerViewPreguntas = (RecyclerView)findViewById(R.id.verPreguntasRV);
        recyclerViewPreguntas.setLayoutManager(new LinearLayoutManager(this));
        // Si no se cambia el tamanno, hacer esto mejora el performance
        recyclerViewPreguntas.setHasFixedSize(true);

        // Setear el adaptador para las preguntas
        this.preguntasAdapter = new RVAdapterPregunta(this, preguntaCards, this);
        recyclerViewPreguntas.setAdapter(preguntasAdapter);

        // Instancia el viewModel para recuperar la lista asincrónicamente
        mPreguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        preguntas = mPreguntaViewModel.getPreguntasTema(idTemaSeleccionado);
        // Setea el titulo del tema seleccionado en la pantalla de ver preguntas
        tituloTema = (TextView) findViewById(R.id.temaSeleccionado);
        tituloTema.setText(temaSeleccionado);
        preguntas.observe(this, new Observer<List<Pregunta>>() {
            @Override
            public void onChanged(List<Pregunta> preguntas) {
                // Si la lista tiene preguntas agrega las preguntas
                if(preguntas.size() > 0){
                    preguntaCards = preguntasAdapter.convertToPreguntaCards(preguntas);
                    preguntasAdapter.setPreguntaCards(preguntas);
                }else{
                    tituloTema.setText(temaSeleccionado + ": No tiene preguntas!");
                }
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
                    case R.id.pref_foro:
                        startActivity(new Intent(ForoGeneralVerPreguntas.this, ConfiguracionActivity.class));
                        break;
                    case R.id.logout_foro:
                        LoginBD login = new FirebaseBD(getApplicationContext());
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

        startActivity(intent);
    }
}
