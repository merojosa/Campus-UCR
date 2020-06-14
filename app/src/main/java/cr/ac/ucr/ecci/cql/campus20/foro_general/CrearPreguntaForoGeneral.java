package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ConfiguracionActivity;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.LoginActivity;
import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.PreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.RankPreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

public class CrearPreguntaForoGeneral extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private List<Tema> temasLista;
    private EditText mEditText;
    private Button btnCrearPregunta;
    private Spinner dropdown;
    private int idTemaSeleccionado;
    private String temaSeleccionado;
    private PreguntaViewModel mPreguntaViewModel;
    private RankPreguntaViewModel mRankPreguntaViewModel;
    private String nombreUsuario;
    private List<Tema> temasFirebase;
    ForoGeneralFirebaseDatabase databaseReference;

    /**
     * Método que se invoca al entrar a la actividad de Crear una pregunta
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Nombre del usuario actual
        Intent mIntent = getIntent();
        this.nombreUsuario = mIntent.getStringExtra("nombreUsuario");

        // Instancias necesarias
        mPreguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        mRankPreguntaViewModel = new ViewModelProvider(this).get(RankPreguntaViewModel.class);
        temasFirebase = new ArrayList<Tema>();

        // Se instancia el firebaseReference
        databaseReference = new ForoGeneralFirebaseDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pregunta_foro_general);
        this.temasLista = new ArrayList<Tema>();
        // Llamado a base de datos



        this.databaseReference.getTemasRef().addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // Se recorre el snapshot para sacar los datos
               for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   // Esto podría producir NullPointerException
                   int id = ds.child("id").getValue(Integer.class);
                   String titulo = ds.child("titulo").getValue(String.class);
                   String description = ds.child("description").getValue(String.class);
                   int contUsers = ds.child("contadorUsuarios").getValue(Integer.class);
                   int imagen = ds.child("imagen").getValue(Integer.class);

                   // Se crea el tema
                   Tema tema = new Tema(id, titulo, description, contUsers, imagen);
                   CrearPreguntaForoGeneral.this.temasFirebase.add(tema);
               }

                dropdown = (Spinner)findViewById(R.id.listaTemasCrearPregunta);
                ArrayAdapter<Tema> dataAdapter = new ArrayAdapter<Tema>(getApplicationContext(), android.R.layout.simple_spinner_item, temasFirebase);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(dataAdapter);
                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Tema tema = dataAdapter.getItem(position);
                        idTemaSeleccionado = tema.getId();
                        temaSeleccionado = tema.getTitulo();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        ;
                    }
                });
           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
           }
        });


        // Codigo para agregar borde al espacio para rellenar la pregunta
        mEditText = (EditText) findViewById(R.id.textoCrearPregunta);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#00ffffff"));
        gd.setStroke(2, Color.parseColor("#00C0F3"));
        mEditText.setBackground(gd);

        // Codigo para manejar color del boton y evento de click
        btnCrearPregunta = (Button) findViewById(R.id.btnCrearPregunta);
        btnCrearPregunta.setBackgroundColor(Color.parseColor("#00C0F3"));
        btnCrearPregunta.setTextColor(Color.BLACK);
        btnCrearPregunta.setText("Crear Pregunta");
        btnCrearPregunta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificarPregunta()){
                    agregarPregunta();
                }
            }
        });


        //Codigo que maneja la navegacion de izquierda a derecha
        dl = (DrawerLayout)findViewById(R.id.activity_main_crear_pregunta);
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
                        startActivity(new Intent(CrearPreguntaForoGeneral.this, MainForoGeneral.class));
                        break;
                    case R.id.temas_foro:
                        startActivity(new Intent(CrearPreguntaForoGeneral.this, ForoGeneralVerTemas.class));
                        break;
                    case R.id.pref_foro:
                        startActivity(new Intent(CrearPreguntaForoGeneral.this, ConfiguracionActivity.class));
                        break;
                    case R.id.logout_foro:
                        CampusBD login = new FirebaseBD();
                        login.cerrarSesion();

                        ActivityCompat.finishAffinity(CrearPreguntaForoGeneral.this);
                        startActivity(new Intent(CrearPreguntaForoGeneral.this, LoginActivity.class));
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


    private boolean verificarPregunta() {
        if(mEditText.getText().toString().equals("")){
            Toast.makeText(CrearPreguntaForoGeneral.this, "Por favor digite su pregunta", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void agregarPregunta() {

        String texto = mEditText.getText().toString();
        Pregunta pregunta = new Pregunta(0, nombreUsuario, idTemaSeleccionado, texto, 0, 0);
        mPreguntaViewModel.insert(pregunta);

        // Se lanza un SELECT en la base, para así poder recuperar el ID AUTOGENERADO de room y agregarlo de manera correcta a Firebase
        mPreguntaViewModel.getIDPorTextoYUsuario(pregunta.texto, pregunta.nombreUsuario).observe(this, new Observer<List<Pregunta>>() {
            @Override
            public void onChanged(List<Pregunta> preguntas) {
                int idGenerado = 0;
                List<Pregunta> preguntaRoom = new ArrayList<Pregunta>();
                for(Pregunta pregunta : preguntas){
                    idGenerado = pregunta.id;
                    preguntaRoom.add(pregunta);
                }

                // Inserta en Firebase tambien
                CrearPreguntaForoGeneral.this.databaseReference.getPreguntasRef().child(Integer.toString(pregunta.temaID)).child(Integer.toString(idGenerado)).setValue(preguntaRoom.get(0));

                Intent intent = new Intent(CrearPreguntaForoGeneral.this, ForoGeneralVerPreguntas.class);
                // Llamada a la actividad de ver respuestas
                intent.putExtra("idTemaSeleccionado", idTemaSeleccionado);
                intent.putExtra("temaSeleccionado", temaSeleccionado);
                startActivity(intent);
            }
        });
    }
}
