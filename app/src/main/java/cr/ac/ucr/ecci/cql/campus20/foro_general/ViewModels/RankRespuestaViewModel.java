package cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories.RankRespuestaRepository;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankRespuesta;


public class RankRespuestaViewModel extends AndroidViewModel {

    // Definición del Repository y de la lista en LiveData
    private RankRespuestaRepository mRepository;

    public RankRespuestaViewModel(Application application)
    {
        super(application);

        // MODIFICAR PARA QUE USE SOLO 1 BASE DE DATOS
        mRepository = new RankRespuestaRepository(application);
    }

    public void insert(RankRespuesta rankRespuesta) { mRepository.insert(rankRespuesta);}

    public void update(RankRespuesta rankRespuesta) {
        mRepository.update(rankRespuesta);
    }

    public void delete(RankRespuesta rankRespuesta) {
        mRepository.delete(rankRespuesta);
    }

    public RankRespuesta getRank(int id, String nombreUsuario) {
        return mRepository.getRank(id, nombreUsuario);
    }
}