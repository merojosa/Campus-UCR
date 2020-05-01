package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }

    // Ir al foro
    private void irForoGeneral () {
        Intent intent = new Intent(this, MainForoGeneral.class);
        // Llamada a la actividad
        startActivity(intent);
    }
}