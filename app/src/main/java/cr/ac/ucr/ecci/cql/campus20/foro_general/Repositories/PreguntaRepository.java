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
     * @return una lista con las preguntas pde un tema especifico
     */
    public LiveData<List<Pregunta>> getPreguntasTema(int id){
        return mPreguntaDao.getPreguntasTema(id);
    }
}
