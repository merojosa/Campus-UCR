package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

public class ConfiguracionActivity extends AppCompatActivity
{

    public static final String KEY_CORREO = "CORREO";
    public static final String PATH_CONFIG = "config_usuarios/";
    public static final String DESTINO_APP = "/app_inicial";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        Button buttonIniciar = findViewById(R.id.buttonGuardar);
        buttonIniciar.setOnClickListener(v -> guardarDatos());
    }

    private void guardarDatos()
    {
        RadioGroup apps = findViewById(R.id.radioGroupConfig);

        int appElegido = apps.getCheckedRadioButtonId();

        // El default es UCR Eats
        int opcion = 0;

        switch (appElegido)
        {
            case R.id.radioButtonMujeres:
            {
                opcion = 1;
                break;
            }
            case R.id.radioButtonForos:
            {
                opcion = 2;
                break;
            }
            case R.id.radioButtonLugares:
            {
                opcion = 3;
                break;
            }
        }

        guardarConfiguracion(opcion);

        Redireccionador redireccionador = new Redireccionador();
        redireccionador.irActividadGuardada(this);
    }

    private void guardarConfiguracion(int appId)
    {
        LoginBD loginBD = new FirebaseBD();
        String correo = loginBD.obtenerCorreoActual();
        String usuario = correo.substring(0, correo.indexOf('@'));

        // Guardar datos
        loginBD.escribirDatos(PATH_CONFIG + usuario + DESTINO_APP, appId);
    }
}
