package cr.ac.ucr.ecci.cql.campus20.foro_general;

public class Temas {
    public static final String IMAGEN = "i";

    private final String name;
    private final String img;
    private final String description;

    public Temas(String name, String img, String description) {
        this.name = name;
        this.img = img;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getDescription() {
        return description;
    }

    // Para el ListView
    @Override
    public String toString() {
        return this.name;
    }
}
