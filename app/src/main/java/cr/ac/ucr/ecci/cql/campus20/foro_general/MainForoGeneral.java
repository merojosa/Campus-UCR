package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import com.google.android.material.navigation.NavigationView;

import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import cr.ac.ucr.ecci.cql.campus20.MainActivity;
import cr.ac.ucr.ecci.cql.campus20.R;

public class MainForoGeneral extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_foro_general);

        //boton temporal para ir a la pantalla de temas
        Button buttonTemas = (Button) findViewById(R.id.buttonTemas);

        // Asocia evento clic al boton
        buttonTemas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irATemas();
            }
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

        // Inicialización de la barra de navegación general
        BottomNavigationView generalNavigationView = findViewById(R.id.bottom_navigation);

        // Dejar por defecto el ícono de foro seleccionado
        generalNavigationView.setSelectedItemId(R.id.foro);

        // Ejecución del listener para cambiar de actividad
        generalNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // Dependiendo del item seleccionado, ejecutará el Intent del módulo específico
                switch(menuItem.getItemId()){
                    case R.id.ucreats:
                        break;
                    case R.id.mujeres:
                        break;
                    case R.id.foro:
                        break;
                    case R.id.lugares:
                        break;
                }
                return false;
            }
        });

        // Codigo que realiza el llenado de la lista de temas recomendados
        ListView listaTemasRecomendados = findViewById(R.id.listaTemasSugeridos);
        ForoGeneralVerTemas temas = new ForoGeneralVerTemas();
        AdaptadorTemas adaptadorTemas = new AdaptadorTemas(this, temas.GetTemasRecomendados());
        listaTemasRecomendados.setAdapter(adaptadorTemas);

        dl = (DrawerLayout)findViewById(R.id.activity_main_foro_general);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home_foro:
                        Intent intentForo = new Intent(MainForoGeneral.this, MainForoGeneral.class);
                        startActivity(intentForo);
                        break;
                    case R.id.temas_foro:
                        Intent intent2Foro = new Intent(MainForoGeneral.this, ForoGeneralVerTemas.class);
                        startActivity(intent2Foro);
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });

    }
    // Ir a pantalla de agregar pregunta
    private void crearPregunta() {
        Intent intent = new Intent(this, CrearPreguntaForoGeneral.class);
        // Llamada a la actividad de crear pregunta
        startActivity(intent);
    }

    // Ir al foro
    private void irATemas() {
        Intent intent = new Intent(this, ForoGeneralVerTemas.class);
        // Llamada a la actividad de mostrar temas
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
