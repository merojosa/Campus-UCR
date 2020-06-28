package cr.ac.ucr.ecci.cql.campus20.ucr_eats.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
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
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites.OrdenesPendientesActivity;

/**
 * Esta clase está implementada temporalmente para realizar pruebas de campo sobre notificaciones
 */
public class UcrEatsNotificacionServiceFieldTest {

    // El servicio tendrá su propio contexto, pero se necesita para la prueba de campo
    Context context;

    // *******************************************************************************************
    // CUIDADO: el siguiente codigo pertenecerá al servicio para notificaciones de UCR Eats,
    // se escibe acá mientras se hace el set up del servicio.
    // *******************************************************************************************
    // Es necesario establecer un canal de notifiaciones
    private static String CHANNEL_ID = "UCR_Eats_Orders";
    private static String NOT_PICKED_MESSAGE = "Un repartidor ha aceptado tu pedido";
    private static String PICKED_MESSAGE = "El repartidor ha recogido tu pedido";

    public UcrEatsNotificacionServiceFieldTest(Context context) {
        this.context = context;
    }

    public void setOrderNotificacionListener(String orderId /* algun otro dato, como el meal, o la soda*/)
    {
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();

        DatabaseReference ref = db.getPendingOrdersRef().child(orderId).child("status");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int status = 0;
                if (dataSnapshot.getValue() != null)
                    status = dataSnapshot.getValue(Integer.class);

                // Creamos la notificación, según status = 0 (pedido aceptado pero no recogido) o 1 (pedido ha sido recogido)
                // https://developer.android.com/training/notify-user/build-notification
                String notificationMsg;
                if (status == 0)
                    notificationMsg = NOT_PICKED_MESSAGE;
                else
                    notificationMsg = PICKED_MESSAGE;

                // Create an explicit intent for an Activity in your app
                Intent intent = new Intent(context/*this*/, OrdenesPendientesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context /*this*/, 0, intent, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context /*this*/, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_ucreats)
                        .setContentTitle("UCR Eats")
                        .setContentText(notificationMsg)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(notificationMsg))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context/*this*/);

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(createID(), builder.build());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }
        });

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
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class); // getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // *******************************************************************************************
}
