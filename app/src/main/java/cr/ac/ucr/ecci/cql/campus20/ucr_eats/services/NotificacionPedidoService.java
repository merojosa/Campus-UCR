package cr.ac.ucr.ecci.cql.campus20.ucr_eats.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;

import android.graphics.BitmapFactory;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import cr.ac.ucr.ecci.cql.campus20.R;


public class NotificacionPedidoService extends Service
{
    private final static String NOTIFICACION_ORDEN = "Notificaciones de órdenes";
    private static final int NOTIFICACION_ID = 101;
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

        // Escuchar el cambio de firebase del idOrden

        return START_STICKY;
    }
}
