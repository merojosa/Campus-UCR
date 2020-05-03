package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import cr.ac.ucr.ecci.cql.campus20.R;

public class CrearPreguntaForoGeneral extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    /**
     * Método que se invoca al entrar a la actividad de Crear una pregunta
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pregunta_foro_general);

        /*
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
                        Intent intentForo = new Intent(CrearPreguntaForoGeneral.this, MainForoGeneral.class);
                        startActivity(intentForo);
                        break;
                    case R.id.lugares:
                        break;
                }
                return false;
            }
        });*/

        //Codigo que maneja la navegacion de izquierda a derecha
        dl = (DrawerLayout)findViewById(R.id.activity_main_crear_pregunta);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
