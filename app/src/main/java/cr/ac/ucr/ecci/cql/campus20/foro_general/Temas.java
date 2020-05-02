package cr.ac.ucr.ecci.cql.campus20.foro_general;

public class Temas {

    private String name;
    private int img;
    private String description;

    public Temas(String name, int img, String description) {
        this.name = name;
        this.img = img;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public String getDescription() {
        return description;
    }

    // Para el ListView
    //@Override
    /*public String toString() {
        return this.name;
    }*/
}
