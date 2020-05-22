package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.PreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.TemaViewModel;
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
    private PreguntaViewModel mPreguntaViewModel;

    /**
     * Método que se invoca al entrar a la actividad de Crear una pregunta
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Instancias necesarias
        mPreguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pregunta_foro_general);
        this.temasLista = new ArrayList<Tema>();
        // Llamado a base de datos
        TemaViewModel mTemaViewModel = new ViewModelProvider(this).get(TemaViewModel.class);
        mTemaViewModel.getAllTemas().observe(this, new Observer<List<Tema>>() {
            @Override
            public void onChanged(List<Tema> temas) {
                for(int i = 0; i < temas.size(); ++i){
                    temasLista.add(temas.get(i));
                }
                dropdown = (Spinner)findViewById(R.id.listaTemasCrearPregunta);
                ArrayAdapter<Tema> dataAdapter = new ArrayAdapter<Tema>(getApplicationContext(), android.R.layout.simple_spinner_item, temasLista);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(dataAdapter);
                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Tema tema = dataAdapter.getItem(position);
                        idTemaSeleccionado = tema.getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        ;
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
                        Intent intentForo = new Intent(CrearPreguntaForoGeneral.this, MainForoGeneral.class);
                        startActivity(intentForo);
                        break;
                    case R.id.temas_foro:
                        Intent intent2Foro = new Intent(CrearPreguntaForoGeneral.this, ForoGeneralVerTemas.class);
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
        Pregunta pregunta = new Pregunta(0, idTemaSeleccionado, texto, 0, 0);
        mPreguntaViewModel.insert(pregunta);
        Intent intent = new Intent(this, ForoGeneralVerPreguntas.class);
        // Llamada a la actividad de crear pregunta
        intent.putExtra("idTemaSeleccionado", idTemaSeleccionado);
        startActivity(intent);
    }
}
