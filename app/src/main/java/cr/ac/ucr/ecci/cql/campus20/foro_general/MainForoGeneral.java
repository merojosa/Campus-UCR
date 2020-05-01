package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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


        // Inicialización de la barra de navegación general
        BottomNavigationView generalNavigationView = findViewById(R.id.bottom_navigation);
        // Dejar por defecto el Inicio seleccionado
        generalNavigationView.setSelectedItemId(R.id.foro);
        // Ejecución del listener para cambiar de actividad
        generalNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // Dependiendo del item seleccionado, ejecutará el Intent del módulo específico
                switch(menuItem.getItemId()){
                    case R.id.inicio:
                        Intent intentInicio = new Intent(MainForoGeneral.this, MainActivity.class);
                        startActivity(intentInicio);
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

    }
    // Ir al foro
    private void irATemas() {
        Intent intent = new Intent(this, ForoGeneralVerTemas.class);
        // Llamada a la actividad
        startActivity(intent);
    }
}
