package cr.ac.ucr.ecci.cql.campus20.foro_general.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankRespuesta;

@Dao
public interface RankRespuestaDao {

    // Insert de RankRespuesta
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RankRespuesta rankRespuesta);

    // Para actualizar un RankRespuesta
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(RankRespuesta rankRespuesta);

    // Para eliminar un RankRespuesta
    @Delete
    void delete(RankRespuesta rankRespuesta);

    // Delete de la tabla RankRespuesta
    @Query("DELETE FROM Rank_respuestas")
    void deleteAll();

    //Devuelve el RankRespuesta de la respuesta con id
    @Query("SELECT * FROM Rank_respuestas where IdResp = :id AND NombreUsuario = :nombreUsuario")
    RankRespuesta getRank(int id, String nombreUsuario);
}