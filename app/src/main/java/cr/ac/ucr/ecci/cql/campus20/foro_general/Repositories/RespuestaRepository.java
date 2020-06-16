package cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.RespuestaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

public class RespuestaRepository {

    // Se define el Dao
    private RespuestaDao mRespuestaDao;
    //private PreguntaRepository mRepository;

    public RespuestaRepository(Application application) {
        // Se obtiene la base de datos y se obtienen los datos pertinentes
        ForoGeneralDatabase db = ForoGeneralDatabase.getDatabase(application);
        mRespuestaDao = db.respuestaDao();
    }

    /**
     * Insert a la base de datos, para insertar una respuesta dentro de la tabla
     *
     * @param respuesta la respuesta a insertar
     */
    public void insert(Respuesta respuesta) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRespuestaDao.insert(respuesta);
        });
    }

    /**
     * Recupera un LiveData (lista que puede cambiar) de respuestas de una pregunta asociado
     * @param id Identificar de la pregunta
     * @return
     */
    public LiveData<List<Respuesta>> getRespuestasDePregunta(int id){
        return mRespuestaDao.getRespuestasDePregunta(id);
    }

    /**
     * Update a la base de datos, para sumar al contador de likes
     * @param id Identificacion de la pregunta
     * @param num cantidad a sumar al contador de likes
     */
    public void updateLikes(int id, int num) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRespuestaDao.updateLikes(id, num);
        });
    }

    /**
     * Update a la base de datos, para sumar al contador de dislikes
     * @param id Identificacion de la pregunta
     * @param num cantidad a sumar al contador de dislikes
     */
    public void updateDislikes(int id, int num) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRespuestaDao.updateDislikes(id, num);
        });
    }

    /**
     * Método para recuperar la respuesta con un texto y un nombre de usuario específico
     * @param texto el texto de la respuesta a buscar
     * @param nombreUsuario el nombre de usuario que creó la respuesta a buscar
     * @return un LiveData que contiene una lista con la respuesta con las especificaciones requeridas
     */
    public LiveData<List<Respuesta>> getIDPorTextoYUsuario(String texto, String nombreUsuario){
        return mRespuestaDao.getIDPorTextoYUsuario(texto, nombreUsuario);
    }
}
