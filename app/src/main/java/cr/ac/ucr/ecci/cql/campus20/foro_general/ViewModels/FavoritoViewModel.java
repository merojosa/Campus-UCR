package cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;

// ESTA CLASE NO USA NINGUN REPOSITORY PORQUE ESTE NO EXISTE

public class FavoritoViewModel extends AndroidViewModel {

    // Definición del DAO y de la lista en LiveData
    private FavoritoDao favoritoDao;
    private LiveData<List<Favorito>> mAllFavoritos;

    public FavoritoViewModel(Application application)
    {
        super(application);

        // MODIFICAR PARA QUE USE SOLO 1 PUTA BASE DE DATOS
        favoritoDao = ForoGeneralDatabase.getDatabase(application).favoritoDao();
        mAllFavoritos = favoritoDao.getAllFavoritos();
    }

    public LiveData<List<Favorito>> getAllFavoritos() { return mAllFavoritos; }

    public void insert(Favorito favorito) { favoritoDao.insert(favorito);}

    public void deleteOneFavorito(int favoritoID) { favoritoDao.deleteOneFavorito(favoritoID);}
}
