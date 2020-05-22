package cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerTemas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;

public class FavoritoRepository {

    private FavoritoDao mFavoritoDao;
    private LiveData<List<Favorito>> mAllFavoritos;

    public FavoritoRepository(Application application) {
        ForoGeneralDatabase db = ForoGeneralDatabase.getDatabase(application);
        mFavoritoDao = db.favoritoDao();
        mAllFavoritos = mFavoritoDao.getAllFavoritos();
    }

    public LiveData<List<Favorito>> getAllFavoritos() {
        return mAllFavoritos;
    }

    public void insert(Favorito favorito) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mFavoritoDao.insert(favorito);
        });
    }

    public void deleteOneFavorito(int favoritoID) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mFavoritoDao.deleteOneFavorito(favoritoID);
        });
    }
}
