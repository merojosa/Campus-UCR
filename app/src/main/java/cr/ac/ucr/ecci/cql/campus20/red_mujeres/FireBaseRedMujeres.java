package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;

public class FireBaseRedMujeres {

    private FirebaseAuth auth;
    ArrayList<Map<String, Object>> userArr;
    ArrayList<Map<String, Object>> groupArr;
    public DatabaseReference grupo;
    public DatabaseReference usuarios;
    private FirebaseDatabase mDatabase;

    public FireBaseRedMujeres() {

        auth = FirebaseAuth.getInstance();
        userArr = new ArrayList<>();
        groupArr = new ArrayList<>();
        mDatabase =  FirebaseDatabase.getInstance();
    }

    public void autCallback(DatabaseReference ref, FirebaseListener listener) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.exito(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.fallo(databaseError);
            }
        });

    }

    public String obtenerCorreoActual() {
        if (auth.getCurrentUser() != null)
            return auth.getCurrentUser().getEmail();
        else
            return null;
    }

    //interfaz para trajar datos fuera del contexto del OnChangeData
    public interface FirebaseCallBack {

        void onCallBack(ArrayList<Map<String, Object>> list);

    }

}
