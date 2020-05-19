package cr.ac.ucr.ecci.cql.campus20.red_mujeres;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;




public class MenuRedMujeres extends AppCompatActivity {

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

    private void enviarSolicitud() {
        enviarConfirmacion();
        // recibe correo del usuario ya logeado a la aplicacion
        // mandar solicitud a ese correo
    }

    private boolean validarUsuario() {
        boolean validado;

        // recuperar correo del usuario ya logeado a la aplicacion
        // revisar si la solicitud ya habia sido enviada
        // si si
            // revisar si fue aprobada o rechazada
            validado = false;
        // si no
            // enviarla
        return validado;
    }

    private void enviarConfirmacion() {
        try {
            GMailSender sender = new GMailSender("dessalfaro@gmail.com", "");
            sender.sendMail("This is Subject",
                    "This is Body",
                    "dessalfaro@gmail.com",
                    "dessalfaro@gmail.com");

            Context context = getApplicationContext();
            CharSequence text = "Hello email!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } catch (Exception e) {
            Context context = getApplicationContext();
            CharSequence text = "Hello toast!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
    }
}
