package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

// Referencia: https://blog.mindorks.com/firebase-login-and-authentication-android-tutorial
public class ValidacionFirebase implements ValidacionLogin
{
    private FirebaseAuth auth;

    public ValidacionFirebase()
    {
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public Task validarCredenciales(String correo, String contrasenna)
    {
        return auth.signInWithEmailAndPassword(correo, contrasenna);
    }

    @Override
    public boolean autenticado()
    {
        return auth.getCurrentUser() != null;
    }
}
