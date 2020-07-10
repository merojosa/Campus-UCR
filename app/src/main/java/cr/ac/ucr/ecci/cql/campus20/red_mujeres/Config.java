package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;
import cr.ac.ucr.ecci.cql.campus20.R;

public class Config extends AppCompatActivity {

    EditText contactoNom, contactoNum;
    private FirebaseDatabase mDatabase;
    private MenuRedMujeres m = new MenuRedMujeres();
    private String currentUser = m.getCurrentUserID();
    private FireBaseRedMujeres bd = new FireBaseRedMujeres();

    private String name = "";
    private String num = "";

    public Config(){
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference("usuarios_red_mujeres");
        validarContacto();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference("usuarios_red_mujeres");

        contactoNom  = (EditText) findViewById(R.id.contactoNom);
        contactoNum  = (EditText) findViewById(R.id.contactoNum);

        validarContacto();

        final Button button = findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = contactoNom.getText().toString();
                String num = contactoNum.getText().toString();
                guardarConfig(name, num, ref);
                finish();
            }
        });
    }

    public void guardarConfig(String name, String num, DatabaseReference ref){
        ref.child(currentUser).child("ContactoConfianza").child("Nombre").setValue(name);
        ref.child(currentUser).child("ContactoConfianza").child("Numero").setValue(num);
        this.name = name;
        this.num = num;
    }


    public void validarContacto(){
        DatabaseReference root = mDatabase.getReference();

        bd.autCallback(root, new FirebaseListener() {
            @Override
            public void exito(DataSnapshot dataSnapshot) {
                String nombre =  (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUser).child("ContactoConfianza").child("Nombre").getValue();
                String numero =  (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUser).child("ContactoConfianza").child("Numero").getValue();
                contactoNom.setText(nombre);
                contactoNum.setText(numero);

            }

            @Override
            public void fallo(DatabaseError databaseError) {
                contactoNom.setText("No Establecido");
                contactoNum.setText("No Establecido");
            }
        });
    }
}