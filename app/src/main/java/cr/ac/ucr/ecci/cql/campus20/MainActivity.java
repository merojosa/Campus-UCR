package cr.ac.ucr.ecci.cql.campus20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //boton temporal para ir al foro general
        Button buttonForoGeneral = (Button) findViewById(R.id.buttonForoGeneral);

        // Asocia evento clic al boton
        buttonForoGeneral.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irForoGeneral();
            }
        });


        // Inicialización de la barra de navegación general
        BottomNavigationView generalNavigationView = findViewById(R.id.bottom_navigation);
        // Dejar por defecto el Inicio seleccionado
        generalNavigationView.setSelectedItemId(R.id.inicio);
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
                        Intent intentForo = new Intent(MainActivity.this, MainForoGeneral.class);
                        startActivity(intentForo);
                        break;
                    case R.id.lugares:
                        break;
                }
                return false;
            }
        });
    }



    // Ir al foro
    private void irForoGeneral () {
        Intent intent = new Intent(this, MainForoGeneral.class);
        // Llamada a la actividad
        startActivity(intent);
    }
}