package cr.ac.ucr.ecci.cql.campus20.foro_general.Daos;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.LiveDataTestUtil;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

@RunWith(AndroidJUnit4.class)
public class PreguntaDaoTest {

    // Para datos
    private ForoGeneralDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {

        // Se crea una instancia de la base de datos DIRECTO EN MEMORIA en lugar de almacenarla en algún
        // archivo en el dispositivo
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                ForoGeneralDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    // Data set para testing
    // Pregunta Prueba
    private static String USUARIO_PREGUNTA_PRUEBA = "test";
    private static int TEMA_ID_PREGUNTA_PRUEBA = 1;
    private static String TEXTO_PREGUNTA_PRUEBA = "Pregunta de prueba";
    private static int LIKES_PREGUNTA_PRUEBA = 1;
    private static int DISLIKES_PREGUNTA_PRUEBA = 0;

    // Pregunta Prueba2
    private static String USUARIO_PREGUNTA_PRUEBA2 = "otro";
    private static int TEMA_ID_PREGUNTA_PRUEBA2 = 4;
    private static String TEXTO_PREGUNTA_PRUEBA2 = "Pregunta de prueba2";
    private static int LIKES_PREGUNTA_PRUEBA2 = 4;
    private static int DISLIKES_PREGUNTA_PRUEBA2 = 18;

    private static Pregunta PREGUNTA_PRUEBA = new Pregunta(0, USUARIO_PREGUNTA_PRUEBA, TEMA_ID_PREGUNTA_PRUEBA, TEXTO_PREGUNTA_PRUEBA, LIKES_PREGUNTA_PRUEBA, DISLIKES_PREGUNTA_PRUEBA);
    private static Pregunta PREGUNTA_PRUEBA2 = new Pregunta(0, USUARIO_PREGUNTA_PRUEBA2, TEMA_ID_PREGUNTA_PRUEBA2, TEXTO_PREGUNTA_PRUEBA2, LIKES_PREGUNTA_PRUEBA2, DISLIKES_PREGUNTA_PRUEBA2);


    /**
     * Prueba que se encarga de probar el método de getPreguntas del PreguntaDao y ya que la instancia de base de datos
     * no tiene temas almacenados, debería devolverse vacía
     * @throws InterruptedException que es la excepcion de interrupción
     */
    @Test
    public void getTemasDatasaseVacia() throws InterruptedException {
        // TEST
        List<Pregunta> preguntas = LiveDataTestUtil.getValue(this.database.preguntaDao().getPreguntasTema(TEMA_ID_PREGUNTA_PRUEBA));
        assertTrue("There is a problema with the list", preguntas.isEmpty());

    }

    /**
     * Prueba que sirve para insert de una pregunta y el método getPreguntasTema que devuelve una lista de las preguntas
     * con un id dado.
     * @throws InterruptedException que es la excepcion de interrupcion
     */
    @Test
    public void insertAndGetPreguntasTema() throws InterruptedException {

        // Insertar temas con los IDs de prueba
        Tema temaPregunta1 = new Tema(TEMA_ID_PREGUNTA_PRUEBA, "Tema Pregunta 1", "Tema Pregunta 1", 100, R.drawable.foro1);
        Tema temaPregunta2 = new Tema(TEMA_ID_PREGUNTA_PRUEBA2, "Tema Pregunta 2", "Tema Pregunta 2", 2, R.drawable.foro1);
        this.database.temaDao().insert(temaPregunta1);
        this.database.temaDao().insert(temaPregunta2);

        // Insertar preguntas
        this.database.preguntaDao().insert(PREGUNTA_PRUEBA);
        this.database.preguntaDao().insert(PREGUNTA_PRUEBA2);

        // TEST
        // Deberia retornar una lista con solamente una pregunta
        List<Pregunta> preguntas = LiveDataTestUtil.getValue(this.database.preguntaDao().getPreguntasTema(TEMA_ID_PREGUNTA_PRUEBA));
        // Prueba la comparación
        assertTrue(preguntas.get(0).getNombreUsuario().equals(PREGUNTA_PRUEBA.getNombreUsuario()) && preguntas.get(0).getTexto().equals(TEXTO_PREGUNTA_PRUEBA));

        // TEST
        // Deberia retornar una lista con solamente una pregunta
        List<Pregunta> preguntas2 = LiveDataTestUtil.getValue(this.database.preguntaDao().getPreguntasTema(TEMA_ID_PREGUNTA_PRUEBA2));
        // Prueba la comparación entre el título y el tema
        assertTrue(preguntas2.get(0).getNombreUsuario().equals(PREGUNTA_PRUEBA2.getNombreUsuario()) && preguntas2.get(0).getTexto().equals(TEXTO_PREGUNTA_PRUEBA2));
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }
}
