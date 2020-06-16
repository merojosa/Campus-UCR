package cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Objects;

import cr.ac.ucr.ecci.cql.campus20.LiveDataTestUtil;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class FavoritoRepositoryTest {

    // Para datos
    private ForoGeneralDatabase database;

    private FavoritoRepository repositorio;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // Data set para testing
    private static int TEMA_ID = 1;
    private static Tema TEMA_PRUEBA = new Tema(1, "PruebaTema", "Pa ver si funciona", 100, R.drawable.foro1);
    private static Tema TEMA_PRUEBA2 = new Tema(2, "Prueba2", "Pa verificar", 10, R.drawable.foro_calificado);
    private static Tema TEMA_PRUEBA3 = new Tema(3, "Prueba3", "Pruebas raras", 10, R.drawable.foro_calificado);

    private static Favorito FAVORITO_PRUEBA = new Favorito(1, "test");
    private static Favorito FAVORITO_PRUEBA2 = new Favorito(2, "test");
    private static Favorito FAVORITO_PRUEBA3 = new Favorito(3, "test");

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
        this.repositorio = new FavoritoRepository((Application) context.getApplicationContext(), "test");

        // Se borran todos los favoritos
        this.repositorio.deleteOneFavorito(1, "test");
        this.repositorio.deleteOneFavorito(2, "test");
        this.repositorio.deleteOneFavorito(3, "test");

        // Inserción de los temas
        this.database.temaDao().insert(TEMA_PRUEBA);
        this.database.temaDao().insert(TEMA_PRUEBA2);
        this.database.temaDao().insert(TEMA_PRUEBA3);
    }


    /**
     * Prueba que se encarga de probar el método del Repository de Favorito getAllFavoritos() para que devuelva una lista
     * vacía, ya que no se han hecho inserciones al repositorio
     * @throws InterruptedException
     */
    @Test
    public void chequearRepositorioVacio() throws InterruptedException{

        Thread.sleep(10);

        // Se devuelven los favoritos almacenados en la base de datos por medio del Repository
        List<Favorito> temasFavoritosProbados = LiveDataTestUtil.getValue(this.repositorio.getAllFavoritos());

        // Prueba que la lista NO contiene Favoritos
        assertTrue("Falla la prueba", temasFavoritosProbados.isEmpty());
    }

    /**
     * Prueba que se encarga de insertar, por medio del Repository de Favorito, dos Favoritos
     * y después, usando un método del Repository, devuelve la lista de todos los Favoritos del usuario
     * almacenados, llamando por debajo al favoritosDao que usa el Repository
     * @throws InterruptedException que es la excepcion de interrupcion
     */
    @Test
    public void insertarFavoritoRepositorio() throws InterruptedException{

        // Se prueba el insert
        this.repositorio.insert(FAVORITO_PRUEBA);
        this.repositorio.insert(FAVORITO_PRUEBA2);

        Thread.sleep(100);

        // TEST
        List<Favorito> temasFavoritosProbados = LiveDataTestUtil.getValue(this.repositorio.getAllFavoritos());
        Assert.assertEquals("Falla la prueba de inserción", 2, temasFavoritosProbados.size());
    }

    /**
     * Prueba que se encarga de primero realizar dos inserciones por medio del Repository, luego se prueba el
     * método deleteOneFavorito() del Repository para demostrar que sí se logra eliminar, ya que este
     * método llama por debajo al favoritoDao
     * @throws InterruptedException
     */
    @Test
    public void eliminarFavoritoRepositorio() throws InterruptedException {
        // Se hacen 2 inserts
        this.repositorio.insert(FAVORITO_PRUEBA);
        this.repositorio.insert(FAVORITO_PRUEBA2);

        // Se hace la prueba del delete
        this.repositorio.deleteOneFavorito(FAVORITO_PRUEBA2.getIdTema(), "test");

        Thread.sleep(10);

        // TEST
        List<Favorito> temasFavoritosProbados = LiveDataTestUtil.getValue(this.repositorio.getAllFavoritos());

        Assert.assertEquals("Falla la prueba de eliminar", 1, temasFavoritosProbados.size());
    }


    @After
    public void closeDb() throws Exception {
        this.repositorio = null;
        database.close();
    }
}
