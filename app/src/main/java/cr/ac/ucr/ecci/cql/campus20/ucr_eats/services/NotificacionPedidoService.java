package cr.ac.ucr.ecci.cql.campus20.ucr_eats.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;

import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites.OrdenesPendientesActivity;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.OrderStatus;


public class NotificacionPedidoService extends Service
{
    public final static String LLAVE_ID_ORDEN = "llave_orden";

    // Es necesario establecer un canal de notifiaciones
    private static String CHANNEL_ID = "UCR_Eats_Orders";
    private static String PENDIENTE_MESSAGE = "Esperando a que un repartidor acepte tu pedido";
    private static String HACIA_SODA_MESSAGE = "Un repartidor se dirige a la soda a recoger tu pedido";
    private static String HACIA_CASA_MESSAGE = "El repartidor va rumbo a entregarte el pedido";

    private String idOrden;
    DatabaseReference ref;
    ValueEventListener listener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        this.idOrden = intent.getStringExtra(LLAVE_ID_ORDEN);
        setOrderNotificacionListener(idOrden);
        System.out.println("Iniciando servicio................................................"+idOrden);
        return START_STICKY;
    }

    public void setOrderNotificacionListener(String orderId /* algun otro dato, como el meal, o la soda*/)
    {
        createNotificationChannel();
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();

        ref = db.getPendingOrdersRef().child(orderId).child("status");


        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OrderStatus status = OrderStatus.PENDIENTE;
                if (dataSnapshot.getValue() != null)
                    status = dataSnapshot.getValue(OrderStatus.class);

                // Creamos la notificación, según status = 0 (pedido aceptado pero no recogido) o 1 (pedido ha sido recogido)
                // https://developer.android.com/training/notify-user/build-notification
                String notificationMsg = "Algo ha salido mal";
                if (status == OrderStatus.HACIA_SODA)
                    notificationMsg = HACIA_SODA_MESSAGE;
                else if (status == OrderStatus.HACIA_CASA)
                    notificationMsg = HACIA_CASA_MESSAGE;

                // Si el pedido aún está pendiente por ser aceptado, no mostramos notificción
                if (status != OrderStatus.PENDIENTE)
                {
                    // Create an explicit intent for an Activity in your app
                    Intent intent = new Intent(getApplicationContext(), OrdenesPendientesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_ucreats)
                            .setContentTitle("UCR Eats")
                            .setContentText(notificationMsg)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(notificationMsg))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setVibrate(new long[] { 1000, 1000})
                            // Set the intent that will fire when the user taps the notification
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(createID(), builder.build());
                }
                else
                {
                    // Log sobre el estatus del pedido
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }
        };
        Toast.makeText(this, db.toString()+ " " + ref.toString() + " " + listener.toString(), Toast.LENGTH_LONG).show();
        ref.addValueEventListener(listener);
    }
    // Parar el id de la notificación ( la precisión usada será suficiente puesto que los chances de recibir
    // varias notificaciones en el mismo segundo son muy bajos)
    public int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }

    // Es seguro llamar este método más de una vez, ya que no replica canales ya creados,
    // sin embargo es necesario llamarlo temprano, tan pronto como inicia la aplicación si es posible.
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "UcrEatsNotifications"; //getString(R.string.channel_name);
            String description = "Notifications for UcrEats module"; //getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class); // getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // Toast.makeText(this, "Reiniciando servicio...", Toast.LENGTH_SHORT).show();
        //Intent broadcastIntent = new Intent();
        //broadcastIntent.putExtra("idOrden", idOrden);
        //broadcastIntent.setAction("restartservice");
        //broadcastIntent.setClass(this, Restarter.class);
        //this.sendBroadcast(broadcastIntent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //System.out.println("onTaskRemoved called");
        super.onTaskRemoved(rootIntent);
        ref.removeEventListener(listener);
        //Toast.makeText(this, "Reiniciando servicio...", Toast.LENGTH_SHORT).show();
        Intent broadcastIntent = new Intent();
        broadcastIntent.putExtra("idOrden", idOrden);
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);

        this.stopSelf();
    }

}