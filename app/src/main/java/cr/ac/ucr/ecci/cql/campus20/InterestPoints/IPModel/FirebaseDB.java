package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

/**
 * Abstraction over Firebase database, so that the instance is initialized only once in app.
 * */
public class FirebaseDB extends FirebaseBD {

    private static String PATH_PREFIX = "interest_points";

    public FirebaseDB(Context context){
        super(context);
    }

    /**
     * Inserts entities as GeneralData objects in Firebase Realtime Database.
     * @param table Entity name, or table name like SQL database.
     * @param data POJO entity containing the data to be inserted.
     * */
    public void insert(String table, GeneralData data){
        mDatabase.getReference(PATH_PREFIX).child(table).child(Integer.toString(data.getId())).setValue(data);
    }

    /**
     * @param path Database path to be referenced, or table like SQL database.
     * @return A reference to the Firebase file indicated in path parameter.
     * */
    public DatabaseReference getReference(String path) {
        return super.getReference(PATH_PREFIX).child(path);
    }
}
