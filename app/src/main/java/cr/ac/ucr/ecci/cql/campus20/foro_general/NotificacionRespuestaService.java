package cr.ac.ucr.ecci.cql.campus20.foro_general;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import cr.ac.ucr.ecci.cql.campus20.R;

public class NotificacionRespuestaService extends Service {

    // Se establece la variable para el canal de notificaciones
    public static String CHANNEL_ID = "Foro_Respuestas";
    public static String RESPUESTA_MESSAGE = "Hay una nueva respuesta a tu pregunta: ";

    public String nombreUsuarioRespuesta = "";
    public String miNombreUsuario = "";

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
        setRespuestaNotificacionListener();
        return START_STICKY;
    }

    /**
     * Método que se encarga de crear el canal para la notificación y estar a la escucha de
     * cualquier respuesta añadida a Firebase, para verificar si una respuesta de interés
     * ha sido posteada
     */
    public void setRespuestaNotificacionListener()
    {
        // Se crea el canal de notificaciones
        createNotificationChannel();
        this.db = new ForoGeneralFirebaseDatabase();

        // Se extrae el nombre del usuario
        this.miNombreUsuario = db.obtenerUsuario();

        DatabaseReference ref = db.getRespuestasRef();

        // La referencia funcionará para cuando se añaden nuevas respuestas
        Query query = ref.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Se crean variables locales para la verificación de la
                // respuesta en cuanto a la pregunta se refiere
                int temaID = 1;
                int id = 0;
                int preguntaID = 1;
                int respuestaNotificada = 100;
                //PreguntaCard preguntaReferencia = null;
                // Chequea que el snapshot no sea nulo
                if (dataSnapshot.getValue() != null) {

                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        // Se extrae cada uno de los datos necesarios
                        id = Objects.requireNonNull(ds.child("id").getValue(Integer.class));
                        temaID = Objects.requireNonNull(ds.child("temaID").getValue(Integer.class));
                        preguntaID = Objects.requireNonNull(ds.child("preguntaID").getValue(Integer.class));
                        respuestaNotificada = Objects.requireNonNull(ds.child("notificada").getValue(Integer.class));
                        nombreUsuarioRespuesta = ds.child("nombreUsuario").getValue(String.class);

                        // Verifica si la respuesta ya fue notificada al usuario de interés
                        if (respuestaNotificada == 0) {
                            getPreguntaReferencia(id, temaID, preguntaID);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * Método que sirve para crear un id único para cada notificación
     * @return id, que es el id único
     */
    public int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }

    /**
     * Método que se encarga de crear el canal de notificaciones, para cualquier versión del
     * SDK que sea Oreo o superior
     */
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

    /**
     * Método que se encarga de obtener la referencia a la pregunta contestada y si se puede, notificarle
     * al usuario apropiado la respuesta que otro usuario posteó en su pregunta.
     * @param idFirebase, que es el id del campo de firebase de referencia de la respuesta, para modificar su estado de notificación
     * @param temaID que es el id del tema al que hace referencia la respuesta
     * @param preguntaID que es el id de la pregunta a la que hace referencia la respuesta
     */
    private void getPreguntaReferencia(int idFirebase, int temaID, int preguntaID)
    {
        DatabaseReference ref = db.getPreguntasRef();
        ref.child(Integer.toString(temaID)).child(Integer.toString(preguntaID)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Extrae el nombre de usuario de la respuesta contestada
                String nombreUsuario = dataSnapshot.child("nombreUsuario").getValue(String.class);
                String texto = dataSnapshot.child("texto").getValue(String.class);
                int contadorLikes = Objects.requireNonNull(dataSnapshot.child("contadorLikes").getValue(Integer.class));
                int contadorDislikes = Objects.requireNonNull(dataSnapshot.child("contadorDisLikes").getValue(Integer.class));
                int resuelta = Objects.requireNonNull(dataSnapshot.child("resuelta").getValue(Integer.class));

                // Chequea si el nombre del usuario dueño de la pregunta contestada soy yo
                // Además chequea si el dueño de la pregunta está loggeado, para enviarle la notificación
                if (nombreUsuario.equals(miNombreUsuario) && (!nombreUsuario.equals(nombreUsuarioRespuesta)))
                {
                    // Solo hasta que el usuario reciba la notificación de la respuesta a su pregunta
                    // se le puede cambiar el estado de la misma a notificada
                    db.getRespuestasRef().child(Objects.requireNonNull(Integer.toString(idFirebase))).child("notificada").setValue(1);

                    // Se crea la preguntaCard
                    preguntaCard = new PreguntaCard(temaID, preguntaID, nombreUsuario, texto, contadorLikes, contadorDislikes, resuelta);

                    String notificationMessage = RESPUESTA_MESSAGE + preguntaCard.getTexto(); // Integer.toString(temaID) + Integer.toString(preguntaID);

                    // Se crea el intent para la actividad en la app
                    Intent intent = new Intent(getApplicationContext(), ForoGeneralVerRespuestas.class);

                    // Se le agregan los parámetros extra al intent
                    intent.putExtra("preguntaSeleccionada", preguntaCard);
                    intent.putExtra("nombreUsuario", nombreUsuario);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    // Se arma la notificación
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }
        });
    }
}
