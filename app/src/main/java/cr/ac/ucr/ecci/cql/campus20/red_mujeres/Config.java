package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;
import cr.ac.ucr.ecci.cql.campus20.R;

public class Config extends AppCompatActivity implements ExampleDialog.ExampleDialogListener{

    EditText contactoNom, contactoNum;
    private FirebaseDatabase mDatabase;
    private MenuRedMujeres m = new MenuRedMujeres();
    private String currentUser = m.getCurrentUserID();
    private FireBaseRedMujeres bd = new FireBaseRedMujeres();

    private String name = "";
    private String num = "";

    private TextView textViewName;
    private TextView textViewNum;

    private String id2Edit;

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

    public void onClick(View v) {
        String tag = "Contacto #";
        switch (v.getId()) {
            case R.id.editar1:
                tag = tag + "1";
                id2Edit = "1";
                break;
            case R.id.editar2:
                tag = tag + "2";
                id2Edit = "2";
                break;
            case R.id.editar3:
                tag = tag + "3";
                id2Edit = "3";
                break;
        }
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), tag);
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

    @Override
    public void guardarContactos(String name, String num) {

        String name2Set = "name"+id2Edit;
        String num2Set = "num"+id2Edit;

        String packageName = getPackageName();
        int nameId = getResources().getIdentifier(name2Set, "id", packageName);
        int numId = getResources().getIdentifier(num2Set, "id", packageName);

        textViewName = (TextView) findViewById(nameId);
        textViewNum = (TextView) findViewById(numId);

        textViewName.setText(name);
        textViewNum.setText(num);
    }
}