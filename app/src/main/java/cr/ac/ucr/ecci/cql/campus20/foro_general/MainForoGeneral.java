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

    }
    // Ir al foro
    private void irATemas() {
        Intent intent = new Intent(this, ForoGeneralVerTemas.class);
        // Llamada a la actividad
        startActivity(intent);
    }
}
