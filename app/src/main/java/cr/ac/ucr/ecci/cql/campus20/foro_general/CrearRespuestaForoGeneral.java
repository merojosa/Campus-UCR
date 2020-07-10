package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
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

import android.util.Log;

import com.google.android.material.navigation.NavigationView;

import cr.ac.ucr.ecci.cql.campus20.ConfiguracionActivity;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.LoginActivity;
import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.RespuestaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//Crea respuesta a partir de la pregunta que se seleccionó del lista de preguntas.
public class CrearRespuestaForoGeneral extends AppCompatActivity {

    private EditText mEditText;
    private TextView titulo;
    private Button btnCrearRespuesta;
    private PreguntaCard pregunta;
    private RespuestaViewModel mRespuestaViewModel;
    private Button adjuntarMapa;


    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private ArrayList<Double> coordenadas;
    private Double lat;
    private Double lon;
    private boolean mapaAgregado;

    private String nombreUsuario;
    ForoGeneralFirebaseDatabase databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_respuesta_foro_general);

        //Recuperar pregunta a la que se le va a crear respuesta
        Intent mIntent = getIntent();
        pregunta = mIntent.getParcelableExtra("preguntaSeleccionada");

        // Nombre del usuario actual
        this.nombreUsuario = mIntent.getStringExtra("nombreUsuario");

        mRespuestaViewModel = new ViewModelProvider(this).get(RespuestaViewModel.class);

        //Si esta en el defaultValue es porque no viene de la actividad de agregar mapa.
        mapaAgregado = mIntent.getBooleanExtra("mapaAgregado", false);

        //Si se agregó al mapa entonces se agregan valores a objeto
        if (mapaAgregado == true) {
            lat = mIntent.getDoubleExtra("latitud", 0.0);
            lon = mIntent.getDoubleExtra("longitud", 0.0);
        }

        // Codigo para manejar color del boton y evento de click
        adjuntarMapa = (Button) findViewById(R.id.adjuntarMapa);
        adjuntarMapa.setBackgroundColor(Color.parseColor("#005DA4"));
        adjuntarMapa.setTextColor(Color.BLACK);
        adjuntarMapa.setText("Adjuntar un mapa");
        adjuntarMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //coordenadas = agregarMapa();
                agregarMapa();
            }
        });


        // Se instancia el firebaseReference
        databaseReference = new ForoGeneralFirebaseDatabase();

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
                if (verificarRespuesta()) {
                    agregarRespuesta();
                }
            }
        });

        //Codigo que maneja la navegacion de izquierda a derecha
        dl = (DrawerLayout) findViewById(R.id.activity_main_crear_respuesta);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Se lanza cada actividad, dependiendo de la selección del usuario
        nv = (NavigationView) findViewById(R.id.nv_foro);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home_foro:
                        startActivity(new Intent(CrearRespuestaForoGeneral.this, MainForoGeneral.class));
                        break;
                    case R.id.temas_foro:
                        startActivity(new Intent(CrearRespuestaForoGeneral.this, ForoGeneralVerTemas.class));
                        break;
                    case R.id.mis_preguntas_foro:
                        Intent intent = new Intent(CrearRespuestaForoGeneral.this, ForoGeneralVerMisPreguntas.class);
                        intent.putExtra("nombreUsuario", CrearRespuestaForoGeneral.this.nombreUsuario);
                        // Llamada a la actividad de crear pregunta
                        startActivity(intent);
                        break;
                    case R.id.pref_foro:
                        startActivity(new Intent(CrearRespuestaForoGeneral.this, ConfiguracionActivity.class));
                        break;
                    case R.id.logout_foro:
                        CampusBD login = new FirebaseBD();
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

    private void agregarMapa() {
        Intent intent = new Intent(this, AgregarMapa.class);
        //AGREGAR DATOS
        startActivity(intent);
        //llama la actividad de mapas

    }

    /**
     * Este método inserta una nueva respuesta partir de lo que se digite, luego de insertar en
     * la base de datos se devuelve a la vista de respuesta para dicha pregunta
     */
    private void agregarRespuesta() {
        String textoRespuesta = mEditText.getText().toString();
        int idPregunta = pregunta.getId();
        int idTema = pregunta.getTemaID();

        //TODO AGREGAR DATOS DE MAPA A OBJETO RESPUESTA
        //Respuesta respuesta = new Respuesta(0, textoRespuesta, idPregunta, 0, 0);
        //Respuesta respuesta = new Respuesta(0, nombreUsuario, textoRespuesta, idPregunta, idTema, 0, 0);
        //mRespuestaViewModel.insert(respuesta);

        //insertar en firebase

        databaseReference.getRespuestasRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int id;
                if(dataSnapshot.getValue() == null){
                    id = 1;
                }else{
                    id = (int) dataSnapshot.getChildrenCount() + 1;
                }
                String texto = mEditText.getText().toString();
                Respuesta respuesta = new Respuesta(id, nombreUsuario, textoRespuesta, idPregunta, idTema, 0,0);

                // Inserta en Firebase tambien
                CrearRespuestaForoGeneral.this.databaseReference.getRespuestasRef().child(Integer.toString(id)).setValue(respuesta);

                //Luego de insertar volver a vista previa
                Intent intent = new Intent(CrearRespuestaForoGeneral.this, ForoGeneralVerRespuestas.class);
                intent.putExtra("preguntaSeleccionada", pregunta);
                startActivity(intent);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

//        // Se lanza un SELECT en la base, para así poder recuperar el ID AUTOGENERADO de room y agregarlo de manera correcta a Firebase
//        mRespuestaViewModel.getIDPorTextoYUsuario(respuesta.texto, respuesta.nombreUsuario).observe(this, new Observer<List<Respuesta>>() {
//            @Override
//            public void onChanged(List<Respuesta> respuestas) {
//                int idGenerado = 0;
//                List<Respuesta> respuestaRoom = new ArrayList<Respuesta>();
//                for (Respuesta respuestaTemp : respuestas) {
//                    idGenerado = respuestaTemp.getId();
//                    respuestaRoom.add(respuestaTemp);
//                }
//
//                if (respuestaRoom.size() > 0)
//                {
//                    // Inserta en Firebase tambien
//                    CrearRespuestaForoGeneral.this.databaseReference.getRespuestasRef().child(Integer.toString(pregunta.getId())).child(Integer.toString(idGenerado)).setValue(respuestaRoom.get(0));
//
//                    // Servicio para detectar el estado de la orden (falta probar)
//                    Intent servicioIntent = new Intent(CrearRespuestaForoGeneral.this, NotificacionRespuestaService.class);
//                    servicioIntent.putExtra("llave_pregunta", Integer.toString(pregunta.getId()));
//                    //servicioIntent.putExtra("llave_tema", pregunta.temaID);
//                    startService(servicioIntent);
//
//                    //Luego de insertar volver a vista previa
//                    Intent intent = new Intent(CrearRespuestaForoGeneral.this, ForoGeneralVerRespuestas.class);
//                    intent.putExtra("preguntaSeleccionada", pregunta);
//                    startActivity(intent);
//                }
//
//            }
//        });
    }

    /**
     * Verifica si el contenido de respuesta no es vacio
     *
     * @return boolean
     */
    private boolean verificarRespuesta() {
        if (mEditText.getText().toString().equals("")) {
            Toast.makeText(CrearRespuestaForoGeneral.this, "Por favor digite su respuesta", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Este método realiza una actividad cuando un objeto específico de la lista es seleccionado
     *
     * @param item funciona para indicar el objeto de la lista que se selecionó
     * @return un booleano, ya que aún no se ha implementado el llamado a la base de datos
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
