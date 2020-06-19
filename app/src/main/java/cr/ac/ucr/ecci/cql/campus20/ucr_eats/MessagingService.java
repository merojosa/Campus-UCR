package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

//import FirebaseMessagingService;

import android.util.Log;
import android.widget.Toast;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
}
