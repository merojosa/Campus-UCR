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

    public  interface FirebaseCallBack{

        void onCallBack(ArrayList<Map<String,Object>> list);

    }

}
