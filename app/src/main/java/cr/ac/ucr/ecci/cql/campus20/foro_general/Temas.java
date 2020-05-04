package cr.ac.ucr.ecci.cql.campus20.foro_general;

public class Temas {

    private String name;
    private int img;
    private String description;

    /**
     * Construtor del objeto temas
     * @param name nombre para el tema
     * @param description una breve descripción del mismo
     * @param img la imagen a incluir en la lista, para representar el tema
     */
    public Temas(String name, int img, String description) {
        this.name = name;
        this.img = img;
        this.description = description;
    }

    /**
     * Metodo para obtener el nombre del tema
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Método para obtener la imagen del tema
     * @return img
     */
    public int getImg() {
        return img;
    }

    /**
     * Método para obtener la descripción del tema
     * @return description
     */
    public String getDescription() {
        return description;
    }

    // Para el ListView
    //@Override
    /*public String toString() {
        return this.name;
    }*/
}
