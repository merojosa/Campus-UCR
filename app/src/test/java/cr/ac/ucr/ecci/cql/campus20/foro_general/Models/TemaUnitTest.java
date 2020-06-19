package cr.ac.ucr.ecci.cql.campus20.foro_general.Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

import static org.junit.Assert.*;

public class TemaUnitTest {

    private Tema prueba;

    @Before
    public void setUp()
    {
        prueba =  new Tema(15, "PruebaTema", "Pa ver si funciona", 100, R.drawable.foro1);
    }

    @Test
    public void temaNotNull()
    {
        assertNotNull(prueba);
    }

    // Prueba del constructor y sus getters
    @Test
    public void testConstructorTema()
    {
        assertEquals(15, prueba.getId());
        assertEquals(prueba.getTitulo(), "PruebaTema");
        assertEquals(prueba.getDescription(), "Pa ver si funciona");
        assertEquals(prueba.getImagen(), R.drawable.foro1);
    }

    @After
    public void tearDown()
    {
        prueba = null;
    }
}
