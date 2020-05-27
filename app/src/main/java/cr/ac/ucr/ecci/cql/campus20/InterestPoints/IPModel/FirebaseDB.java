package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

/**
 * Abstraction over Firebase database, so that the instance is initialized only once in app.
 * */
public class FirebaseDB {

    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private final String INSTANCE_NAME = "secondary";

    public FirebaseDB(Context context){
        this.context = context;
        /*Configured manually because there is another Firebase project used in this app.
        * Later both projects will be joined in the same database.*/
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:57372885444:android:2672635af5d431ff9ea491") // Required for Analytics.
                .setApiKey("AIzaSyDj_D5ifZYRIt7Al6XO8MZzBOS7z417UnM") // Required for Auth.
                .setDatabaseUrl("https://androidtest-e1ca3.firebaseio.com") // Required for RTDB.
                .build();
        boolean hasBeenInitialized = false;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(context);
        FirebaseApp myApp = null;
        for(FirebaseApp app : firebaseApps){
            if(app.getName().equals(INSTANCE_NAME)){
                hasBeenInitialized = true;
            }
        }

        if(!hasBeenInitialized) {
            FirebaseApp.initializeApp(this.context, options, INSTANCE_NAME);
            myApp = FirebaseApp.getInstance(INSTANCE_NAME);
            FirebaseDatabase.getInstance(myApp).setPersistenceEnabled(true);
        }else{
            myApp = FirebaseApp.getInstance(INSTANCE_NAME);
        }

        this.database = FirebaseDatabase.getInstance(myApp);
    }

    /**
     * Inserts entities as GeneralData objects in Firebase Realtime Database.
     * @param table Entity name, or table name like SQL database.
     * @param data POJO entity containing the data to be inserted.
     * */
    public void insert(String table, GeneralData data){
        reference = database.getReference(table);
        reference.child(Integer.toString(data.getId())).setValue(data);
    }

    /**
     * @param path Database path to be referenced, or table like SQL database.
     * @return A reference to the Firebase file indicated in path parameter.
     * */
    public DatabaseReference getReference(String path) {
        return database.getReference(path);
    }
}
