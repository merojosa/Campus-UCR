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
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Pregunta pregunta);

    @Update(onConflict = OnConflictStrategy.ABORT)
    void update(Pregunta pregunta);

    @Delete
    void delete(Pregunta pregunta);

    @Query("SELECT * FROM Pregunta WHERE id = :id")
    Pregunta getPreguntaPorID(int id);

    @Query("SELECT * FROM Pregunta WHERE temaID = :temaID ORDER BY id")
    LiveData<List<Pregunta>> getPreguntasTema(int temaID);

    @Query("DELETE FROM Pregunta")
    void borrarTodo();

    // Aumenta los likes
    @Query("UPDATE Pregunta SET contadorLikes = contadorLikes + :num WHERE id = :id")
    void updateLikes(int id,int num);

    // Disminuye los likes
    @Query("UPDATE Pregunta SET contadorDisikes = contadorDisikes + :num WHERE id = :id")
    void updateDislikes(int id,int num);
}

