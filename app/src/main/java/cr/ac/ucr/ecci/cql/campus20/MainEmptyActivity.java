package cr.ac.ucr.ecci.cql.campus20;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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
        CampusBD loginBD = new FirebaseBD();
        Redireccionador redireccionador = new Redireccionador();
        
        if(loginBD.autenticado())
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
