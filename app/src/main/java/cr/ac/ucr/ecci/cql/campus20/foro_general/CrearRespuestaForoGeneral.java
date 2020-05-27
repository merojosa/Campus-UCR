package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import cr.ac.ucr.ecci.cql.campus20.ConfiguracionActivity;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.LoginActivity;
import cr.ac.ucr.ecci.cql.campus20.LoginBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.RespuestaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

//Crea respuesta a partir de la pregunta que se seleccionó del lista de preguntas.
public class CrearRespuestaForoGeneral extends AppCompatActivity {

    private EditText mEditText;
    private TextView titulo;
    private Button btnCrearRespuesta;
    private PreguntaCard pregunta;
    private RespuestaViewModel mRespuestaViewModel;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_respuesta_foro_general);

        Intent mIntent = getIntent();
        pregunta = mIntent.getParcelableExtra("preguntaSeleccionada");

        mRespuestaViewModel = new ViewModelProvider(this).get(RespuestaViewModel.class);

        // Codigo para agregar borde al espacio para rellenar la pregunta
        mEditText = (EditText) findViewById(R.id.textoCrearRespuesta);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#00ffffff"));
        gd.setStroke(2, Color.parseColor("#00C0F3"));
        mEditText.setBackground(gd);

        // Codigo para manejar color del boton y evento de click
        btnCrearRespuesta = (Button) findViewById(R.id.btnCrearRespuesta);
        btnCrearRespuesta.setBackgroundColor(Color.parseColor("#00C0F3"));
        btnCrearRespuesta.setTextColor(Color.BLACK);
        btnCrearRespuesta.setText("Crear Respuesta");
        btnCrearRespuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPregunta()) {
                    agregarPregunta();
                }
            }
        });

        //Codigo que maneja la navegacion de izquierda a derecha
        dl = (DrawerLayout)findViewById(R.id.activity_main_crear_respuesta);
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
                        startActivity(new Intent(CrearRespuestaForoGeneral.this, MainForoGeneral.class));
                        break;
                    case R.id.temas_foro:
                        startActivity(new Intent(CrearRespuestaForoGeneral.this, ForoGeneralVerTemas.class));
                        break;
                    case R.id.pref_foro:
                        startActivity(new Intent(CrearRespuestaForoGeneral.this, ConfiguracionActivity.class));
                        break;
                    case R.id.logout_foro:
                        LoginBD login = new FirebaseBD();
                        login.cerrarSesion();

                        ActivityCompat.finishAffinity(CrearRespuestaForoGeneral.this);
                        startActivity(new Intent(CrearRespuestaForoGeneral.this, LoginActivity.class));
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });

    }

    /**
     * Este método inserta una nueva pregunta partir de lo que se digite, luego de insertar en
     * la base de datos se devuelve a la vista de respuesta para dicha pregunta
     */
    private void agregarPregunta() {
        String textoRespuesta = mEditText.getText().toString();
        int idPregunta = pregunta.getId();

        Respuesta respuesta = new Respuesta(0, textoRespuesta, idPregunta, 0, 0);
        mRespuestaViewModel.insert(respuesta);
        //Luego de insertar volver a vista previa
        Intent intent = new Intent(this, ForoGeneralVerRespuestas.class);
        intent.putExtra("preguntaSeleccionada", pregunta);
        startActivity(intent);
    }

    private boolean verificarPregunta() {
        if (mEditText.getText().toString().equals("")) {
            Toast.makeText(CrearRespuestaForoGeneral.this, "Por favor digite su respuesta", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
