package cr.ac.ucr.ecci.cql.campus20.foro_general;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.TemaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.PreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;

public class RepositorioForoGeneral
{
    private PreguntaDao preguntaDao;
    private TemaDao temaDao;

    public RepositorioForoGeneral(Application application) {
        ForoGeneralDatabase db = ForoGeneralDatabase.getDatabase(application);
        this.preguntaDao = db.preguntaDao();
        this.temaDao = db.temaDao();
    }

    public void borrarTodo()
    {
        ForoGeneralDatabase.databaseWriteExecutor.execute( () ->
                this.temaDao.borrarTodo());
    }

    public boolean insertarTema(Tema tema)
    {
        ForoGeneralDatabase.databaseWriteExecutor.execute( () ->
                this.temaDao.insert(tema));

        return true;
    }

    public boolean insertarPregunta(Pregunta pregunta)
    {
        ForoGeneralDatabase.databaseWriteExecutor.execute( () ->
                this.preguntaDao.insert(pregunta));

        return true;
    }

    public List<Tema> getTemas()
    {
        List<Tema> temas = null;

        Future<List<Tema>> data = ForoGeneralDatabase.databaseWriteExecutor.submit( () ->
                this.temaDao.getTemas());

        try {
            temas = data.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return temas;
    }
}
