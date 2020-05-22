package cr.ac.ucr.ecci.cql.campus20;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.red_mujeres.MainRedMujeres;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;

public class Redireccionador
{
    private DatabaseReference mDatabase;
    private LoginBD loginBD;


    public Redireccionador()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        loginBD = new FirebaseBD();
    }

    // Termina la actividad actual cuando redirecciona
    public void irActividadGuardada(Context context)
    {
        // Si no hay actividad guardada, ir a la actividad de configuracion
        String correo = loginBD.obtenerCorreoActual();
        String idUsuario = correo.substring(0, correo.indexOf('@'));
        Intent intentConfiguracion = new Intent(context, ConfiguracionActivity.class);

        AtomicBoolean resultado = new AtomicBoolean(false);

        FirebaseListener listener = new FirebaseListener()
        {
            @Override
            public void exito(DataSnapshot dataSnapshot)
            {
                resultado.set(true);
                Long appUsuario = (Long) dataSnapshot.getValue();

                if(appUsuario != null)
                {
                    irAppPredeterminada(appUsuario.intValue(), context);
                }
                else
                {
                    // No hay actividad guardada, enviar a la configuracion inicial
                    intentConfiguracion.putExtra(ConfiguracionActivity.KEY_CORREO, idUsuario);
                    context.startActivity(intentConfiguracion);
                }

                // Termino la actividad que ejecuto el redireccionamiento
                ((Activity) context).finish();
            }

            @Override
            public void fallo(DatabaseError databaseError)
            {
                resultado.set(true);
            }
        };


        if(conexionInternet(context))
        {
            loginBD.tareaAppDefaultAsync(idUsuario, listener);

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    timer.cancel();
                    if (resultado.get() == false)
                    {
                        //  Timeout
                        loginBD.detenerAppDefaultAsync();

                        Toast.makeText(context,"Error de conexion a la base de datos",Toast.LENGTH_LONG).show();
                    }
                }
            };
            // Timeout de 10 segundos
            timer.schedule(timerTask, 10000L);
        }
        else
        {
            Toast.makeText(context,"No hay conexión a internet",Toast.LENGTH_LONG).show();
        }
    }

    private void irAppPredeterminada(int id, Context context)
    {
        Intent intent;
        switch (id)
        {
            case 0:
            default:
            {
                intent = new Intent(context, MainUcrEats.class);
                break;
            }
            case 1:
            {
                intent = new Intent(context, MainRedMujeres.class);
                break;
            }

            case 2:
            {
                intent = new Intent(context, MainForoGeneral.class);
                break;
            }
            case 3:
            {
                intent = new Intent(context, InterestPointsActivity.class);
                break;
            }
        }

        context.startActivity(intent);
    }

    private boolean conexionInternet(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
