package cr.ac.ucr.ecci.cql.campus20.foro_general.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankPregunta;

import java.util.List;

@Dao
public interface RankPreguntaDao {

    // Insert de RankPregunta
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RankPregunta rankPregunta);

    // Para actualizar un RankPregunta
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(RankPregunta rankPregunta);

    // Para eliminar un RankPregunta
    @Delete
    void delete(RankPregunta rankPregunta);

    // Delete de la tabla RankPregunta
    @Query("DELETE FROM Rank_preguntas")
    void deleteAll();

    //Devuelve el RankPregunta de la pregunta con id
    @Query("SELECT * FROM Rank_preguntas where IdPreg = :id AND NombreUsuario = :nombreUsuario")
    RankPregunta getRank(int id, String nombreUsuario);
}