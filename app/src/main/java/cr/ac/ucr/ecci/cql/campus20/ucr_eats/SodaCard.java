package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

public class SodaCard
{
    int id;
    private String nombre;
    private String foto;

    public SodaCard(int id, String nombre, String foto)
    {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() { return foto; }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
