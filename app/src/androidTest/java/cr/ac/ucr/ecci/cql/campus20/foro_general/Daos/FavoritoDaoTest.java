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
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;

@RunWith(AndroidJUnit4.class)
public class FavoritoDaoTest {

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
    private static Tema TEMA_PRUEBA = new Tema(TEMA_ID, "PruebaTema", "Pa ver si funciona", 100, R.drawable.foro1);
    private static Tema TEMA_PRUEBA2 = new Tema(2, "Prueba2", "Pa verificar", 10, R.drawable.foro_calificado);

    private static Favorito FAVORITO_PRUEBA = new Favorito(TEMA_ID, "test");
    private static Favorito FAVORITO_PRUEBA2 = new Favorito(2, "test");

    /**
     * Prueba que se encarga de probar el método de getAllFavoritosUsuario del favoritoDao() y ya que la instancia de base de datos
     * no tiene temas favoritos almacenados, debería devolverse vacía
     * @throws InterruptedException que es la excepcion de interrupcion
     */
    @Test
    public void getFavoritosDatabaseVacia() throws InterruptedException {
        // TEST
        List<Favorito> temasFavoritos = LiveDataTestUtil.getValue(this.database.favoritoDao().getAllFavoritosUsuario("test"));
        assertTrue("There is a problema with the list", temasFavoritos.isEmpty());
    }

    /**
     * Prueba que sirve para probar el insert de temas favoritos y el método getAllFavoritosUsuario
     * @throws InterruptedException que es la excepcion de interrupcion
     */
    @Test
    public void insertFavoritosAndGetFavoritosporUsuario() throws InterruptedException {

        // Añadir un nuevo tema
        this.database.temaDao().insert(TEMA_PRUEBA);
        this.database.temaDao().insert(TEMA_PRUEBA2);
        // Añadir nuevos temas favoritos
        this.database.favoritoDao().insert(FAVORITO_PRUEBA);
        this.database.favoritoDao().insert(FAVORITO_PRUEBA2);

        // TEST
        List<Favorito> temasFavoritosProbados = LiveDataTestUtil.getValue(this.database.favoritoDao().getAllFavoritosUsuario("test"));
        // Prueba que la lista contenga los 2 temas favoritos y que el ID del primer tema coincida
        assertTrue(temasFavoritosProbados.size() == 2 && temasFavoritosProbados.get(0).getIdTema() == TEMA_PRUEBA.getId());
    }

    /**
     * Prueba que se encarga de probar la inserción y el borrado de 1 tema favorito por medio del
     * método deleteOneFavorito()
     * @throws InterruptedException
     */
    @Test
    public void insertFavoritosAndDeleteOneFavorito() throws InterruptedException{

        // Añadir un nuevo tema
        this.database.temaDao().insert(TEMA_PRUEBA);
        this.database.temaDao().insert(TEMA_PRUEBA2);
        // Añadir nuevos temas favoritos
        this.database.favoritoDao().insert(FAVORITO_PRUEBA);
        this.database.favoritoDao().insert(FAVORITO_PRUEBA2);

        // Se elimina 1 tema favorito de la tabla que contiene los 2 temas
        this.database.favoritoDao().deleteOneFavorito(FAVORITO_PRUEBA.getIdTema(), "test");

        // TEST
        List<Favorito> temasFavoritosProbados = LiveDataTestUtil.getValue(this.database.favoritoDao().getAllFavoritosUsuario("test"));

        // Prueba que la lista contenga el tema favorito restante y que el ID del tema favorito coincida
        assertTrue(temasFavoritosProbados.size() == 1 && temasFavoritosProbados.get(0).getIdTema() == FAVORITO_PRUEBA2.getIdTema());
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }
}
