package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;

public class FireBaseRedMujeres {

    private FirebaseDatabase mDatabase;
    public String nombreGrupo;
    public JSONArray usuarios;
    public Map<String, Object> grupo;

    public FireBaseRedMujeres(){
    }

    public Map<String, Object>  obtenerGrupoAsync(String nombreGrupo){
        DatabaseReference grupo = FirebaseDatabase.getInstance().getReference(nombreGrupo);
        ArrayList<Map<String, Object>> map = new ArrayList<>();


        grupo.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Map<String, Object> grupo = (HashMap<String, Object>)dataSnapshot.getValue();
                map.add(grupo);
                new FirebaseListener(){

                    @Override
                    public void exito(DataSnapshot dataSnapshot) {



                    }

                    @Override
                    public void fallo(DatabaseError databaseError) {

                    }

                };

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                new FirebaseListener(){

                    @Override
                    public void exito(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void fallo(DatabaseError databaseError) {

                    }
                };


            }
        });


        return map.get(0);
    }

}
