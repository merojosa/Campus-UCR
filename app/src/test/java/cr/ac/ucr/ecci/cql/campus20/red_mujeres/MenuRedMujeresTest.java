package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MenuRedMujeresTest {

    @Test
    void onCreate() {
    }

    @Test
    void comunidadesUsuario() {
    }

    @Test
    void comunidadesTotales() {
    }

    @Test
    void enviarConfirmacion() {
        MenuRedMujeres test = Mockito.mock(MenuRedMujeres.class);
        assertEquals(null, test.enviarConfirmacion(false));
    }
}