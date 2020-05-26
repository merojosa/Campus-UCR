package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Rating",
        foreignKeys = @ForeignKey(entity = Restaurant.class,
                parentColumns = "id",
                childColumns = "restaurant_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE), // Unique Meal name
        indices = {@Index(value={"point"}, unique = true)}) // Unique Meal name
public class Rating {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "restaurant_id")
    private int restaurant_id;

    @ColumnInfo(name = "point")
    private int point;


    public Rating(int restaurant_id,  int point)
    {
        this.setRestaurant_id(restaurant_id);
        this.setPoint(point);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

}