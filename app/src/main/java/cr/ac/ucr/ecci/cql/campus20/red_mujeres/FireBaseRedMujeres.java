package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.annotations.JsonAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;


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

    public void  fetchGroupUsersAsync(String id) {
        usuarios = mDatabase.getReference("usuarios_red_mujeres").child(id);

        readGroupUsersData(new FirebaseCallBack() {
            @Override
            public void onCallBack(ArrayList<Map<String, Object>> list) {
                System.out.println(list);
            }
        });
    }

    public void  fetchGroupAsync(String nombreGrupo) {
        grupo = mDatabase.getReference("Comunidades").child(nombreGrupo);
        readGroupData(new FireBaseRedMujeres.FirebaseCallBack() {
            @Override
            public void onCallBack(ArrayList<Map<String, Object>> list) {
                Map<String, Object> map = list.get(0);
                //Sacamamos del mapa los usarios pertenecientes al grupo
                ArrayList<Integer> users = (ArrayList<Integer>)map.get("IDusuarios");

                for(int i = 0 ; i< users.size();++i){
                    //Recuperamos la informacion de los integrantes del grupo
                    fetchGroupUsersAsync(""+users.get(i));
                }
            }
        });
    }

    //Obtiene el json especifico para la referencia a la base de datos en el nodo del grupo especifcado
    public  void readGroupData(FirebaseCallBack firebaseCallBack){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Json se comporta como un mapa KEY = nombre atributo, Value = valor
                Map<String,Object> list = (HashMap<String, Object>) dataSnapshot.getValue();
                groupArr.add(list);
                firebaseCallBack.onCallBack(groupArr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        grupo.addListenerForSingleValueEvent(valueEventListener);

    }

    //Obtiene el json especifico para la referencia a la base de datos en el nodo del usuario especifcado
    public  void readGroupUsersData(FirebaseCallBack firebaseCallBack){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Json se comporta como un mapa KEY = nombre atributo, Value = valor
                Map<String,Object> list = (HashMap<String, Object>) dataSnapshot.getValue();

                userArr.add(list);
                firebaseCallBack.onCallBack(userArr);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        usuarios.addListenerForSingleValueEvent(valueEventListener);
    }

    //interfaz para trajar datos fuera del contexto del OnChangeData
    public interface FirebaseCallBack {

        void onCallBack(ArrayList<Map<String, Object>> list);

    }

}
