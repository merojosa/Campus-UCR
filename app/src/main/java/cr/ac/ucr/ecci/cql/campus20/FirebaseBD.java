package cr.ac.ucr.ecci.cql.campus20;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import timber.log.Timber;

// Referencia: https://blog.mindorks.com/firebase-login-and-authentication-android-tutorial
public class FirebaseBD implements LoginBD
{
    protected FirebaseDatabase mDatabase;
    private FirebaseAuth auth;

    protected DatabaseReference appInicial;
    private ValueEventListener firebaseListener;

    public FirebaseBD(Context context)
    {
        boolean hasBeenInitialized = false;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(context);
        FirebaseApp myApp = null;
        if(firebaseApps.size() > 0){
            hasBeenInitialized = true;
        }

        if(!hasBeenInitialized) {
            FirebaseApp.initializeApp(context);
            myApp = FirebaseApp.getInstance();
            FirebaseDatabase.getInstance(myApp).setPersistenceEnabled(true);
        }else{
            myApp = FirebaseApp.getInstance();
        }

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance(myApp);
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
        appInicial = mDatabase.getReference("config_usuarios").child(idUsuario).child("app_inicial");
        firebaseListener = new ValueEventListener()
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
        };
        appInicial.addValueEventListener(firebaseListener);
    }

    @Override
    public void detenerAppDefaultAsync()
    {
        appInicial.removeEventListener(firebaseListener);
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

    public DatabaseReference getReference(String path) {
        return mDatabase.getReference(path);
    }

    public DatabaseReference getReference() {
        return mDatabase.getReference();
    }

}
