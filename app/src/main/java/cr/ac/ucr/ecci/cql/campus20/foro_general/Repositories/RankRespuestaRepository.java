package cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories;

import android.app.Application;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.RankRespuestaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankRespuesta;

public class RankRespuestaRepository {

    // Se define el Dao y el RankRespuesta
    private RankRespuestaDao mRankRespuestaDao;
    private RankRespuesta mRankRespuesta;

    // Constructor de la clase
    public RankRespuestaRepository(Application application) {
        // Se obtiene la base de datos y se obtienen los datos pertinentes
        ForoGeneralDatabase db = ForoGeneralDatabase.getDatabase(application);
        mRankRespuestaDao = db.rankRespuestaDao();
    }

    /**
     * Insert a la base de datos, para insertar un rankRespuesta dentro de la tabla
     * @param rankRespuesta, objeto
     */
    public void insert(RankRespuesta rankRespuesta) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRankRespuestaDao.insert(rankRespuesta);
        });
    }

    /**
     * Update a la base de datos, para actualizar un rankRespuesta dentro de la tabla
     * @param rankRespuesta, objeto
     */
    public void update(RankRespuesta rankRespuesta) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRankRespuestaDao.update(rankRespuesta);
        });
    }

    /**
     * Delete a la base de datos, para borrar un rankRespuesta de la tabla
     * @param rankRespuesta, objeto
     */
    public void delete(RankRespuesta rankRespuesta) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRankRespuestaDao.delete(rankRespuesta);
        });
    }

    /**
     * Método que devuelve un RankRespuesta de la respuesta
     * @param id, el identificador de la respuesta
     * @param nombreUsuario, nombre del usuario actual
     */
    public RankRespuesta getRank(int id, String nombreUsuario) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRankRespuesta = mRankRespuestaDao.getRank(id,nombreUsuario);
        });
        return mRankRespuesta;
    }
}