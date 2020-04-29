package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Instanciar boton para pasar a la actividad UcrEats
        Button buttonUcrEats = (Button) findViewById(R.id.buttonUcrEats);
        // Asocia evento clic al boton
        buttonUcrEats.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irUcrEats();
            }
        });
    }

    private void irUcrEats()
    {
        // Intent para llamar a la Actividad MainUcrEats
        Intent intent = new Intent(this, MainUcrEats.class);
        // Llamada a la actividad
        startActivity(intent);
    }
}
