package cr.ac.ucr.ecci.cql.campus20.foro_general;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;

public class ForoGeneralFirebaseDatabase extends FirebaseBD
{
    private static String ROOT_PATH = "foro_general";
    private static String TEMAS_PATH = "temas";
    private static String FAVORITOS_PATH = "favoritos";
    private static String PREGUNTAS_PATH = "preguntas";

    private DatabaseReference rootReference = null;

    // Constructor
    public ForoGeneralFirebaseDatabase()
    {
        this.rootReference = FirebaseDatabase.getInstance().getReference(ROOT_PATH);
    }

    // Método que devuelve la referencia al child Temas
    public DatabaseReference getTemasRef()
    {
        return this.rootReference.child(TEMAS_PATH);
    }

}