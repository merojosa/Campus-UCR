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
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

@RunWith(AndroidJUnit4.class)
public class TemaDaoTest {

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
    private static int TEMA_ID = 1;
    private static int TEMA_PREGUNTA2 = 2;
    private static Tema TEMA_PRUEBA = new Tema(TEMA_ID, "PruebaTema", "Pa ver si funciona", 100, R.drawable.foro1);
    private static Tema TEMA_PRUEBA2 = new Tema(2, "Prueba2", "Pa verificar", 10, R.drawable.foro_calificado);


    /**
     * Prueba que se encarga de probar el método de getTemas del temaDao() y ya que la instancia de base de datos
     * no tiene temas almacenados, debería devolverse vacía
     * @throws InterruptedException que es la excepcion de interrupción
     */
    @Test
    public void getTemasDatasaseVacia() throws InterruptedException {
        // TEST
        List<Tema> temas = LiveDataTestUtil.getValue(this.database.temaDao().getTemas());
        assertTrue("There is a problema with the list",temas.isEmpty());
    }

    /**
     * Prueba que sirve para probar el insert de un tema y el método buscarTemaPorID
     * @throws InterruptedException que es la excepcion de interrupcion
     */
    @Test
    public void insertAndGetTemaPorID() throws InterruptedException {

        // Añadir un nuevo tema
        this.database.temaDao().insert(TEMA_PRUEBA);

        // TEST
        Tema temita = this.database.temaDao().buscarTemaPorID(TEMA_ID);
        // Prueba la comparación entre el título y el tema
        assertTrue(temita.getTitulo().equals(TEMA_PRUEBA.getTitulo()) && temita.getId() == TEMA_ID);
    }

    /**
     * Prueba que sirve para probar el insert de MÚLTIPLES temas y el método que retorna
     * todos los temas almacenados en la tabla en un LiveData<List<Tema>>
     * @throws InterruptedException que es la excepcion de interrupcion
     */
    @Test
    public void insertAndGetAllTemas() throws InterruptedException {

        // Añadir un nuevo tema
        this.database.temaDao().insert(TEMA_PRUEBA);
        this.database.temaDao().insert(TEMA_PRUEBA2);

        // TEST
        List<Tema> temasProbados = LiveDataTestUtil.getValue(this.database.temaDao().getTemas());
        // Prueba que la lista contenga los 2 temas y que el ID del primer tema coincida
        assertTrue(temasProbados.size() == 2 && temasProbados.get(0).getId() == TEMA_PRUEBA.getId());
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

}
