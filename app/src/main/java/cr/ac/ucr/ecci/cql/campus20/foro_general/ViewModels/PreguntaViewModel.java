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
        mRepository = new PreguntaRepository(application);
    }

    /**
     * Metood que retorna las preguntas de un tema especifico
     * @param id el id del tema para recuperar sus preguntas
     * @return una lista de preguntas dentro de un LiveData
     */
    public LiveData<List<Pregunta>> getPreguntasTema(int id) { return mRepository.getPreguntasTema(id); }

    public void insert(Pregunta pregunta) { mRepository.insert(pregunta);}
}
