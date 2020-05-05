package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cr.ac.ucr.ecci.cql.campus20.red_mujeres.MainRedMujeres;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.DeploymentScript;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import android.view.View;
import android.widget.Button;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button2); //TODO:Cambiar nombre variable. Botón para ir a mapa

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, MainRedMujeres.class); //Mapa pantalla principal red mujeres
                MainActivity.this.startActivity(myIntent);
            }
        });

        /*Botón para ingresar al sub menú de los puntos de interes*/
        DeploymentScript.RunScript(getApplicationContext());
        Button buttonInterestPoints = findViewById(R.id.buttonInterestPoints);
        buttonInterestPoints.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goInterestPoints();
            }
        });

        findViewById(R.id.ucreatsbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irUcrEats();
            }
        });
    }

    private void goInterestPoints() {
        Intent intent = new Intent(this, InterestPointsActivity.class);
        startActivity(intent);
    }

    private void irUcrEats()
    {
        // Intent para llamar a la Actividad MainUcrEats
        Intent intent = new Intent(this, MainUcrEats.class);
        // Llamada a la actividad
        startActivity(intent);
    }

}