package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Meal",
        foreignKeys = @ForeignKey(entity = Restaurant.class,
                    parentColumns = "id",
                    childColumns = "restaurant_id",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE), // Foreign key to Restaurant ID
        indices = {@Index(value={"name"}, unique = true)}) // Unique Meal name
public class Meal
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "restaurant_id")
    private int restaurant_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "photo")
    private String photo;

    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "price")
    private int price;

    public Meal(int restaurant_id, String name, String photo, int type, int price)
    {
        this.setRestaurant_id(restaurant_id);
        this.setName(name);
        this.setPhoto(photo);
        this.setType(type);
        this.setPrice(price);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
