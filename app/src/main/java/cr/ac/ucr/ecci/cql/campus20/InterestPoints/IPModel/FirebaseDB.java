package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class FirebaseDB {

    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private final String INSTANCE_NAME = "secondary";

    public FirebaseDB(Context context){
        this.context = context;
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:57372885444:android:2672635af5d431ff9ea491") // Required for Analytics.
                .setApiKey("AIzaSyDj_D5ifZYRIt7Al6XO8MZzBOS7z417UnM") // Required for Auth.
                .setDatabaseUrl("https://androidtest-e1ca3.firebaseio.com") // Required for RTDB.
                .build();
        boolean hasBeenInitialized = false;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(context);
        for(FirebaseApp app : firebaseApps){
            if(app.getName().equals(INSTANCE_NAME)){
                hasBeenInitialized = true;
            }
        }

        if(!hasBeenInitialized) {
            FirebaseApp.initializeApp(this.context, options, INSTANCE_NAME);
        }

        FirebaseApp app = FirebaseApp.getInstance("secondary");
        this.database = FirebaseDatabase.getInstance(app);
    }

    public void insert(String table, GeneralData data){
        reference = database.getReference(table);
        reference.child(Integer.toString(data.getId())).setValue(data);
    }

    public DatabaseReference getReference(String path) {
        return database.getReference(path);
    }
}
