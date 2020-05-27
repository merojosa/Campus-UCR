package cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories.FavoritoRepository;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;


public class FavoritoViewModel extends AndroidViewModel {

    // Definición del Repository y de la lista en LiveData
    private FavoritoRepository mRepository;
    private LiveData<List<Favorito>> mAllFavoritos;

    public FavoritoViewModel(Application application)
    {
        super(application);
        mRepository = new FavoritoRepository(application);
        mAllFavoritos = mRepository.getAllFavoritos();
    }

    public LiveData<List<Favorito>> getAllFavoritos() { return mAllFavoritos; }

    public void insert(Favorito favorito) { mRepository.insert(favorito);}

    public void deleteOneFavorito(int favoritoID) { mRepository.deleteOneFavorito(favoritoID);}

    //public Tema getOne(int favoritoID) { return favoritoDao.getOne(favoritoID);}
}
