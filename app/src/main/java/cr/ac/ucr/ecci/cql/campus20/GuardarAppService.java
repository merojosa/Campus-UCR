package cr.ac.ucr.ecci.cql.campus20;

import android.app.IntentService;
import android.content.Intent;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.red_mujeres.MainRedMujeres;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;


public class GuardarAppService extends IntentService
{
    private static final String PATH_CONFIG = "config_usuarios/";
    private static final String DESTINO_APP = "/app_inicial";
    public static final String APP_ID_KEY = "appId";


    public GuardarAppService()
    {
        super("GuardarAppService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        int appId = intent.getIntExtra(APP_ID_KEY, -1);

        appId = traducirAppId(appId);

        if(appId != -1)
        {
            LoginBD loginBD = new FirebaseBD();
            String correo = loginBD.obtenerCorreoActual();
            String usuario = correo.substring(0, correo.indexOf('@'));

            // Guardar datos
            loginBD.escribirDatos(PATH_CONFIG + usuario + DESTINO_APP, appId);
        }
    }

    private int traducirAppId(int id)
    {
        switch (id)
        {
            case R.id.ucreats: return 0;

            case R.id.mujeres: return 1;

            case R.id.foro: return 2;

            case R.id.lugares: return 3;
        }
        return -1;
    }
}
