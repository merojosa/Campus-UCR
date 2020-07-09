package cr.ac.ucr.ecci.cql.campus20.ucr_eats.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class Restarter  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.i("Broadcast Listened", "Service tried to stop");
        Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();
        System.out.println("Restarteando servicio +++++++++++++++++++++++++++++++++++++++++++++");
        String idOrden = intent.getStringExtra("idOrden");
        Intent servicioIntent = new Intent(context, NotificacionPedidoService.class);
        servicioIntent.putExtra(NotificacionPedidoService.LLAVE_ID_ORDEN, idOrden);

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        //{
        //    context.startForegroundService(servicioIntent);
        //}
        //else
        //{
            context.startService(servicioIntent);
        //}
    }
}
