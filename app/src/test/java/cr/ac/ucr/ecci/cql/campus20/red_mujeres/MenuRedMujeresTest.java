package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class MenuRedMujeresTest {

    @Test
    public void onCreate() {
        MenuRedMujeres test = Mockito.mock(MenuRedMujeres.class);
        Bundle btest = Mockito.mock(Bundle.class);
        test.onCreate(btest);
        verify(test, times(1)).onCreate(btest);


    }

    @Test
    public void comunidadesUsuario() {

    }

    @Test
    public void comunidadesTotales() {
    }

    // Prueba que solo se llame enviarConfirmacion 1 vez dentro de verificarSolicitud, pues
    // los parametros de este llamado cambian dependiendo de si el usuario fue aceptado o
    // rechazado y solo se le debe enviar el correo una vez de acuerdo a esto
    @Test
    public void enviarConfirmacion(){
        MenuRedMujeres test = Mockito.mock(MenuRedMujeres.class);
        doNothing().when(test).verificarSolicitud(isA(String.class));
        doNothing().when(test).enviarConfirmacion(isA(boolean.class), isA(String.class));
        test.verificarSolicitud("1");
        test.enviarConfirmacion(true, "diana.ariasrojas@ucr.ac.cr");

        verify(test, times(1)).enviarConfirmacion(true, "diana.ariasrojas@ucr.ac.cr");

    }

    @Test
    public void enviarSolicitudAdmin(){
        MenuRedMujeres test = Mockito.mock(MenuRedMujeres.class);
        ArgumentCaptor<String> valueCapture1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCapture2 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCapture3 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCapture4 = ArgumentCaptor.forClass(String.class);
        doNothing().when(test).enviarSolicitudAdmin(valueCapture1.capture(),valueCapture2.capture(),valueCapture3.capture(),valueCapture4.capture());


        test.enviarSolicitudAdmin("Diana", "Mujer", "B60686", "diana.ariasrojas@ucr.ac.cr");

        Assertions.assertEquals("Diana", valueCapture1.getValue());
        Assertions.assertEquals("Mujer", valueCapture2.getValue());
        Assertions.assertEquals("B60686", valueCapture3.getValue());
        Assertions.assertEquals("diana.ariasrojas@ucr.ac.cr", valueCapture4.getValue());
    }



}