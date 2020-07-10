package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import cr.ac.ucr.ecci.cql.campus20.R;

public class CrearComunidad extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private FireBaseRedMujeres bd = new FireBaseRedMujeres();
    private String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_comunidad);

        //Se oculta el ActionBar para "reemplazarlo" por el AppBar definido
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        Intent intent = getIntent();
        usuarioID = intent.getStringExtra("userID");

        // Instancia base de datos
        mDatabase = FirebaseDatabase.getInstance();

        //Se instancian los elementos del layout para recibir datos
        EditText editTextNombre = (EditText) findViewById(R.id.editText_Create_Community_Name);
        EditText editTextDescripcion = (EditText) findViewById(R.id.editText_Create_Community_Description);
        Button buttonCreateCommunityForm = (Button) findViewById(R.id.button_Create_Community_Form);

        buttonCreateCommunityForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Despliegue pop up confirmación de creación de un nuevo grupo
                popUpCrear(getApplicationContext(), editTextNombre.getText().toString(), editTextDescripcion.getText().toString());
            }
        });
    }

    //Popup para la confirmar la creación de la nueva comunidad
    public void popUpCrear(Context context, String nombreGrupo, String descripcionGrupo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CrearComunidad.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Crear nueva comunidad");
        builder.setMessage("¿Desea crear la nueva comunidad " + nombreGrupo + "?");

        String positiveText = "Sí";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        escribirComunidadEnBD(usuarioID, nombreGrupo, descripcionGrupo);
                    }
                });

        String negativeText = "No";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(context, "Negativo" , Toast.LENGTH_SHORT).show();
                    }
                });

        //Creación y despliegue del diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Método para almacenar la nueva comunidad en la base de datos
    public void escribirComunidadEnBD(String usuarioID, String nombreGrupo, String descripcionGrupo) {
        DatabaseReference ref = mDatabase.getReference("Comunidades");

        //Almacenamiento de los detalles del grupo en la base de datos
        ref.child(nombreGrupo).setValue(nombreGrupo);
        ref.child(nombreGrupo).child("Admin").setValue(Integer.parseInt(usuarioID));
        ref.child(nombreGrupo).child("Descripcion").setValue(descripcionGrupo);
        ref.child(nombreGrupo).child("IDusuarios").push().setValue(Integer.parseInt(usuarioID));
        ref.child(nombreGrupo).child("Nombre").setValue(nombreGrupo);

        //Se guarda la nueva comunidad como parte de las que ya pertenece el usuario
        ref = mDatabase.getReference("usuarios_red_mujeres");
        ref.child(usuarioID).child("Grupos").push().setValue(nombreGrupo);

        Toast.makeText(getApplicationContext(), "Comunidad "+ nombreGrupo + " creada" , Toast.LENGTH_SHORT).show();
    }
}