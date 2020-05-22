package cr.ac.ucr.ecci.cql.campus20;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

// Referencia: https://blog.mindorks.com/firebase-login-and-authentication-android-tutorial
public class FirebaseBD implements LoginBD
{
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;

    public FirebaseBD()
    {
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

    // Referencia: https://subscription.packtpub.com/book/web_development/9781788624718/1/ch01lvl1sec12/reading-and-writing-to-realtime-database
    @Override
    public void escribirDatos(String path, Object datos)
    {
        DatabaseReference referencia = mDatabase.getReference(path);
        referencia.setValue(datos).addOnFailureListener(e -> Timber.d(e.getLocalizedMessage()));
    }

    @Override
    public boolean autenticadoRM() {
        //Se encarga de recuperar atributo RM_Flag

//        final boolean[]aut = new boolean[1];
//        String user = auth.getCurrentUser().toString();
//
//        DatabaseReference usuario = mDatabase.getReference("config_usuarios").child("pruebacampusucr").child("RM_Flag");
//
//        usuario.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                aut[0] = dataSnapshot.getValue(Boolean.TYPE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        return aut[0];
        return true;
    }

}
