package cr.ac.ucr.ecci.cql.campus20.foro_general.models;
import androidx.annotation.NonNull;
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

    @PrimaryKey
    @NonNull
    public int id;

    @ColumnInfo(name = "titulo")
    public String titulo = "";

    @ColumnInfo(name= "descripcion")
    private String description;

    @ColumnInfo(name = "contadorUsuarios")
    public int contadorUsuarios = 0;

    @ColumnInfo(name = "imagenId")
    private int imagen;


    public Tema(int id, String titulo, String description, int contadorUsuarios, int imagen) {
        this.id = id;
        this.titulo = titulo;
        this.description = description;
        this.contadorUsuarios = contadorUsuarios;
        this.imagen = imagen;
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
        if(this.description != null)
            return description;
        else
            return "description";
    }

    public int getId() {return id;}

    public String getTitulo() {return titulo;}

    public int getImagen() {return imagen;}

    // Para el ListView
    @Override
    public String toString() {
        return this.titulo;
    }
}
