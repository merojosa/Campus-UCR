package cr.ac.ucr.ecci.cql.campus20.foro_general.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

@Dao
public interface RespuestaDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Respuesta respuesta);

    @Update(onConflict = OnConflictStrategy.ABORT)
    void update(Respuesta respuesta);

    @Delete
    void delete(Respuesta respuesta);

    //recuperar una respuesta especifica
    @Query("SELECT * FROM Respuesta WHERE id = :id")
    Respuesta getRespuestaPorID(int id);


    // Recuperar el ID, basándose por el texto de la pregunta y el nombre del usuario que la escribió
    @Query("SELECT * FROM Respuesta WHERE texto =:texto AND nombreUsuario =:nombreUsuario")
    LiveData<List<Respuesta>> getIDPorTextoYUsuario(String texto, String nombreUsuario);


    //Para recuperar las respuestas asociadas a pregunta
    @Query("SELECT * FROM Respuesta WHERE preguntaID = :preguntaID")
    LiveData<List<Respuesta>> getRespuestasDePregunta(int preguntaID);

    @Query("DELETE FROM Respuesta")
    void borrarTodo();

    @Query("DELETE FROM Respuesta WHERE id = :id")
    void borrarRespuesta(int id);

    // Aumenta los likes
    @Query("UPDATE Respuesta SET cantidad_likes = cantidad_likes + :num WHERE id = :id")
    void updateLikes(int id,int num);

    // Disminuye los likes
    @Query("UPDATE Respuesta SET cantidad_dislikes = cantidad_dislikes + :num WHERE id = :id")
    void updateDislikes(int id,int num);
}
