package cr.ac.ucr.ecci.cql.campus20.foro_general.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Rank_preguntas")
public class RankPregunta {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="IdPreg")
    public int idPreg;

    //2 disliked, 1 liked
    @NonNull
    @ColumnInfo(name="IsLiked")
    public int isLiked;

    // Constructor
    public RankPregunta(@NonNull int idPreg, @NonNull int isLiked)
    {
        this.idPreg = idPreg;
        this.isLiked = isLiked;
    }

    public int getIdPreg() {
        return idPreg;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIdPreg(int idPreg) {
        this.idPreg = idPreg;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }
}
