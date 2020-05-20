package cr.ac.ucr.ecci.cql.campus20.foro_general;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.PreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.TemaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

@Database(entities = {Tema.class, Pregunta.class}, version = 3, exportSchema = false)
public abstract class ForoGeneralDatabase extends RoomDatabase
{
    private static ForoGeneralDatabase INSTANCE;


    public static ForoGeneralDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ForoGeneralDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ForoGeneralDatabase.class, "ForoGeneral-Database")
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    new PopulateDbAsync(INSTANCE).execute();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public void clearDb() {
        if (INSTANCE != null) {
            new PopulateDbAsync(INSTANCE).execute();
        }
    }

    public abstract TemaDao temaDao();
    public abstract FavoritoDao favoritoDao();


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final TemaDao temaDao;
        private final FavoritoDao favoritoDao;

        public PopulateDbAsync(ForoGeneralDatabase instance) {
            temaDao = instance.temaDao();
            favoritoDao = instance.favoritoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            temaDao.borrarTodo();

            Tema temaUno = new Tema(0, "General", 10);
            Tema temaDos = new Tema(1, "Info", 3);
            Tema temaTres = new Tema(2, "Becas", 5);

            temaDao.insert(temaUno);
            temaDao.insert(temaDos);
            temaDao.insert(temaTres);

            // Inserta un registro en los favoritos
            Favorito fav1 = new Favorito(0);
            favoritoDao.insert(fav1);

            return null;
        }
    }
}
