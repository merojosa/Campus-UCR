package cr.ac.ucr.ecci.cql.campus20.foro_general.Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;

import static org.junit.Assert.*;

public class PreguntaUnitTest {

    private Pregunta pruebaPregunta;

    @Before
    public void setUp() {
        pruebaPregunta =  new Pregunta(0, "test", 1, "Pregunta de prueba", 0, 0);
    }

    @Test
    public void preguntaNotNull() {
        assertNotNull(pruebaPregunta);
    }

    // Prueba del constructor y sus getters
    @Test
    public void testConstructorTema() {
        assertEquals(1, pruebaPregunta.getTemaID());
        assertEquals(pruebaPregunta.getNombreUsuario(), "test");
    }

    @After
    public void tearDown() {
        pruebaPregunta = null;
    }
}