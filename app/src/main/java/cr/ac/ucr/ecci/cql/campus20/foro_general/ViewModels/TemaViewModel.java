package cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.TemaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

public class TemaViewModel extends AndroidViewModel {

    // Definición del DAO y de la lista en LiveData
    private TemaDao temaDao;
    private LiveData<List<Tema>> mAllTemas;

    public TemaViewModel(Application application)
    {
        super(application);
        // MODIFICAR PARA QUE USE SOLO 1 PUTA BASE DE DATOS
        temaDao = ForoGeneralDatabase.getDatabase(application).temaDao();
        mAllTemas = temaDao.getTemas();
    }

    public LiveData<List<Tema>> getAllTemas() { return mAllTemas; }

    public void insert(Tema tema) { temaDao.insert(tema);}

    public Tema buscarTema(int id) { return temaDao.buscarTemaPorID(id);}

}
