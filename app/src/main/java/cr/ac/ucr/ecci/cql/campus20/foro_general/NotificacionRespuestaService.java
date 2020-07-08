package cr.ac.ucr.ecci.cql.campus20.foro_general;

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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

public class NotificacionRespuestaService extends Service {

    // Se establece la variable para el canal de notificaciones
    public static String CHANNEL_ID = "Foro_Respuestas";
    public static String RESPUESTA_MESSAGE = "Hay una nueva respuesta a tu pregunta: ";

    public String nombreUsuario = "";

    PreguntaCard preguntaCard = null;

    public ForoGeneralFirebaseDatabase db = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String idPregunta = intent.getStringExtra("llave_pregunta");
        setRespuestaNotificacionListener(idPregunta);
        return START_STICKY;
    }

    public void setRespuestaNotificacionListener(String idPregunta)
    {
        // Se crea el canal de notificaciones
        createNotificationChannel();
        this.db = new ForoGeneralFirebaseDatabase();

        // Se extrae el nombre del usuario
        this.nombreUsuario = db.obtenerUsuario();

        DatabaseReference ref = db.getRespuestasRef();

        // La referencia funcionará para cuando se añaden nuevas preguntas
        ref.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int temaID = 1;
                int preguntaID = 1;
                PreguntaCard preguntaReferencia = null;
                if (dataSnapshot.getValue() != null) {

                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        temaID = dataSnapshot.child("temaID").getValue(Integer.class);
                        preguntaID = dataSnapshot.child("preguntaID").getValue(Integer.class);
                        preguntaReferencia = getPreguntaReferencia(temaID, preguntaID);
                    }
                }

                Toast.makeText(getApplicationContext(), "RESPUESTA RECIBIDA", Toast.LENGTH_SHORT).show();

                if (preguntaReferencia != null)
                {
                    String notificationMessage = RESPUESTA_MESSAGE + preguntaReferencia.getTexto();

                    // Se crea el intent para la actividad en la app
                    Intent intent = new Intent(getApplicationContext(), ForoGeneralVerRespuestas.class);

                    // Se le agregan los parámetros extra al intent
                    intent.putExtra("preguntaSeleccionada", preguntaReferencia);
                    intent.putExtra("nombreUsuario", nombreUsuario);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_foro)
                        .setContentTitle("ForoGeneral")
                        .setContentText(notificationMessage)
                        .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notificationMessage))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setVibrate(new long[] { 1000, 1000})
                        // Setea el intent que se ejecutará al seleccionar la notificación
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(createID(), builder.build());
                }
            }

//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }
        });
    }

    public int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
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

    // Método que obtiene el Card necesario para que la actividad de visualizar las preguntas se despliegue
    private PreguntaCard getPreguntaReferencia(int temaID, int preguntaID)
    {
        DatabaseReference ref = db.getPreguntasRef();
        ref.child(Integer.toString(temaID)).child(Integer.toString(preguntaID)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombreUsuario = dataSnapshot.child("nombreUsuario").getValue(String.class);
                String texto = dataSnapshot.child("texto").getValue(String.class);
                int contadorLikes = dataSnapshot.child("contadorLikes").getValue(Integer.class);
                int contadorDislikes = dataSnapshot.child("contadorDisLikes").getValue(Integer.class);
                int resuelta = dataSnapshot.child("resuelta").getValue(Integer.class);

                // Se crea la preguntaCard
                preguntaCard = new PreguntaCard(temaID, preguntaID, nombreUsuario, texto, contadorLikes, contadorDislikes, resuelta);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }
        });

        return preguntaCard;
    }
}
