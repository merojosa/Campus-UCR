package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
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



}