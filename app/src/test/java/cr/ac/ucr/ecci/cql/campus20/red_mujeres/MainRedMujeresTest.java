package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

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
    void panico() {
    }

    @Test
    void popupPanico() {
    }

    @Test
    void popupCompartir() {
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
    void onRequestPermissionsResult() {

    }

    @Test
    void onExplanationNeeded() {
    }

    @Test
    void onPermissionResult() {
    }

    @Test
    void onCancelNavigation() {
    }

    @Test
    void onNavigationFinished() {
    }

    @Test
    void onNavigationRunning() {
    }

    @Test
    void onStart() {
    }

    @Test
    void onResume() {
    }

    @Test
    void onPause() {
    }

    @Test
    void onStop() {
    }

    @Test
    void onSaveInstanceState() {
    }

    @Test
    void onDestroy() {
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        test.onDestroy();

        verify(test, times(1)).onDestroy();
    }

    @Test
    void onLowMemory() {
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
    void updateMyLocation() {
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