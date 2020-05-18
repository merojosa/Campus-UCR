package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ConfiguracionActivity extends AppCompatActivity
{

    public static final String KEY_CORREO = "CORREO";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        // Obtener correo del usuario autenticado.
        String correo_usuario = getIntent().getStringExtra(KEY_CORREO);
    }
}
