package cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories.FavoritoRepository;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories.RankPreguntaRepository;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankPregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;


public class RankPreguntaViewModel extends AndroidViewModel {

    // Definición del Repository y de la lista en LiveData
    private RankPreguntaRepository mRepository;

    public RankPreguntaViewModel(Application application)
    {
        super(application);

        // MODIFICAR PARA QUE USE SOLO 1 BASE DE DATOS
        mRepository = new RankPreguntaRepository(application);
    }

    public void insert(RankPregunta rankPregunta) { mRepository.insert(rankPregunta);}

    public void update(RankPregunta rankPregunta) {
        mRepository.update(rankPregunta);
    }

    public void delete(RankPregunta rankPregunta) {
        mRepository.delete(rankPregunta);
    }

    public RankPregunta getRank(int id, String nombreUsuario) {
        return mRepository.getRank(id, nombreUsuario);
    }
}
