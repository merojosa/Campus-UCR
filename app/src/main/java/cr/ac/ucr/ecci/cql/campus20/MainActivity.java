package cr.ac.ucr.ecci.cql.campus20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.app.FragmentTransaction;
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
import android.util.Log;
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

        Button buttonIniciarSesion = findViewById(R.id.buttonLogin);

        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCredenciales();
            }
        });
    }

    public void validarCredenciales()
    {
        startActivity(new Intent(this, MainUcrEats.class));
    }

}