package cr.ac.ucr.ecci.cql.campus20.red_mujeres;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.LoginBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class MenuRedMujeres extends AppCompatActivity {
    private LoginBD usuario;
    //private String correo = usuario.obtenerCorreoActual();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_red_mujeres);

        if (!validarUsuario()) {
            popupRegistro();
        }

    }

    private void popupRegistro() {
        // create a dialog with AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuRedMujeres.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Oh-uh!");
        builder.setMessage("Parece que no has enviado la solicitud de registro para unirte a la Red de Mujeres.");

        String positiveText = "Enviar Solicitud";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enviarConfirmacion();
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

    private boolean validarUsuario() {
        boolean validado;

        // revisar si solicitud fue aceptada

        // si si
            // validado = true;

        // si no
            // proceso de validacion...

            // si fue aceptado
            // enviarConfirmacion();
            // validado = true;
            // guardar que el usuario fue aceptado

            // sino
            // guardar que el usuario fue rechazado
            validado = false;

        return validado;
    }


    private void enviarConfirmacion() {
        List<String> toEmailList = Arrays.asList("correo@ucr.ac.cr"); //Lista de remitentes en caso de que se ocupe enviar a un grupo de correos
        try {
            new SendMailTask(MenuRedMujeres.this).execute("correo@gmail.com", //remitente
                    "password", //contraseña remitente
                     toEmailList, //lista de destinatarios
                    "Confirmacion Red Mujeres", //asunto
                    "Su solicitud para unirse a la red de mujeres ha sido aceptada. Ahora puede acceder a grupos de confianza desde la aplicacion CampusUCR."); //mensaje en el cuerpo
        } catch (Exception e) {
            Log.i("Excepcion", e.getMessage());
        }
    }
}
