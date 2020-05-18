package cr.ac.ucr.ecci.cql.campus20;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Referencia: https://blog.mindorks.com/firebase-login-and-authentication-android-tutorial
public class FirebaseBD implements LoginBD
{
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;

    public FirebaseBD()
    {
//      FirebaseAuth.getInstance().signOut();
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public Task iniciarSesion(String correo, String contrasenna)
    {
        return auth.signInWithEmailAndPassword(correo, contrasenna);
    }

    @Override
    public boolean autenticado()
    {
        return auth.getCurrentUser() != null;
    }

    @Override
    public String obtenerCorreoActual()
    {
        if(auth.getCurrentUser() != null)
            return auth.getCurrentUser().getEmail();
        else
            return null;
    }

    @Override
    public void tareaAppDefaultAsync(String idUsuario, FirebaseListener listener)
    {
        DatabaseReference appInicial = mDatabase.getReference("config_usuarios").child(idUsuario).child("app_inicial");

        appInicial.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                listener.exito(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.fallo(databaseError);
            }
        });
    }

    @Override
    public void cerrarSesion()
    {
        auth.signOut();
    }
}
