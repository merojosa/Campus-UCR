package cr.ac.ucr.ecci.cql.campus20.foro_general;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.PreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.TemaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

@Database(entities = {Tema.class, Pregunta.class}, version = 3, exportSchema = false)
public abstract class ForoGeneralDatabase extends RoomDatabase
{
    public abstract PreguntaDao preguntaDao();
    public abstract TemaDao temaDao();

    private static ForoGeneralDatabase INSTANCE = null;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ForoGeneralDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (ForoGeneralDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ForoGeneralDatabase.class, "ForoGeneral-database").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
