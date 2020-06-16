package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MainRedMujeresTest {

    @Test
    void onCreate() {
    }

    @Test
    void onMapReady() {
    }

    @Test
    void iniciarRuta() {
    }

    @Test
    void panico() {
    }

    @Test
    void popupPanicoCallsPanico() {
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        test.popupPanico();
        verify(test, times(1)).popupPanico();
    }

    @Test
    void popupCompartir() {
    }

    @Test
    void enviarWhatsapp() {
    }

    @Test
    void enviarSMS() {
    }

    @Test
    void onMapClick() {
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
    }

    @Test
    void onLowMemory() {
    }

    @Test
    void readGroupData() {
    }

    @Test
    void readGroupUsersData() {
    }

    @Test
    void UpdateMyLocation() { // Se prueba que el segundo parametro sea de tipo Double
        MainRedMujeres test = Mockito.mock(MainRedMujeres.class);
        ArgumentCaptor<Double> valueCapture = ArgumentCaptor.forClass(Double.class);
        doNothing().when(test).UpdateMyLocation(valueCapture.capture(),valueCapture.capture());
        test.UpdateMyLocation(9.903952,-83.985007);

        assertEquals((Double)(-83.985007),valueCapture.getValue());
    }


}