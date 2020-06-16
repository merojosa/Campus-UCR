package cr.ac.ucr.ecci.cql.campus20.foro_general.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;

@Dao
public interface PreguntaDao
{
    // Para insertar una pregunta
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Pregunta pregunta);

    // Para actualizar una pregunta
    @Update(onConflict = OnConflictStrategy.ABORT)
    void update(Pregunta pregunta);

    // Para eliminar una pregunta
    @Delete
    void delete(Pregunta pregunta);

    // Recuperar el ID, basándose por el texto de la pregunta y el nombre del usuario que la escribió
    @Query("SELECT * FROM Pregunta WHERE texto =:texto AND nombreUsuario =:nombreUsuario")
    LiveData<List<Pregunta>> getIDPorTextoYUsuario(String texto, String nombreUsuario);

    // Para recuperar todas las preguntas relacionadas a un tema especifico
    @Query("SELECT * FROM Pregunta WHERE temaID = :temaID ORDER BY id")
    LiveData<List<Pregunta>> getPreguntasTema(int temaID);

    // Para eliminar TODAS las preguntas
    @Query("DELETE FROM Pregunta")
    void borrarTodo();

    // Aumenta los likes
    @Query("UPDATE Pregunta SET contadorLikes = contadorLikes + :num WHERE id = :id")
    void updateLikes(int id,int num);

    // Disminuye los likes
    @Query("UPDATE Pregunta SET contadorDisLikes = contadorDisLikes + :num WHERE id = :id")
    void updateDislikes(int id,int num);
}

