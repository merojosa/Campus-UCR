package cr.ac.ucr.ecci.cql.campus20.ucr_eats.services;

import android.app.Service;
import android.content.Intent;

import android.os.IBinder;

import androidx.annotation.Nullable;


public class NotificacionPedidoService extends Service
{
    public final static String LLAVE_ID_ORDEN = "llave_orden";

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String idOrden = intent.getStringExtra(LLAVE_ID_ORDEN);

        return START_STICKY;
    }
}
