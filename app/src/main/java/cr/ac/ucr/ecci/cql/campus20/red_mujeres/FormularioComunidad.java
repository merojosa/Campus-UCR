package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;


public class FormularioComunidad extends AppCompatActivity {

    //Variables usadas para almacenar los parámetros recibidos por intent
    Comunidad comunidad;
    String usuarioID;
    String usuarioNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_comunidad);

        //Se oculta el ActionBar para "reemplezarlo" por el AppBar definido
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        //Se reciben diferentes parámetros a través de Intent
        Intent intent = getIntent();
        usuarioID = intent.getStringExtra("userID");
        usuarioNombre = intent.getStringExtra("userName");
        comunidad = intent.getParcelableExtra("comunidad");

        //Se instancian los elementos del layout
        TextView textCommunityNameForm = (TextView) findViewById(R.id.text_Community_Name_Form);
        EditText editTextJustificacion = (EditText) findViewById(R.id.EditTextJustificacion);
        Button buttonJoinCommunityForm = (Button) findViewById(R.id.button_Join_Community_Form);

        textCommunityNameForm.setText(comunidad.getCommunityName());   //Se asigna el nombre de la comunidad al texto para su despliegue en el layout
        buttonJoinCommunityForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpUnirse(getApplicationContext(), editTextJustificacion.getText().toString()); //Despliegue del mensaje de confirmación para unirse al grupo seleccionado
                    //Enviar el nombre de la comunidad y demas al administrador

            }
        });

    }

    //Método que despliega un popup para determinar si un usuario quiere unirse a una comunidad
    public void popUpUnirse(Context context, String unionMotivo)
    {
        // Creación del diálogo con AlertDialog builder. Primero se hace el constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(FormularioComunidad.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Unirse");
        builder.setMessage("¿Desea unirse a la comunidad " + comunidad.getCommunityName() + "?");

        String positiveText = "Sí";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context, "Afirmativo" , Toast.LENGTH_SHORT).show();
                        //comunidad.Unirse(context, usuarioID, usuarioNombre, unionMotivo);  //Llamado al método que agrega un usuario a una comunidad
                        enviarSolicitudUnion(usuarioID, usuarioNombre, unionMotivo);
                        //Llamado a la actividad que despliega el mapa
//                        startActivity(new Intent(FormularioComunidad.this, MainRedMujeres.class)
//                                .putExtra("usuarioID", usuarioID)
//                                .putExtra("usuarioName", usuarioNombre));
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

    private void enviarSolicitudUnion(String usuarioID, String usuarioNombre, String unionMotivo)
    {
        String asunto = "";
        String cuerpo = "";
        asunto = "Solicitud de unión a comunidad " + comunidad.getCommunityName();
        cuerpo = "Motivo: \n" + unionMotivo;

        List<String> toEmailList = new ArrayList<>();
        toEmailList.add("admredmujeres@gmail.com");

        try {
            new SendMailTask(FormularioComunidad.this).execute("campus.virtual.ucr@gmail.com", //remitente
                    "Campus2020", //contraseña remitente
                    toEmailList, //lista de destinatarios
                    asunto, //asunto
                    cuerpo); //mensaje en el cuerpo
            Toast.makeText(getApplicationContext(), "Solicitud enviada" , Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.i("Excepcion", e.getMessage());
        }
    }
}
