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

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.PreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.RankPreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.RespuestaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.TemaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankPregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

@Database(entities = {Tema.class, Pregunta.class, Favorito.class, RankPregunta.class, Respuesta.class}, version = 10, exportSchema = false)
public abstract class ForoGeneralDatabase extends RoomDatabase
{

    public abstract TemaDao temaDao();
    public abstract FavoritoDao favoritoDao();
    public abstract PreguntaDao preguntaDao();
    public abstract RankPreguntaDao rankPreguntaDao();
    public abstract RespuestaDao respuestaDao();

    private static ForoGeneralDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static ForoGeneralDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ForoGeneralDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ForoGeneralDatabase.class, "ForoGeneral-Database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            databaseWriteExecutor.execute(() -> {
                TemaDao temaDao = INSTANCE.temaDao();
                temaDao.borrarTodo();

                FavoritoDao favoritoDao = INSTANCE.favoritoDao();
                favoritoDao.deleteAll();

                // Creación de temas para insertarlos en la base de datos
                Tema temaUno = new Tema(1, "General", "Lo más nuevo", 10, R.drawable.foro1);
                Tema temaDos = new Tema(2, "Escuelas", "Información sobre distintas escuelas", 3, R.drawable.foro_escuelas);
                Tema temaTres = new Tema(3, "Becas", "Desde como aplicar hasta como gastar", 5, R.drawable.foro_becas);
                Tema temaCuatro = new Tema(4, "Profesores", "Información sobre distintos profesores", 6, R.drawable.foro_profesores);
                Tema temaCinco = new Tema(5, "Residencias", "Más barato que alquilar ...", 15, R.drawable.foro_residencias);
                Tema temaSeis = new Tema(6, "Buses", "Todo sobre el interno, algo sobre los externos", 11, R.drawable.foro_buses);
                Tema temaSiete = new Tema(7, "Mejores calificadas", "Las preguntas más valoradas por la comunidad", 6, R.drawable.foro_calificado);

                temaDao.insert(temaUno);
                temaDao.insert(temaDos);
                temaDao.insert(temaTres);
                temaDao.insert(temaCuatro);
                temaDao.insert(temaCinco);
                temaDao.insert(temaSeis);
                temaDao.insert(temaSiete);

                Favorito fav1 = new Favorito(1, "test");
                favoritoDao.insert(fav1);
            });

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };


//    public void clearDb() {
//        if (INSTANCE != null) {
//            new PopulateDbAsync(INSTANCE).execute();
//        }
//    }
//
//
//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//        private final TemaDao temaDao;
//        private final FavoritoDao favoritoDao;
//
//        public PopulateDbAsync(ForoGeneralDatabase instance) {
//            temaDao = instance.temaDao();
//            favoritoDao = instance.favoritoDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            temaDao.borrarTodo();
//
//            Tema temaUno = new Tema(0, "General", 10);
//            Tema temaDos = new Tema(0, "Info", 3);
//            Tema temaTres = new Tema(0, "Becas", 5);
//
//            temaDao.insert(temaUno);
//            temaDao.insert(temaDos);
//            temaDao.insert(temaTres);
//
//            // Inserta un registro en los favoritos
//            Favorito fav1 = new Favorito(0);
//            favoritoDao.insert(fav1);
//
//            return null;
//        }
//    }
}
