package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
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

import cr.ac.ucr.ecci.cql.campus20.MainActivity;
import cr.ac.ucr.ecci.cql.campus20.R;

public class MainForoGeneral extends AppCompatActivity {

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
}
