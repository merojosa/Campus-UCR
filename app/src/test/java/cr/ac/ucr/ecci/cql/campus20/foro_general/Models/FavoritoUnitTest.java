package cr.ac.ucr.ecci.cql.campus20.foro_general.Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

import static org.junit.Assert.*;

public class FavoritoUnitTest {

    private Favorito pruebaFavorito;

    @Before
    public void setUp() {
        pruebaFavorito =  new Favorito(15, "test");
    }

    @Test
    public void favoritoNotNull() {
        assertNotNull(pruebaFavorito);
    }

    // Prueba del constructor y sus getters
    @Test
    public void testConstructorTema() {
        assertEquals(15, pruebaFavorito.getIdTema());
        assertEquals(pruebaFavorito.getNombreUsuario(), "test");
    }

    @After
    public void tearDown() {
        pruebaFavorito = null;
    }
}
