package cr.ac.ucr.ecci.cql.campus20;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.red_mujeres.MainRedMujeres;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;

public class MainEmptyActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        iniciar();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        iniciar();
    }

    private void iniciar()
    {
        CampusBD campusBD = new FirebaseBD();
        Redireccionador redireccionador = new Redireccionador();
        
        if(campusBD.autenticado())
        {
            redireccionador.irActividadGuardada(this);
        }
        else
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }
}
