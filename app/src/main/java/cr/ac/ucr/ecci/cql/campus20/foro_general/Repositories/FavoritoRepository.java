package cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerTemas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;

public class FavoritoRepository {

    // Se define el Dao y la lista a usar
    private FavoritoDao mFavoritoDao;
    private LiveData<List<Favorito>> mAllFavoritos;

    // Constructor de la clase
    public FavoritoRepository(Application application) {
        // Se obtiene la base de datos y se obtienen los datos pertinentes
        ForoGeneralDatabase db = ForoGeneralDatabase.getDatabase(application);
        mFavoritoDao = db.favoritoDao();
        mAllFavoritos = mFavoritoDao.getAllFavoritos();
    }

    /**
     * Método para obtener todos los temas marcados como favoritos
     * @return mAllFavoritos, que es una lista en LiveData que contiene los temas favoritos
     */
    public LiveData<List<Favorito>> getAllFavoritos() {
        return mAllFavoritos;
    }

    /**
     * Insert a la base de datos, para insertar un tema Favorito dentro de la tabla
     * @param favorito
     */
    public void insert(Favorito favorito) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mFavoritoDao.insert(favorito);
        });
    }

    /**
     * Método que ejecuta el delete de un registro de la tabla Favorito
     * @param favoritoID, el identificador del tema favorito a eliminar
     */
    public void deleteOneFavorito(int favoritoID) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mFavoritoDao.deleteOneFavorito(favoritoID);
        });
    }
}