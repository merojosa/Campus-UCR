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
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.ConfiguracionActivity;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.LoginActivity;
import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters.RVAdapterRespuesta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.PreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.RespuestaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

public class ForoGeneralVerRespuestas extends AppCompatActivity {

    LiveData<List<Respuesta>> respuestas;
    RespuestaViewModel mRespuestaViewModel;
    private TextView tituloPregunta;
    private RecyclerView recyclerViewRespuestas;
    private RVAdapterRespuesta adapterRespuesta;
    private List<Respuesta> listaRespuestas;
    FloatingActionButton buttonAgregarRespuestas;

    //boton para marcar como resuelto
    CheckBox marcarResuelto;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private List<Respuesta> respuestasFireBase;
    LiveData<List<Respuesta>> temp;
    String nombreUsuario;

    private PreguntaViewModel mPreguntaViewModel;


    ForoGeneralFirebaseDatabase databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general_ver_respuestas);
        Intent mIntent = getIntent();

        mPreguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);

        // Se instancia el firebaseReference
        this.databaseReference = new ForoGeneralFirebaseDatabase();

        // Boton flotante de Agregar Respuestas
        buttonAgregarRespuestas = findViewById(R.id.buttonAgregarRespuestas);
        buttonAgregarRespuestas.setVisibility(View.VISIBLE);

        PreguntaCard preguntaSeleccionada = mIntent.getParcelableExtra("preguntaSeleccionada");
        //busca usuario actual
        this.nombreUsuario = mIntent.getStringExtra("nombreUsuario");

        int idPreguntaSeleccionada = preguntaSeleccionada.getId();
        int idTemaSeleccionado = preguntaSeleccionada.getTemaID();
        //revisa si esta resuelta
        final int[] cerrada = {preguntaSeleccionada.getResuelta()};


        //valores de cerrada
        //0 para abierta
        //1 para resuelta

        //boton para marcar como resuelto
        marcarResuelto = findViewById(R.id.marcarResuelto);

        if (cerrada[0] == 1){//la pregunta ha sido cerrada
            marcarResuelto.setChecked(true);
            buttonAgregarRespuestas.setVisibility(View.INVISIBLE);
        }
        else{
            marcarResuelto.setChecked(false);
            marcarResuelto.setText("Marcar como resuelta");
            buttonAgregarRespuestas.setVisibility(View.VISIBLE);
        }

        //LiveData<List<Pregunta>> lista = mPreguntaViewModel.getPreguntasTema(preguntaSeleccionada.getId());
        //List<Pregunta> probando = lista.getValue();
        // Asocia evento clic al boton
        marcarResuelto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //primero verifica que la pregunta este abierta
                String creador = preguntaSeleccionada.getNombreUsuario();
                if(cerrada[0] != 1){
                    //luego verifica si el usuario es el creador de la pregunta
                    if(nombreUsuario.compareTo(creador) == 0){
                        //llama al metodo para cerrar la pregunta
                        cerrarReabrirPregunta(preguntaSeleccionada, 1);
                        //reporta que se cerro la pregunta
                        Toast.makeText(ForoGeneralVerRespuestas.this, "Pregunta cerrada ", Toast.LENGTH_SHORT).show();
                        marcarResuelto.setText("Resuelto");
                        //invisible para agregar respuestas
                        buttonAgregarRespuestas.setVisibility(View.INVISIBLE);
                        cerrada[0] =1;
                    }
                    else{
                        Toast.makeText(ForoGeneralVerRespuestas.this, "Solo el creador puede cerrarla ", Toast.LENGTH_SHORT).show();
                        marcarResuelto.setChecked(false);
                    }
                }
                else //reabrir pregunta
                {
                    //luego verifica si el usuario es el creador de la pregunta
                    if(nombreUsuario.compareTo(creador) == 0){
                        //llama al metodo para cerrar la pregunta
                        cerrarReabrirPregunta(preguntaSeleccionada, 0);
                        //reporta que se cerro la pregunta
                        Toast.makeText(ForoGeneralVerRespuestas.this, "Pregunta reabierta ", Toast.LENGTH_SHORT).show();
                        marcarResuelto.setText("Marcar como resuelta");
                        //visible para agregar respuestas
                        buttonAgregarRespuestas.setVisibility(View.VISIBLE);
                        cerrada[0] =0;
                    }
                    else{
                        Toast.makeText(ForoGeneralVerRespuestas.this, "Solo el creador puede reabrirla ", Toast.LENGTH_SHORT).show();
                        marcarResuelto.setChecked(true);
                    }
                }


            }
        });


        recyclerViewRespuestas = (RecyclerView) findViewById(R.id.verRespuestasRV);

        recyclerViewRespuestas.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewRespuestas.setHasFixedSize(true);

        adapterRespuesta = new RVAdapterRespuesta(this, listaRespuestas);
        recyclerViewRespuestas.setAdapter(adapterRespuesta);

        mRespuestaViewModel = new ViewModelProvider(this).get(RespuestaViewModel.class);



        //sacar los elementos de la vista desde firebase
        //pasarlos a respuestas
        final boolean[] enNube = {false};
        //obtiene todas las respuestas para el id de la pregunta, pero solo las agrega si el id del tema coincide

        this.respuestasFireBase = new ArrayList<Respuesta>();

        this.databaseReference.getRespuestasRef().child(String.valueOf(idPreguntaSeleccionada)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Se recorre el snapshot para sacar los datos
                ForoGeneralVerRespuestas.this.respuestasFireBase.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    int id = ds.child("id").getValue(Integer.class);
                    String nombreUsuario = ds.child("nombreUsuario").getValue(String.class);
                    int preguntaID = ds.child("preguntaID").getValue(Integer.class);
                    int temaID = ds.child("temaID").getValue(Integer.class);
                    String texto = ds.child("texto").getValue(String.class);
                    int contadorLikes = ds.child("contadorLikes").getValue(Integer.class);
                    int contadorDisLikes = ds.child("contadorDislikes").getValue(Integer.class);

                    // Se crea la respuesta
                    if(temaID == idTemaSeleccionado){
                        Respuesta respuesta = new Respuesta(id, nombreUsuario, texto, preguntaID, temaID, contadorLikes, contadorDisLikes);
                        ForoGeneralVerRespuestas.this.respuestasFireBase.add(respuesta);


                        //busca si esta en la base local, si no esta lo agrega
                        /*temp = mRespuestaViewModel.getRespuestaDePreguntaYTema(id, idPreguntaSeleccionada, idTemaSeleccionado);
                        temp.observe(ForoGeneralVerRespuestas.this, new Observer<List<Respuesta>>() {
                            @Override
                            public void onChanged(List<Respuesta> respuestas) {
                                if (respuestas.size() < 1) {
                                    //el valor no esta en room, hay que agregarlo
                                    mRespuestaViewModel.insert(respuesta);
                                    //adapterRespuesta.setRespuestas(respuestas);
                                }
                            }
                        });*/
                    }

                }
                if(ForoGeneralVerRespuestas.this.respuestasFireBase.size()>0){
                    adapterRespuesta.setRespuestas(ForoGeneralVerRespuestas.this.respuestasFireBase);
                    //confirmacion temporal para saber cual presentar
                    enNube[0] = true;
                }else {
                    tituloPregunta.setText(preguntaSeleccionada.getTexto() + ": " + "No hay respuestas aun.");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }

        });


        //respuestas = mRespuestaViewModel.getRespuestasDePregunta(idPreguntaSeleccionada);


        tituloPregunta = (TextView) findViewById(R.id.preguntaSeleccionada);
        tituloPregunta.setText(preguntaSeleccionada.getTexto());

        if(!enNube[0]){
            respuestas = mRespuestaViewModel.getRespuestasDePreguntaYTema(idPreguntaSeleccionada, idTemaSeleccionado);
            respuestas.observe(this, new Observer<List<Respuesta>>() {
                @Override
                public void onChanged(List<Respuesta> respuestas) {
                    if (respuestas.size() > 0) {
                        adapterRespuesta.setRespuestas(respuestas);
                    } /*else {
                        tituloPregunta.setText(preguntaSeleccionada.getTexto() + ": " + "No hay respuestas aun.");
                    }*/
                }
            });
        }


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
                    case R.id.mis_preguntas_foro:
                        Intent intent = new Intent(ForoGeneralVerRespuestas.this, ForoGeneralVerMisPreguntas.class);
                        intent.putExtra("nombreUsuario", ForoGeneralVerRespuestas.this.databaseReference.obtenerUsuario());
                        // Llamada a la actividad de crear pregunta
                        startActivity(intent);
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

    /**
     * Modifica la pregunta en la base de datos, ya sea marcandola o desmarcadola como resuelta
     * @param pregunta pregunta a modificar
     * @param accion 0 para reabrir, 1 para cerrar
     */
    public void cerrarReabrirPregunta(@NotNull PreguntaCard pregunta, int accion){
        //llamar query sql con id de pregunta y accion como parametros
        //Pregunta preguntaTemp = new Pregunta(pregunta.getId(), pregunta.getNombreUsuario(), pregunta.getTemaID(), pregunta.getTexto(), pregunta.getContadorLikes(), pregunta.getContadorDislikes());
        //preguntaTemp.setResuelta(accion);
        //mPreguntaViewModel.updateResuelta(pregunta.getId(), accion);
        //actualiza el valor tambien en firebase
        ForoGeneralVerRespuestas.this.databaseReference.getPreguntasRef().child(Integer.toString(pregunta.getTemaID())).child(Integer.toString(pregunta.getId())).child("resuelta").setValue(accion);
    }
}
