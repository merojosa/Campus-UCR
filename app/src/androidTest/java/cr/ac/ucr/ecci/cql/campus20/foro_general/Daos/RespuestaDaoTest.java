package cr.ac.ucr.ecci.cql.campus20.foro_general.Daos;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

import cr.ac.ucr.ecci.cql.campus20.LiveDataTestUtil;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

@RunWith(AndroidJUnit4.class)
public class RespuestaDaoTest {
    private ForoGeneralDatabase db;
    private static String USUARIO_TEST = "test";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {

        // Se crea una instancia de la base de datos DIRECTO EN MEMORIA en lugar de almacenarla en algún
        // archivo en el dispositivo
        this.db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                ForoGeneralDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    //Datos a verificar
    private static int TEMA_ID = 1;
    private static Tema TEMA_PRUEBA = new Tema(TEMA_ID, "PruebaTema", "Para ver si funciona", 100, R.drawable.foro1);

    private static Pregunta PREGUNTA_PRUEBA = new Pregunta(1, USUARIO_TEST, TEMA_ID, "pregunta de prueba", 0, 0);
    private static int PREGUNTA_ID = PREGUNTA_PRUEBA.id;

    private static Respuesta RESPUESTA_PRUEBA_1 = new Respuesta(1, USUARIO_TEST, "respuesta de prueba", PREGUNTA_ID, TEMA_ID, 0, 0);
    private static int RESPUESTA_ID_1 = RESPUESTA_PRUEBA_1.id;

    private static Respuesta RESPUESTA_PRUEBA_2 = new Respuesta(1, USUARIO_TEST, "otra respuesta de prueba", PREGUNTA_ID, TEMA_ID, 1, 1);
    private static int RESPUESTA_ID_2 = RESPUESTA_PRUEBA_2.id;


    /**
     * Probar el método getRespuestasDePregunta del dao de Respuesta.
     * la instancia de la base de datos está vacia por lo que debe devolverse una lista nula
     **/
    @Test
    public void getRespuestasDePreguntaDBVacia() throws InterruptedException {
        List<Respuesta> listaRespuestas = LiveDataTestUtil.getValue(this.db.respuestaDao().getRespuestasDePregunta(PREGUNTA_ID));
        assertTrue("lista esta vacia porque no hay preguntas y por lo tanto no hay respuestas", listaRespuestas.isEmpty());
    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }
}
