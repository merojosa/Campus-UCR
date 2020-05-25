package cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.RespuestaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

public class RespuestaRepository {

    private RespuestaDao mRespuestaDao;
    private PreguntaRepository mRepository;

    public RespuestaRepository(Application application) {

        // Se obtiene la base de datos y se obtienen los datos pertinentes
        ForoGeneralDatabase db = ForoGeneralDatabase.getDatabase(application);
        mRespuestaDao = db.respuestaDao();
    }

    /**
     * Insert a la base de datos, para insertar una respuesta dentro de la tabla
     *
     * @param respuesta
     */
    public void insert(Respuesta respuesta) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRespuestaDao.insert(respuesta);
        });
    }

    public LiveData<List<Respuesta>> getRespuestasDePregunta(int id){
        return mRespuestaDao.getRespuestasDePregunta(id);
    }
}
