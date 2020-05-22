package cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.PreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.TemaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

public class PreguntaViewModel extends AndroidViewModel {

    // Definición del DAO y de la lista en LiveData
    private PreguntaDao preguntaDao;
    private LiveData<List<Tema>> preguntas;

    public PreguntaViewModel(Application application)
    {
        super(application);
        preguntaDao = ForoGeneralDatabase.getDatabase(application).preguntaDao();
    }

    public void insert(Pregunta pregunta) { preguntaDao.insert(pregunta);}

    public LiveData<List<Pregunta>> getPreguntasTema(int id) { return preguntaDao.getPreguntasTema(id);}
}
