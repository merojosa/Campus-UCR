package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.red_mujeres.MainRedMujeres;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;


public class MainActivity extends AppCompatActivity
{
    private LoginBD loginBD;
    private DatabaseReference mDatabase;

    private EditText correoInput;
    private EditText contrasennaInput;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DEBUGGING, BORRAR AL HACER MERGE A MASTER
        FirebaseAuth.getInstance().signOut();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        loginBD = new FirebaseBD();

        if(loginBD.autenticado())
            irActividadGuardada();

        Button buttonIniciarSesion = findViewById(R.id.buttonLogin);
        correoInput = findViewById(R.id.editTextCorreo);
        contrasennaInput = findViewById(R.id.editTextContrasenna);

        buttonIniciarSesion.setOnClickListener(v -> procesarDatos());
    }

    public void procesarDatos()
    {
        String correo = correoInput.getText().toString().trim();
        String contrasenna = contrasennaInput.getText().toString();

        if(correo.length() > 0 && contrasenna.length() > 0)
        {
            validarCredenciales(correo, contrasenna);
        }
        else
        {
            mostrarMensajeError();
        }
    }


    public void validarCredenciales(String correo, String contrasenna)
    {

        if(loginBD.autenticado() == false)
        {
            Task tareaValidador = loginBD.validarCredenciales(correo, contrasenna);

            tareaValidador.addOnCompleteListener(this, task ->
            {
                if(task.isSuccessful())
                {
                    irActividadGuardada();
                }
                else
                {
                    mostrarMensajeError();
                }
            });
        }
    }

    public void mostrarMensajeError()
    {
        Toast.makeText(this, "Correo o contraseña inválidos", Toast.LENGTH_LONG).show();
    }

    public void irActividadGuardada()
    {
        // Si no hay actividad guardada, ir a la actividad de configuracion
        String correo = loginBD.obtenerCorreoActual();
        String idUsuario = correo.substring(0, correo.indexOf('@'));

        loginBD.tareaAppDefaultAsync(idUsuario, new FirebaseListener()
        {
            @Override
            public void exito(DataSnapshot dataSnapshot)
            {
                Long appUsuario = (Long) dataSnapshot.getValue();
                irAppPredeterminada(appUsuario.intValue());
            }

            @Override
            public void fallo(DatabaseError databaseError)
            {

            }
        });

        /*
        // No hay actividad guardada, enviar a la configuracion inicial
        Intent intent = new Intent(this, ConfiguracionActivity.class);
        intent.putExtra(ConfiguracionActivity.KEY_CORREO, );
        startActivity(intent);
         */
    }

    public void irAppPredeterminada(int id)
    {
        Intent intent;
        switch (id)
        {
            case 0:
            default:
            {
                intent = new Intent(this, MainUcrEats.class);
                break;
            }
            case 1:
            {
                intent = new Intent(this, MainRedMujeres.class);
                break;
            }

            case 2:
            {
                intent = new Intent(this, MainForoGeneral.class);
                break;
            }
            case 3:
            {
                intent = new Intent(this, InterestPointsActivity.class);
                break;
            }
        }

        startActivity(intent);
    }
}