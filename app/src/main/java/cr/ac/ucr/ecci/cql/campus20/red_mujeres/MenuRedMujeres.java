package cr.ac.ucr.ecci.cql.campus20.red_mujeres;
import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;

public class MenuRedMujeres extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private FireBaseRedMujeres usuario = new FireBaseRedMujeres();
    private String correo = usuario.obtenerCorreoActual();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_red_mujeres);

        mDatabase = FirebaseDatabase.getInstance();
        validarUsuario("1");

    }

    private void popupRegistro(String nombre) {
        // create a dialog with AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuRedMujeres.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Oh-uh " + nombre + "!");
        builder.setMessage("Parece que no has enviado la solicitud de registro para unirte a la Red de Mujeres.");

        String positiveText = "Enviar Solicitud";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // proceso de validacion
                        procesarValidacion();
                    }
                });

        String negativeText = "Cancelar";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // En este momento al cancelar el popup se "devuelve" a la pagina principal de Puntos de Interes
                        // porque no hay pagina principal, no pude volver al login, y es la mas bonita.
                        startActivity(new Intent(MenuRedMujeres.this, InterestPointsActivity.class));
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void validarUsuario(String id) {

        DatabaseReference user = mDatabase.getReference("usuarios_red_mujeres").child(id);
        usuario.autCallback(user, new FirebaseListener() {
            @Override
            // revisa si el usuario ya ha sido validado
            public void exito(DataSnapshot dataSnapshot) {
                if ((boolean) dataSnapshot.child("Validado").getValue()) {
                    // si si continue
                    startActivity(new Intent(MenuRedMujeres.this, MainRedMujeres.class));
                } else {
                    // si no muestre popup
                    String nombre = (String) dataSnapshot.child("Nombre").getValue();
                    popupRegistro(nombre);
                }
            }
            @Override
            public void fallo(DatabaseError databaseError) {}
        });

    }

    public void procesarValidacion() {

        // proceso de validacion temporal
        // pregunta al admin si quiere validar al usuario

        AlertDialog.Builder builder = new AlertDialog.Builder(MenuRedMujeres.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Psss Admin");
        builder.setMessage("Quiere validar a este mae?");

        String positiveText = "Sure why not";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enviarConfirmacion(true);
                        // write 1 en la bd

                        //continua a actividad
                        startActivity(new Intent(MenuRedMujeres.this, MainRedMujeres.class));
                    }
                });

        String negativeText = "Safo safo";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enviarConfirmacion(false);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void enviarConfirmacion(boolean aceptado) {
        Context context = getApplicationContext();
        CharSequence text = correo;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        List<String> toEmailList = Arrays.asList(correo); //Lista de remitentes en caso de que se ocupe enviar a un grupo de correos

        String asunto = "";
        String cuerpo = "";

        if(aceptado) {
            asunto = "Confirmacion Red Mujeres";
            cuerpo = "Su solicitud para unirse a la red de mujeres ha sido aceptada. Ahora puede acceder a grupos de confianza desde la aplicacion CampusUCR.";
        } else {
            asunto = "Respuesta Solicitud Red Mujeres";
            cuerpo = "Lo sentimos, su solicitud para unirse a la red de mujeres ha sido rechazada.";
        }

        try {
            new SendMailTask(MenuRedMujeres.this).execute("campus.virtual.ucr@gmail.com", //remitente
                    "Campus2020", //contraseña remitente
                    toEmailList, //lista de destinatarios
                    asunto, //asunto
                    cuerpo); //mensaje en el cuerpo
        } catch (Exception e) {
            Log.i("Excepcion", e.getMessage());
        }
    }


}
