package cr.ac.ucr.ecci.cql.campus20.foro_general.Test;

import android.provider.Settings;

import org.junit.Test;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerMisPreguntas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class MisPreguntasForoGeneralTest {
    private final String USUARIO_TEST = "test";
    private final String TEXTO_PREGUNTA = "Cuándo comienza el segundo semestre?";
    private final int ID = 1;
    private final int CONTADOR_LIKES = 0;
    private final int CONTADOR_DISLIKES = 0;
    private final int RESUELTA = 0;
    private final int TEMA_ID = 1;


    /**
     * Prueba que recupera una pregunta específica del usuario test y verifica sus valores con
     * los datos esperados
     */
    @Test
    public void preguntaFireBaseUsuarioTest(){
        ForoGeneralFirebaseDatabase databaseReference = new ForoGeneralFirebaseDatabase();
        databaseReference.getPreguntasRef().child("1").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int id = dataSnapshot.child("id").getValue(Integer.class);
                assert (id == ID);
                String nombreUsuario = dataSnapshot.child("nombreUsuario").getValue(String.class);
                assert (nombreUsuario.equals(USUARIO_TEST));
                int temaID = dataSnapshot.child("temaID").getValue(Integer.class);
                assert (temaID == TEMA_ID);
                String texto = dataSnapshot.child("texto").getValue(String.class);
                assert (texto.equals(TEXTO_PREGUNTA));
                int contadorLikes = dataSnapshot.child("contadorLikes").getValue(Integer.class);
                assert (contadorLikes == CONTADOR_LIKES);
                int contadorDisLikes = dataSnapshot.child("contadorDisLikes").getValue(Integer.class);
                assert (contadorDisLikes == CONTADOR_DISLIKES);
                int resuelta = dataSnapshot.child("resuelta").getValue(Integer.class);
                assert (resuelta == RESUELTA);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
