package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cr.ac.ucr.ecci.cql.campus20.R;

class NotificacionUnirse extends Service {
    private static String MESSAGE = "El repartidor va rumbo a entregarte el pedido";
    public static String CHANNEL_ID = "Red_Mujeres";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        setUnirseNotificacionListener();
        return START_STICKY;
    }

    public void setUnirseNotificacionListener()
    {
        createNotificationChannel();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Comunidades");
        ref.child("EnRuta").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String notificationMsg = MESSAGE;
                Intent intent = new Intent(getApplicationContext(), MainRedMujeres.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.mapbox_logo_icon)
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
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }
        });
    }

    public int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "RedMujeresNotifications"; //getString(R.string.channel_name);
            String description = "Notifications for RedMujeres module"; //getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class); // getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
