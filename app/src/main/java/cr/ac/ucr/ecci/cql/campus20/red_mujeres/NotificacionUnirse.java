package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

class NotificacionUnirse  {
    public static String CHANNEL_ID = "Red_Mujeres";



    public void setUnirseNotificacionListener(Context context, String id)
    {
        createNotificationChannel(context);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean val;
                String idDriver;
                val = Boolean.parseBoolean(String.valueOf(dataSnapshot.child("Comunidades").child("GrupoEj").child("EnRuta").getValue()));
                idDriver = (String) dataSnapshot.child("Comunidades").child("GrupoEj").child("driverID").getValue();
                if(val == true && !(idDriver.equals(id))) {
                    String nombre = String.valueOf(dataSnapshot.child("usuarios_red_mujeres").child(idDriver).child("Nombre").getValue());
                    enviarNotificacion(context, idDriver, nombre);
                }
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

    public void enviarNotificacion(Context context, String id, String nombre) {
        String notificationMsg = nombre + " está en ruta";
        Intent intent = new Intent(context, MainRedMujeres.class);
        intent.putExtra("id", id);
        intent.putExtra("nombre", nombre);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.mapbox_logo_icon)
                .setContentTitle("Ruta en curso")
                .setContentText(notificationMsg)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notificationMsg))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(new long[] { 1000, 1000})
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(createID(), builder.build());
    }

    private void createNotificationChannel(Context context) {
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
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class); // getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
