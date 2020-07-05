package cr.ac.ucr.ecci.cql.campus20.foro_general;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class NotificacionRespuestaService extends Service {

    // Se establece la variable para el canal de notificaciones
    public static String CHANNEL_ID = "Foro_Respuestas";
    public static String RESPUESTA_MESSAGE = "Hay una nueva respuesta a tu pregunta";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence nombre = "ForoNotifications";
            String descripcion = "Notificaciones para el módulo Foro";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel canal = new NotificationChannel(CHANNEL_ID, nombre, importancia);
            canal.setDescription(descripcion);
            // Registra el canal en el sistema
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(canal);
        }
    }
}
