package cr.ac.ucr.ecci.cql.campus20;


import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

// Referencia para testear metodos asincronicos: https://stackoverflow.com/a/1829949/11026428
@RunWith(AndroidJUnit4.class)
public class TestLoginFirebaseDb
{
    private CountDownLatch lock;
    private CampusBD campusBD;
    private Context context;
    private static final String CORREO_TEST = "test@ucr.ac.cr";

    @Before
    public void iniciar()
    {
        lock = new CountDownLatch(1);
        campusBD = new FirebaseBD();
        context = ApplicationProvider.getApplicationContext();

        // Cuando inicie sesion, se libera el candado.
        campusBD.iniciarSesion(CORREO_TEST, "123456").addOnSuccessListener(o ->
                lock.countDown());
    }

    @After
    public void finalizar()
    {
        campusBD.cerrarSesion();
    }

    @Test
    public void autenticadoDbTest() throws InterruptedException
    {
        // Se espera por 5 segundos hasta que se pueda lograr iniciar sesion
        lock.await(5000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(campusBD.autenticado());
    }

    @Test
    public void obtenerCorreoDbTest() throws InterruptedException
    {
        // Se espera por 5 segundos hasta que se pueda lograr iniciar sesion
        lock.await(5000, TimeUnit.MILLISECONDS);
        Assert.assertEquals(CORREO_TEST, campusBD.obtenerCorreoActual());
    }

    @Test
    public void cerrarSesionDbTest() throws InterruptedException
    {
        // Se espera por 5 segundos hasta que se pueda lograr iniciar sesion
        lock.await(5000, TimeUnit.MILLISECONDS);
        campusBD.cerrarSesion();
        Assert.assertFalse(campusBD.autenticado());
    }
}
