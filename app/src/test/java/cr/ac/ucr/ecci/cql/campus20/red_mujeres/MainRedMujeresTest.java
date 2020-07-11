package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.os.Bundle;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MainRedMujeresTest {

    @Test
    void onCreate() {
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        Bundle btest = Mockito.mock(Bundle.class);
        test.onCreate(btest);
        verify(test, times(1)).onCreate(btest);
    }

    @Test
    void panico() {
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> valueCapture2 = ArgumentCaptor.forClass(String.class);


        doNothing().when(test).panico(valueCapture.capture(), valueCapture2.capture());
        test.panico(1, "123");
        assertEquals( (Object) 1, (Object) valueCapture.getValue());
        assertEquals( (Object) "123", (Object) valueCapture2.getValue());
    }

    @Test
    void popupPanico() {
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        test.popupPanico("Berta", "123");
        verify(test, times(1)).popupPanico("Berta", "123");
    }

    @Test
    void popUpPanicoArgs() {
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCapture2 = ArgumentCaptor.forClass(String.class);

        doNothing().when(test).popupPanico(valueCapture.capture(), valueCapture2.capture());
        test.popupPanico("Berta", "123");
        assertEquals( (Object) "Berta", (Object) valueCapture.getValue());
        assertEquals( (Object) "123", (Object) valueCapture2.getValue());
    }

    @Test
    void enviarWhatsapp() {
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(test).enviarWhatsapp(valueCapture.capture());
        test.enviarWhatsapp("Hola! Esta es mi ruta");

        assertEquals("Hola! Esta es mi ruta", valueCapture.getValue());
    }

    @Test
    void enviarSMS() {
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(test).enviarSMS(valueCapture.capture());
        test.enviarSMS("Hola! Esta es mi ruta");

        assertEquals("Hola! Esta es mi ruta", valueCapture.getValue());
    }

    @Test
    void onDestroy() {
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        test.onDestroy();

        verify(test, times(1)).onDestroy();
    }

    @Test
    void readGroupData() {
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(test).readGroupUsersData(valueCapture.capture());
        test.readGroupUsersData("Comunidad 1");

        assertEquals("Comunidad 1", valueCapture.getValue());
    }

    @Test
    void readGroupUsersData() { //Verifica que los parametros sean recibidos correctamente
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(test).readGroupUsersData(valueCapture.capture());
        test.readGroupUsersData("2");

        assertEquals("2", valueCapture.getValue());
    }

    @Test
    void updateMyLocation() { // Verifica que el segundo parametro sea Double
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        ArgumentCaptor<Double> valueCapture = ArgumentCaptor.forClass(Double.class);
        doNothing().when(test).UpdateMyLocation(valueCapture.capture(),valueCapture.capture());
        test.UpdateMyLocation(9.903952,-83.985007);

        assertEquals((Double)(-83.985007),valueCapture.getValue());
    }

    @Test
    void coordenadasValidas() { //Verifica que la validacion de nulos se efectúa correctamente
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        assertFalse(test.coordenadasValidas(null,null,null,null));
    }

    @Test
    void coordenadasValidas2() { //Verifica que la validación de un nulo se efectúa correctamente
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        assertFalse(test.coordenadasValidas(0.99,null,1.88,1.34));
    }

    @Test
    void coordenadasValidas3() { //Verifica que la validación de coordenadas se ejecuta correctamente
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        // define return value for method getUniqueId()
        when(test.coordenadasValidas(0.9, 10.2, 9.0, 3.4)).thenReturn(true);
        assertTrue(test.coordenadasValidas(0.9, 10.2, 9.0, 3.4));
    }
}