package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.TemaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

public class CrearPreguntaForoGeneral extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private TemaDao temaDao;
    private LiveData<List<Tema>> temas;
    private List<Tema> temasLista;
    private List<String> titulosTemas;


    /**
     * Método que se invoca al entrar a la actividad de Crear una pregunta
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pregunta_foro_general);
        temasLista = new ArrayList<Tema>();
        titulosTemas = new ArrayList<String>();

        // Llamado a base de datos
        temaDao = ForoGeneralDatabase.getDatabase(getApplicationContext()).temaDao();
        temaDao.getTemas().observe(this, new Observer<List<Tema>>() {
            @Override
            public void onChanged(List<Tema> temas) {
                for(Tema tema : temas){
                    temasLista.add(tema);
                    titulosTemas.add(tema.getTitulo());
                }
            }
        });
        String[] titulos;
        if(titulosTemas.size() > 0){
            titulos = titulosTemas.toArray(new String[0]);
        }else{
            titulos = new String[]{"a", "b"};
        }



        Spinner dropdown = (Spinner)findViewById(R.id.listaTemasCrearPregunta);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titulos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(dataAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String titulo = parent.getItemAtPosition(position).toString();
                Toast.makeText(CrearPreguntaForoGeneral.this, titulo, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ;
            }
        });


        // Codigo para agregar borde al espacio para rellenar la pregunta
        EditText mEditText = (EditText) findViewById(R.id.textoCrearPregunta);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#00ffffff"));
        gd.setStroke(2, Color.parseColor("#00C0F3"));
        mEditText.setBackground(gd);

        // Codigo para manejar color del boton y evento de click
        Button btnCrearPregunta = (Button) findViewById(R.id.btnCrearPregunta);
        btnCrearPregunta.setBackgroundColor(Color.parseColor("#00C0F3"));
        btnCrearPregunta.setTextColor(Color.BLACK);
        btnCrearPregunta.setText("Crear Pregunta");
        btnCrearPregunta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificarPregunta()){
                    Toast.makeText(CrearPreguntaForoGeneral.this, "Bien", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CrearPreguntaForoGeneral.this, "Por favor ingrese de manera correcta los datos.", Toast.LENGTH_SHORT).show();
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


    private boolean verificarPregunta(){
        return false;
    }
}
