package cr.ac.ucr.ecci.cql.campus20.foro_general.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

@Dao
public interface FavoritoDao {

    // Insert de Favorito
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Favorito favorito);

    // Delete de los temas que están en la tabla favoritos
    @Query("DELETE FROM Favoritos_table")
    void deleteAll();

    // Extrae todos los temas que están en la tabla
    // En un LIVEDATA
    @Query("SELECT * from Favoritos_table")
    LiveData<List<Favorito>> getAllFavoritos();

    // Elimina un tema de la tabla de favoritos
    @Query("DELETE FROM Favoritos_table WHERE IdTema = :id")
    void deleteOneFavorito(int id);

    @Query("SELECT * FROM Tema where id = :id")
    Tema getOne(int id);
}
