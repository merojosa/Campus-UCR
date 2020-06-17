package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "ActivityInfo")
public class ActivityInfo {

    @PrimaryKey
    public int id;

    @NonNull
    @ColumnInfo(name = "Name")
    public String name;

    public ActivityInfo() {}

    public ActivityInfo(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
