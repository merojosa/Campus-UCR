package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import com.google.firebase.database.DatabaseReference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    public void guardarConfigContactoArgs(){
        Config test = Mockito.mock(Config.class);
        DatabaseReference testdb = Mockito.mock(DatabaseReference.class);

        ArgumentCaptor<DatabaseReference> valueCapture = ArgumentCaptor.forClass(DatabaseReference.class);
        doNothing().when(test).guardarConfigContacto(valueCapture.capture());
        test.guardarConfigContacto(testdb);
        assertEquals(testdb, valueCapture.getValue());

    }

    @Test
    public void guardarConfigContacto(){
        Config test = Mockito.mock(Config.class);
        DatabaseReference testdb = Mockito.mock(DatabaseReference.class);
        test.guardarConfigContacto(testdb);
        verify(test, times(1)).guardarConfigContacto(testdb);
    }

    @Test
    public void guardarConfigGrupoArgs(){
        Config test = Mockito.mock(Config.class);
        DatabaseReference testdb = Mockito.mock(DatabaseReference.class);

        ArgumentCaptor<DatabaseReference> valueCapture = ArgumentCaptor.forClass(DatabaseReference.class);
        doNothing().when(test).guardarConfigGrupo(valueCapture.capture());
        test.guardarConfigGrupo(testdb);
        assertEquals(testdb, valueCapture.getValue());

    }

    @Test
    public void guardarConfigGrupo(){
        Config test = Mockito.mock(Config.class);
        DatabaseReference testdb = Mockito.mock(DatabaseReference.class);
        test.guardarConfigGrupo(testdb);
        verify(test, times(1)).guardarConfigGrupo(testdb);
    }

    @Test
    public void validarContato(){
        Config test = Mockito.mock(Config.class);
        test.validarContacto();
        verify(test, times(1)).validarContacto();

    }

    @Test
    public void validarGrupo(){
        Config test = Mockito.mock(Config.class);
        test.validarGrupo();
        verify(test, times(1)).validarGrupo();

    }

    @Test
    public void guardarContactosArgs(){
        Config test = Mockito.mock(Config.class);
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCapture1 = ArgumentCaptor.forClass(String.class);
        doNothing().when(test).guardarContactos(valueCapture.capture(),valueCapture1.capture());
        test.guardarContactos("Berta", "123");
        Assertions.assertEquals("Berta", valueCapture.getValue());
        Assertions.assertEquals("123", valueCapture1.getValue());
    }

    @Test
    public void guardarContactos(){
        Config test = Mockito.mock(Config.class);
        test.guardarContactos("Berta", "123");
        verify(test, times(1)).guardarContactos("Berta", "123");
    }
}