package cr.ac.ucr.ecci.cql.campus20.foro_general.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankPregunta;

import java.util.List;

@Dao
public interface RankPreguntaDao {

    // Insert de RankPregunta
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RankPregunta rankPregunta);

    // Delete de los likes/dislikes en la tabla RankPregunta
    @Query("DELETE FROM Rank_preguntas")
    void deleteAll();

    // Extrae todos las preguntas que tiene liked
    // En un LIVEDATA
    @Query("SELECT * from Rank_preguntas WHERE IsLiked = 1")
    LiveData<List<RankPregunta>> getAllLiked();

    // Extrae todos las preguntas que tiene disliked
    // En un LIVEDATA
    @Query("SELECT * from Rank_preguntas WHERE IsLiked = 0")
    LiveData<List<RankPregunta>> getAllDisliked();

    // Hace update del isLiked de la tabla
    @Query("UPDATE Rank_preguntas SET IsLiked = :isLiked WHERE IdPreg = :id")
    void updateIsLiked(int isLiked, int id);

    // Elimina el like o dislike
    @Query("DELETE FROM Rank_preguntas WHERE IdPreg = :id")
    void deleteRank(int id);

    //Devuelve el estado de isliked de la pregunta con id
    @Query("SELECT isLiked FROM Rank_preguntas where IdPreg = :id")
    int getRank(int id);
}