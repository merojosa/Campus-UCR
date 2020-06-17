package cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.RankPreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerTemas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankPregunta;

public class RankPreguntaRepository {

    // Se define el Dao y el RankPregunta
    private RankPreguntaDao mRankPreguntaDao;
    private RankPregunta mRankPregunta;

    // Constructor de la clase
    public RankPreguntaRepository(Application application) {
        // Se obtiene la base de datos y se obtienen los datos pertinentes
        ForoGeneralDatabase db = ForoGeneralDatabase.getDatabase(application);
        mRankPreguntaDao = db.rankPreguntaDao();
    }

    /**
     * Insert a la base de datos, para insertar un rankPregunta dentro de la tabla
     * @param rankPregunta, objeto
     */
    public void insert(RankPregunta rankPregunta) {
        Log.d("ANDY","Insert de Repository con id: %s" + rankPregunta.idPreg);
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRankPreguntaDao.insert(rankPregunta);
        });
    }

    /**
     * Update a la base de datos, para actualizar un rankPregunta dentro de la tabla
     * @param rankPregunta, objeto
     */
    public void update(RankPregunta rankPregunta) {
        Log.d("ANDY","Update de Repository con id: %s" + rankPregunta.idPreg);
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRankPreguntaDao.update(rankPregunta);
        });
    }

    /**
     * Delete a la base de datos, para borrar un rankPregunta de la tabla
     * @param rankPregunta, objeto
     */
    public void delete(RankPregunta rankPregunta) {
        Log.d("ANDY","Delete de Repository con id: %s" + rankPregunta.idPreg);
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRankPreguntaDao.delete(rankPregunta);
        });
    }

    /**
     * Método que devuelve un RankPregunta de la pregunta
     * @param id, el identificador de la pregunta
     * @param nombreUsuario, nombre del usuario actual
     */
    public RankPregunta getRank(int id, String nombreUsuario) {
        Log.d("ANDY","GetRank de Repository con id: " + id);
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRankPregunta = mRankPreguntaDao.getRank(id,nombreUsuario);
        });
        return mRankPregunta;
    }
}
