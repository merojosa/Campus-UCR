package cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.PreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerTemas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;

public class PreguntaRepository {

    // Se define el Dao y la lista a usar
    private PreguntaDao mPreguntaDao;
    private PreguntaRepository mRepository;

    // Constructor de la clase
    public PreguntaRepository(Application application){
        // Se obtiene la base de datos y se obtienen los datos pertinentes
        ForoGeneralDatabase db = ForoGeneralDatabase.getDatabase(application);
        mPreguntaDao = db.preguntaDao();
    }

    /**
     * Insert a la base de datos, para insertar un tema Favorito dentro de la tabla
     * @param pregunta
     */
    public void insert(Pregunta pregunta) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mPreguntaDao.insert(pregunta);
        });
    }

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
}
