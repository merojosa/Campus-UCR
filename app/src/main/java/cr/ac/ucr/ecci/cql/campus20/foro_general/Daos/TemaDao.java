package cr.ac.ucr.ecci.cql.campus20.foro_general.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

@Dao
public interface TemaDao
{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Tema tema);

    @Update(onConflict = OnConflictStrategy.ABORT)
    void update(Tema tema);

    @Delete
    void delete(Pregunta pregunta);

    @Query("SELECT * FROM Tema")
    ArrayList<Tema> getTemas(int id);

    @Query("DELETE FROM Tema")
    void borrarTodo();
}

