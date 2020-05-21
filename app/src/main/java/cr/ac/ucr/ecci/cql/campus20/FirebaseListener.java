package cr.ac.ucr.ecci.cql.campus20;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

// Para lidiar con el comportamiento asincronico al hacer un llamado a la base de datos.
public interface FirebaseListener
{
    public void exito(DataSnapshot dataSnapshot);
    public void fallo(DatabaseError databaseError);
}
