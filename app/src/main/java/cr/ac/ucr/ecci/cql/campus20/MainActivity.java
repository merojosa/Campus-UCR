package cr.ac.ucr.ecci.cql.campus20;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.ValidacionFirebase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.ValidacionLogin;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity
{
    ValidacionLogin validador;

    private EditText correoInput;
    private EditText contrasennaInput;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DEBUGGING, BORRAR AL HACER MERGE A MASTER
        FirebaseAuth.getInstance().signOut();

        validador = new ValidacionFirebase();

        if(validador.autenticado())
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

        if(validador.autenticado() == false)
        {
            Task tareaValidador = validador.validarCredenciales(correo, contrasenna);

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
        startActivity(new Intent(this, MainUcrEats.class));
    }
}