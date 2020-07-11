package cr.ac.ucr.ecci.cql.campus20.foro_general.Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

import static org.junit.Assert.*;

public class RespuestaUnitTest {

    private Respuesta respuesta;

    @Before
    public void setUp() {
        respuesta = new Respuesta(1, "test", "respuesta de prueba", 1, 1, 0, 0, 0.0, 0.0, false);
    }

    @Test
    public void getId() {
        assertEquals(1, respuesta.getId());
    }

    @Test
    public void getNombreUsuario() {
        assertEquals("test", respuesta.getNombreUsuario());
    }

    @Test
    public void getTexto() {
        assertEquals("respuesta de prueba", respuesta.getTexto());
    }

    @Test
    public void getPreguntaID() {
        assertEquals(1, respuesta.getPreguntaID());
    }

    @Test
    public void getTemaID() {
        assertEquals(1, respuesta.getTemaID());
    }

    @Test
    public void getContadorLikes() {
        assertEquals(0, respuesta.getContadorLikes());
    }

    @Test
    public void getContadorDislikes() {
        assertEquals(0, respuesta.getContadorDislikes());
    }

    @Test
    public void getLatitudYLongitud() {
        assertEquals(0.0, respuesta.getLatitud(), 1e-15);
        assertEquals(0.0, respuesta.getLongitud(), 1e-15);
    }

    @Test
    public void isMapaAgregado() {
        assertEquals(false, respuesta.isMapaAgregado());
    }

    @After
    public void tearDown() {
        respuesta = null;
    }
}