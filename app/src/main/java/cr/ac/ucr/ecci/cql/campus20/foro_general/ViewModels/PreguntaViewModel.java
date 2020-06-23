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
     * Método que retorna las preguntas de un tema especifico
     * @param id el id del tema para recuperar sus preguntas
     * @return una lista de preguntas dentro de un LiveData
     */
    public LiveData<List<Pregunta>> getPreguntasTema(int id) { return mRepository.getPreguntasTema(id); }

    /**
     * Método que inserta una pregunta
     * @param pregunta la pregunta a insertar
     */
    public void insert(Pregunta pregunta) { mRepository.insert(pregunta);}

    /**
     * Método que actualiza una pregunta
     * @param pregunta la pregunta a actualizar
     */
    public void update(Pregunta pregunta) { mRepository.update(pregunta);}

    /**
     * Método que actualiza los likes de una pregunta
     * @param id el identificador único de la pregunta
     * @param num el número a poner en el contador
     */
    public void updateLikes(int id, int num) { mRepository.updateLikes(id, num);}

    /**
     * Método que actualiza los Dislikes de una pregunta
     * @param id el identificador único de la pregunta
     * @param num el número a poner en el contador
     */
    public void updateDislikes(int id, int num) { mRepository.updateDislikes(id, num);}

    /**
     * Método que retorna la pregunta creada por con el texto y nombre de usuario especificado
     * @param texto el texto de la pregunta a buscar
     * @param nombreUsuario el nombre del usuario que creó la pregunta a buscar
     * @return una lista con la pregunta con esas especificaciones dentro de un LiveData
     */
    public LiveData<List<Pregunta>> getIDPorTextoYUsuario(String texto, String nombreUsuario){ return mRepository.getIDPorTextoYUsuario(texto, nombreUsuario); }
}
