package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import com.google.android.gms.tasks.Task;

public interface ValidacionLogin
{
    // Se obliga a que sea una tarea asincronica y el que lo invoca tiene que manejarlo.
    public Task validarCredenciales(String correo, String contrasenna);

    public boolean autenticado();
}
