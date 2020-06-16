package cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories.RespuestaRepository;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

public class RespuestaViewModel extends AndroidViewModel {

    // Definición del Repository y de la lista en LiveData
    private RespuestaRepository mRepository;

    public RespuestaViewModel(Application application) {
        super(application);
        mRepository = new RespuestaRepository(application);
    }

    /**
     * Método que retorna las respuestas de una pregunta especifica
     * @param id el id de la pregunta
     * @return una lista de respuestas dentro de un LiveData
     */
    public LiveData<List<Respuesta>> getRespuestasDePregunta(int id) {
        return mRepository.getRespuestasDePregunta(id);
    }

    /**
     * Método que retorna las respuestas de una pregunta especifica
     * @param idPregunta el id de la pregunta
     * @param idTema el id del tema
     * @return una lista de respuestas dentro de un LiveData
     */
    public LiveData<List<Respuesta>> getRespuestasDePreguntaYTema(int idPregunta, int idTema) {
        return mRepository.getRespuestasDePreguntaYTema(idPregunta, idTema);
    }

    /**
     * Método que inserta una respuesta
     * @param respuesta la pregunta a insertar
     */
    public void insert(Respuesta respuesta) {
        mRepository.insert(respuesta);
    }

    /**
     * Método que actualiza los likes de una respuesta
     * @param id el identificador único de la respuesta
     * @param num el número a poner en el contador
     */
    public void updateLikes(int id, int num) {
        mRepository.updateLikes(id, num);
    }

    /**
     * Método que actualiza los Dislikes de una respuesta
     * @param id el identificador único de la respuesta
     * @param num el número a poner en el contador
     */
    public void updateDislikes(int id, int num) {
        mRepository.updateDislikes(id, num);
    }

    /**
     * Método que retorna la respuesta creada por con el texto y nombre de usuario especificado
     * @param texto el texto de la respuesta a buscar
     * @param nombreUsuario el nombre del usuario que creó la respuesta a buscar
     * @return una lista con la respuesta con esas especificaciones dentro de un LiveData
     */
    public LiveData<List<Respuesta>> getIDPorTextoYUsuario(String texto, String nombreUsuario){
        return mRepository.getIDPorTextoYUsuario(texto, nombreUsuario);
    }
}
