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
    public void testConstructorFavorito() {
        assertEquals(15, pruebaFavorito.getIdTema());
        assertEquals(pruebaFavorito.getNombreUsuario(), "test");
    }

    // Prueba de los setters del constructor
    @Test
    public void testSetFavorito() {
        // Se hace prueba del setNombreUsuario
        pruebaFavorito.setNombreUsuario("test1");
        assertEquals(pruebaFavorito.getNombreUsuario(), "test1");
        pruebaFavorito.setNombreUsuario("test");
    }

    @Test
    public void testSetIDFavorito() {
        // Se hace prueba del setIDFavorito
        pruebaFavorito.setIdTema(123);
        assertEquals(pruebaFavorito.getIdTema(), 123);
        pruebaFavorito.setIdTema(15);
    }

    @After
    public void tearDown() {
        pruebaFavorito = null;
    }
}
