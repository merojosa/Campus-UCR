package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import static org.junit.jupiter.api.Assertions.*;

import android.content.Context;
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

class CrearComunidadTest {

    //Verifica que onCreate() se llame solo una vez al instanciar la clase
    @Test
    public void onCreate(){
        CrearComunidad test = Mockito.mock(CrearComunidad.class);
        Bundle btest = Mockito.mock(Bundle.class);
        test.onCreate(btest);
        verify(test, times(1)).onCreate(btest);
    }

    //Verifica que popUpCrear se despliegue solo una vez en onCreate
    @Test
    public void popUpCrear(){
        CrearComunidad test = Mockito.mock(CrearComunidad.class);
        Bundle btest = Mockito.mock(Bundle.class);
        Context ctest = Mockito.mock(Context.class);
        doNothing().when(test).onCreate(isA(Bundle.class));
        doNothing().when(test).popUpCrear(isA(Context.class),isA(String.class),isA(String.class));
        test.onCreate(btest);
        test.popUpCrear(ctest,"Chicas Superpoderosas", "Vivas nos queremos");
        verify(test,times(1)).popUpCrear(ctest,"Chicas Superpoderosas", "Vivas nos queremos");
    }

    // Verifica que los parámetros se captaron y se reciben correctamente
    @Test
    public void popUpCrear2(){
        CrearComunidad test = Mockito.mock(CrearComunidad.class);
        Context ctest = Mockito.mock(Context.class);
        ArgumentCaptor<Context> valueCapture1 = ArgumentCaptor.forClass(Context.class);
        ArgumentCaptor<String> valueCapture2 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCapture3 = ArgumentCaptor.forClass(String.class);
        doNothing().when(test).popUpCrear(valueCapture1.capture(),valueCapture2.capture(),valueCapture3.capture());

        test.popUpCrear(ctest,"Chicas Superpoderosas", "Vivas nos queremos");
        Assertions.assertEquals(ctest, valueCapture1.getValue());
        Assertions.assertEquals("Chicas Superpoderosas", valueCapture2.getValue());
        Assertions.assertEquals("Vivas nos queremos", valueCapture3.getValue());
    }

    // Verifica que los parámetros se captaron y se reciben correctamente
    @Test
    public void escribirComunidadEnBD(){

    }

}