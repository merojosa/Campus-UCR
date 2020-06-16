package cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.PreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;

public class PreguntaRepository {

    // Se define el Dao
    private PreguntaDao mPreguntaDao;

    // Constructor de la clase
    public PreguntaRepository(Application application){
        // Se obtiene la base de datos y se obtienen los datos pertinentes
        ForoGeneralDatabase db = ForoGeneralDatabase.getDatabase(application);
        mPreguntaDao = db.preguntaDao();
    }

    /**
     * Inserta a la base de datos, para insertar un tema Favorito dentro de la tabla en ejecucion de threads
     * @param pregunta
     */
    public void insert(Pregunta pregunta) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mPreguntaDao.insert(pregunta);
        });
    }

    /**
     * Recupera un LiveData (lista que puede cambiar) de preguntas de un tema asociado
     * @param id el id del tema a recuperar
     * @return una lista con las preguntas pde un tema especifico, asociado a un usuario especifico
     */
    public LiveData<List<Pregunta>> getPreguntasTema(int id){
        return mPreguntaDao.getPreguntasTema(id);
    }

    /**
     * Update a la base de datos, para sumar al contador de likes
     * @param id Identificacion de la pregunta
     * @param num cantidad a sumar al contador de likes
     */
    public void updateLikes(int id, int num) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mPreguntaDao.updateLikes(id, num);
        });
    }

    /**
     * Update a la base de datos, para sumar al contador de dislikes
     * @param id Identificacion de la pregunta
     * @param num cantidad a sumar al contador de dislikes
     */
    public void updateDislikes(int id, int num) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mPreguntaDao.updateDislikes(id, num);
        });
    }

    /**
     * Método para recuperar la pregunta con un texto y un nombre de usuario específico
     * @param texto el texto de la pregunta a buscar
     * @param nombreUsuario el nombre de usuario que creó la pregunta a buscar
     * @return un LiveData que contiene una lista con la pregunta con las especificaciones requeridas
     */
    public LiveData<List<Pregunta>> getIDPorTextoYUsuario(String texto, String nombreUsuario){
        return mPreguntaDao.getIDPorTextoYUsuario(texto, nombreUsuario);
    }

    /**
     * Método que borra lo que se encuentra en la tabla Pregunta
     */
    public void borrarTodo(){
        mPreguntaDao.borrarTodo();
    }
}
