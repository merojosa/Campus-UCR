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

import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;

public class FireBaseRedMujeres {

    private FirebaseDatabase mDatabase;
    public final ArrayList<Map<String,Object>> arr;
    public final ArrayList<Map<String,Object>> userArr;
    public DatabaseReference grupo;
    public DatabaseReference usuarios;

    void setArr(Map<String,Object> map){
        this.arr.add(map);

    }

    public FireBaseRedMujeres(){
        mDatabase = FirebaseDatabase.getInstance();
        arr = new ArrayList<>();
        userArr = new ArrayList<>();
    }

    public void  obtenerGrupoAsync(String nombreGrupo) {
        grupo = mDatabase.getReference("GrupoEj");
        readGroupData(new FirebaseCallBack() {
            @Override
            public void onCallBack(ArrayList<Map<String, Object>> list) {
                Map<String, Object> map = list.get(0);
                ArrayList<Integer> users = (ArrayList<Integer>)map.get("IDusuarios");

                for(int i = 0 ; i< users.size();++i){
                    obtenerUsuarioAsync(""+users.get(i));
                }
            }
        });
        //System.out.println(x.get(0));

    }

    public void  obtenerUsuarioAsync(String id) {
        usuarios = mDatabase.getReference("usuarios_red_mujeres").child(id);
        readUserspData(new FirebaseCallBack() {
            @Override
            public void onCallBack(ArrayList<Map<String, Object>> list) {
                System.out.println(list);
            }
        });
        //System.out.println(x.get(0));

    }


    private  void readGroupData(FirebaseCallBack firebaseCallBack){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,Object> list = (HashMap<String, Object>) dataSnapshot.getValue();
                arr.add(list);
                firebaseCallBack.onCallBack(arr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        grupo.addListenerForSingleValueEvent(valueEventListener);

    }

    private  void readUserspData(FirebaseCallBack firebaseCallBack){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

    private  interface FirebaseCallBack{

        void onCallBack(ArrayList<Map<String,Object>> list);

    }

}
