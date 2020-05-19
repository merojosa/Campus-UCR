package cr.ac.ucr.ecci.cql.campus20.foro_general.models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(tableName = "Tema")
public class Tema {

    @Ignore
    private String name;
    @Ignore
    private int img;
    @Ignore
    private String description;

    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "titulo")
    public String titulo = "";

    @ColumnInfo(name = "contadorUsuarios")
    public int contadorUsuarios = 0;

    public Tema(int id, String titulo, int contadorUsuarios) {
        this.id = id;
        this.titulo = titulo;
        this.contadorUsuarios = contadorUsuarios;
    }

    /**
     * Construtor del objeto temas
     * @param name nombre para el tema
     * @param description una breve descripción del mismo
     * @param img la imagen a incluir en la lista, para representar el tema
     */
    @Ignore
    public Tema(String name, int img, String description) {
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
