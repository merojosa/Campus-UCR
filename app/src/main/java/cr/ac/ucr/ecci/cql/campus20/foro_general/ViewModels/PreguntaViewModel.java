package cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories.PreguntaRepository;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;


public class PreguntaViewModel extends AndroidViewModel {

    // Definición del Repository y de la lista en LiveData
    private PreguntaRepository mRepository;

    public PreguntaViewModel(Application application)
    {
        super(application);

        // MODIFICAR PARA QUE USE SOLO 1 BASE DE DATOS
        mRepository = new PreguntaRepository(application);
    }

    public LiveData<List<Pregunta>> getPreguntasTema(int id) { return mRepository.getPreguntasTema(id); }

    public void insert(Pregunta pregunta) { mRepository.insert(pregunta);}

    public void updateLikes(int id, int num) { mRepository.updateLikes(id, num);}

    public void updateDislikes(int id, int num) { mRepository.updateDislikes(id, num);}
}
