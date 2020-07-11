package cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories;

import android.app.Application;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.LiveDataTestUtil;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PreguntaRepositoryTest {

    // Para datos
    private ForoGeneralDatabase database;

    private PreguntaRepository repositorio;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // Data set para testing
    private static int TEMA_ID1 = 1;
    private static int TEMA_ID2 = 2;
    private static String USUARIO_PREGUNTA_PRUEBA = "test";
    private static String USUARIO_PREGUNTA_PRUEBA2 = "otro";

    // Pregunta Prueba
    private static String TEXTO_PREGUNTA_PRUEBA = "Pregunta de prueba";
    private static int LIKES_PREGUNTA_PRUEBA = 1;
    private static int DISLIKES_PREGUNTA_PRUEBA = 0;

    // Pregunta Prueba2
    private static String TEXTO_PREGUNTA_PRUEBA2 = "Pregunta de prueba2";
    private static int LIKES_PREGUNTA_PRUEBA2 = 4;
    private static int DISLIKES_PREGUNTA_PRUEBA2 = 18;


    private static Pregunta PREGUNTA_PRUEBA = new Pregunta(0, USUARIO_PREGUNTA_PRUEBA, TEMA_ID1, TEXTO_PREGUNTA_PRUEBA, LIKES_PREGUNTA_PRUEBA, DISLIKES_PREGUNTA_PRUEBA, 0);
    private static Pregunta PREGUNTA_PRUEBA2 = new Pregunta(0, USUARIO_PREGUNTA_PRUEBA2, TEMA_ID2, TEXTO_PREGUNTA_PRUEBA2, LIKES_PREGUNTA_PRUEBA2, DISLIKES_PREGUNTA_PRUEBA2, 0);

    @Before
    public void initRepository() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        // Se crea una instancia de la base de datos DIRECTO EN MEMORIA en lugar de almacenarla en algún
        // archivo en el dispositivo
        this.database = Room.inMemoryDatabaseBuilder(context,
                ForoGeneralDatabase.class)
                .allowMainThreadQueries()
                .build();

        // Se crea el repositorio
        this.repositorio = new PreguntaRepository((Application) context.getApplicationContext());
        // Se debe eliminar la información del repositorio ya que, una vez corrido el test
        // almacenaría las preguntas creadas
        this.repositorio.borrarTodo();
    }


    /**
     * Prueba que se encarga de probar el método del Repository de Pregunta getPreguntasTema() para que devuelva una lista
     * vacía, ya que no se han hecho inserciones al repositorio
     * @throws InterruptedException
     */
    @Test
    public void chequearRepositorioVacio() throws InterruptedException{

        Thread.sleep(10);

        // Se devuelven los favoritos almacenados en la base de datos por medio del Repository
        List<Pregunta> preguntasTema = LiveDataTestUtil.getValue(this.repositorio.getPreguntasTema(TEMA_ID1));

        // Prueba que la lista NO contiene preguntas
        assertTrue("Falla la prueba", preguntasTema.isEmpty());
    }

    /**
     * Prueba que se encarga de insertar, por medio del Repository de Pregunta, dos preguntas
     * y después, usando un método del Repository, devuelve la lista de todas las preguntas de
     * un tema dado.
     * @throws InterruptedException que es la excepcion de interrupcion
     */
    @Test
    public void insertarPreguntaRepositorio() throws InterruptedException{

        Tema temaPrueba = new Tema(TEMA_ID1, "Prueba Tema", "Pruebas Tema 1", 100, R.drawable.foro1);
        Tema temaPrueba2 = new Tema(TEMA_ID2, "Prueba Tema 2", "Pruebas Tema 2", 10, R.drawable.foro_calificado);
        this.database.temaDao().insert(temaPrueba);
        this.database.temaDao().insert(temaPrueba2);

        Thread.sleep(100);

        // Se prueba el insert
        this.repositorio.insert(PREGUNTA_PRUEBA);
        this.repositorio.insert(PREGUNTA_PRUEBA2);

        Thread.sleep(100);

        // TEST
        List<Pregunta> preguntasTema1 = LiveDataTestUtil.getValue(this.repositorio.getPreguntasTema(TEMA_ID1));
        Assert.assertEquals("Falla la prueba de inserción", 1, preguntasTema1.size());
    }

    @After
    public void closeDb() throws Exception {
        this.repositorio = null;
        database.close();
    }
}
