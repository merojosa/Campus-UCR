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
    private LiveData<List<RankPregunta>> mAllLiked;
    private LiveData<List<RankPregunta>> mAllDisliked;

    public RankPreguntaViewModel(Application application)
    {
        super(application);

        // MODIFICAR PARA QUE USE SOLO 1 BASE DE DATOS
        mRepository = new RankPreguntaRepository(application);
        mAllLiked = mRepository.getAllLiked();
        mAllDisliked = mRepository.getAllDisliked();
    }

    public LiveData<List<RankPregunta>> getmAllLiked() { return mAllLiked; }
    public LiveData<List<RankPregunta>> getmAllDisliked() { return mAllDisliked; }

    public void insert(RankPregunta rankPregunta) { mRepository.insert(rankPregunta);}

    public void updateIsLiked(int isLiked, int id) { mRepository.updateIsLiked(isLiked,id);}

    public void deleteRank(int id) { mRepository.deleteRank(id);}

    public int getRank(int id) { return mRepository.getRank(id);}
}
