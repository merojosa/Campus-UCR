package cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.RankPreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerTemas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankPregunta;

public class RankPreguntaRepository {

    // Se define el Dao y las listas a usar
    private RankPreguntaDao mRankPreguntaDao;
    private LiveData<List<RankPregunta>> mAllLikedRankedPreg;
    private LiveData<List<RankPregunta>> mAllDislikedRankedPreg;
    private int mListRank;

    /**
     * Método para obtener todos las preguntas con like
     * @return mAllLikedRankedPreg, que es una lista en LiveData que contiene las preguntas con like
     */

    // Constructor de la clase
    public RankPreguntaRepository(Application application) {
        // Se obtiene la base de datos y se obtienen los datos pertinentes
        ForoGeneralDatabase db = ForoGeneralDatabase.getDatabase(application);
        mRankPreguntaDao = db.rankPreguntaDao();
        mAllLikedRankedPreg = mRankPreguntaDao.getAllLiked();
        mAllDislikedRankedPreg = mRankPreguntaDao.getAllDisliked();
    }
    public LiveData<List<RankPregunta>> getAllLiked() {
        return mAllLikedRankedPreg;
    }

    /**
     * Método para obtener todos las preguntas con dislike
     * @return mAllDislikedRankedPreg, que es una lista en LiveData que contiene las preguntas con dislike
     */
    public LiveData<List<RankPregunta>> getAllDisliked() { return mAllDislikedRankedPreg; }

    /**
     * Insert a la base de datos, para insertar un rankPregunta dentro de la tabla
     * @param rankPregunta
     */
    public void insert(RankPregunta rankPregunta) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRankPreguntaDao.insert(rankPregunta);
        });
    }

    /**
     * Método que ejecuta el update de un registro de la tabla RankPregunta
     * @param isLiked, el identificador del status de liked
     * @param id, el identificador de la pregunta
     */
    public void updateIsLiked(int isLiked, int id) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRankPreguntaDao.updateIsLiked(isLiked, id);
        });
    }

    /**
     * Método que ejecuta el delete del like de la pregunta
     * @param id, el identificador de la pregunta
     */
    public void deleteRank(int id) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mRankPreguntaDao.deleteRank(id);
        });
    }

    /**
     * Método que devuelve el like de la pregunta
     * @param id, el identificador de la pregunta
     */
    public int getRank(int id) {
        ForoGeneralDatabase.databaseWriteExecutor.execute(() -> {
            mListRank = mRankPreguntaDao.getRank(id);
        });
        return mListRank;
    }
}
