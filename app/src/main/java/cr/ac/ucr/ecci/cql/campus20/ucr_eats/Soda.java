package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

public class Soda
{
    private String nombre;
    private int foto;

    public Soda(String nombre, int foto)
    {
        this.nombre = nombre;
        this.foto = foto;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
