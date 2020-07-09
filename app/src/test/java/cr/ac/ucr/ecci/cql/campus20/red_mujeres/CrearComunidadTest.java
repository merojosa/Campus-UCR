package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void onCreate(){
        CrearComunidad test = Mockito.mock(CrearComunidad.class);
        Bundle btest = Mockito.mock(Bundle.class);
        test.onCreate(btest);
        verify(test, times(1)).onCreate(btest);
    }

}