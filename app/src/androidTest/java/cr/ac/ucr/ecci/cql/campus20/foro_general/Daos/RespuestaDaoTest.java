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

    private static Pregunta PREGUNTA_PRUEBA = new Pregunta(1, USUARIO_TEST, TEMA_ID, "pregunta de prueba", 0, 0, 0);
    private static int PREGUNTA_ID = PREGUNTA_PRUEBA.id;

    private static Respuesta RESPUESTA_PRUEBA_1 = new Respuesta(1, USUARIO_TEST, "respuesta de prueba", PREGUNTA_ID, TEMA_ID, 0, 0, 0.0, 0.0, false);
    private static int RESPUESTA_ID_1 = RESPUESTA_PRUEBA_1.getId();

    private static Respuesta RESPUESTA_PRUEBA_2 = new Respuesta(2, USUARIO_TEST, "otra respuesta de prueba", PREGUNTA_ID, TEMA_ID, 1, 1, 0.0, 0.0, false);
    private static int RESPUESTA_ID_2 = RESPUESTA_PRUEBA_2.getId();


    /**
     * Probar el método getRespuestasDePregunta del dao de Respuesta.
     * la instancia de la base de datos está vacia por lo que debe devolverse una lista nula
     *
     * @throws InterruptedException
     */
    @Test
    public void getRespuestasDePreguntaDBVacia() throws InterruptedException {
        List<Respuesta> listaRespuestas = LiveDataTestUtil.getValue(this.db.respuestaDao().getRespuestasDePregunta(PREGUNTA_ID));
        assertTrue("lista esta vacia porque no hay preguntas y por lo tanto no hay respuestas", listaRespuestas.isEmpty());
    }

    /**
     * Para probar la inserción de un tema y una pregunta con su respectiva respuesta
     * y luego consultarla a partir de su ID mediante getRespuestaPorID
     */
    @Test
    public void insertaLuegoGetDeRespuesta() {
        this.db.temaDao().insert(TEMA_PRUEBA);
        this.db.preguntaDao().insert(PREGUNTA_PRUEBA);
        this.db.respuestaDao().insert(RESPUESTA_PRUEBA_1);

        //TEST
        Respuesta respuestaPrueba = db.respuestaDao().getRespuestaPorID(RESPUESTA_ID_1);

        assertEquals(respuestaPrueba.getId(), RESPUESTA_PRUEBA_1.getId());
        assertTrue(respuestaPrueba.getTexto().equals(RESPUESTA_PRUEBA_1.getTexto()));

    }

    /**
     * Inserción de varias respuestas para luego recuperarlas todas las asociadas a pregunta
     * mediante el método getRespuestasDePregunta
     *
     * @throws InterruptedException
     */
    @Test
    public void insertarGetAllRespuestas() throws InterruptedException {
        this.db.temaDao().insert(TEMA_PRUEBA);
        this.db.preguntaDao().insert(PREGUNTA_PRUEBA);
        this.db.respuestaDao().insert(RESPUESTA_PRUEBA_1);
        this.db.respuestaDao().insert(RESPUESTA_PRUEBA_2);

        //TEST
        List<Respuesta> listaRespuestas = LiveDataTestUtil.getValue(this.db.respuestaDao().getRespuestasDePregunta(PREGUNTA_ID));

        assertTrue("Deben de existir 2 elementos en la lista", listaRespuestas.size() == 2);

        assertTrue("Id de respuesta 1 no coincide", listaRespuestas.get(0).getId() == RESPUESTA_ID_1);
    }

    /**
     * Probar que se devuelve una respuesta asociada a una pregunta y un tema particulares
     * mediante el método getRespuestasDePreguntaYTema
     *
     * @throws InterruptedException
     */
    @Test
    public void pruebaGetRespuestasDePreguntaYTema() throws InterruptedException {
        this.db.temaDao().insert(TEMA_PRUEBA);
        this.db.preguntaDao().insert(PREGUNTA_PRUEBA);
        this.db.respuestaDao().insert(RESPUESTA_PRUEBA_1);
        this.db.respuestaDao().insert(RESPUESTA_PRUEBA_2);

        //TEST
        List<Respuesta> respuestas = LiveDataTestUtil.getValue(db.respuestaDao().getRespuestasDePreguntaYTema(PREGUNTA_ID, TEMA_ID));
        assertTrue("Deben de existir 2 elementos en la lista", respuestas.size() == 2);
    }

    /**
     * Insertar una pregunta, tema y respuesta asociadas y probar que se encuentren
     * las que contengan el texto indicado y sean del usuario que se indica
     * mediante el método getIDPorTextoYUsuario
     *
     * @throws InterruptedException
     */
    @Test
    public void pruebaGetRespuestaPorTextoYUsuario() throws InterruptedException {
        this.db.temaDao().insert(TEMA_PRUEBA);
        this.db.preguntaDao().insert(PREGUNTA_PRUEBA);
        this.db.respuestaDao().insert(RESPUESTA_PRUEBA_1);

        //TEST
        List<Respuesta> listaRespuesta = LiveDataTestUtil.getValue(this.db.respuestaDao().getIDPorTextoYUsuario("respuesta de prueba", USUARIO_TEST));

        assertEquals("Debe haber 1 solo elemento", 1, listaRespuesta.size());

    }

    /**
     * Se crean respuestas a una pregunta y se intentan borrar todas
     * usando el método borrarTodo
     */
    @Test
    public void pruebaBorrarTodoRespuestas() throws InterruptedException {
        this.db.temaDao().insert(TEMA_PRUEBA);
        this.db.preguntaDao().insert(PREGUNTA_PRUEBA);
        this.db.respuestaDao().insert(RESPUESTA_PRUEBA_1);
        this.db.respuestaDao().insert(RESPUESTA_PRUEBA_2);

        //Test
        List<Respuesta> listaRespuestas = LiveDataTestUtil.getValue(this.db.respuestaDao().getRespuestasDePregunta(PREGUNTA_ID));
        assertTrue("Deben de existir 2 elementos en la lista", listaRespuestas.size() == 2);

        db.respuestaDao().borrarTodo();
        //se vuelve a consultar
        listaRespuestas = LiveDataTestUtil.getValue(this.db.respuestaDao().getRespuestasDePregunta(PREGUNTA_ID));
        assertTrue("lista no está vacia", listaRespuestas.isEmpty());

    }

    /**
     * Se prueban los métodos updateLikes y updateDislikes
     * para mostrar que se actualiza el valor de los contadores
     */
    @Test
    public void pruebaUpdateLikesAndDislikes() {
        this.db.temaDao().insert(TEMA_PRUEBA);
        this.db.preguntaDao().insert(PREGUNTA_PRUEBA);
        this.db.respuestaDao().insert(RESPUESTA_PRUEBA_1);

        //TEST
        //Originalmente la respuesta 1 se crea con 0 dislikes y 0 likes
        Respuesta respuestaPrueba = db.respuestaDao().getRespuestaPorID(RESPUESTA_ID_1);

        int cantidadLikes = respuestaPrueba.getContadorLikes();
        int cantidadDislikes = respuestaPrueba.getContadorDislikes();

        assertEquals("Hay 0 likes", 0, cantidadLikes);
        assertEquals("Hay 0 dislikes", 0, cantidadDislikes);

        //Se actualizan valores
        db.respuestaDao().updateLikes(RESPUESTA_ID_1, 5);
        db.respuestaDao().updateDislikes(RESPUESTA_ID_1, 2);

        //Se consulta de nuevo por la respuesta
        respuestaPrueba = db.respuestaDao().getRespuestaPorID(RESPUESTA_ID_1);
        cantidadLikes = respuestaPrueba.getContadorLikes();
        cantidadDislikes = respuestaPrueba.getContadorDislikes();

        assertEquals("Hay 5 likes", 5, cantidadLikes);
        assertEquals("Hay 2 dislikes", 2, cantidadDislikes);
    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }
}
