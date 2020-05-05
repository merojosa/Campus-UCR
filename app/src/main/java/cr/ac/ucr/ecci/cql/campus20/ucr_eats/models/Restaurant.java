package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Restaurant",
        indices = {@Index(value={"name"}, unique = true)})
public class Restaurant
{
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "name")
    public String name = "";

    @ColumnInfo(name = "photo")
    public String photo = "";

    @ColumnInfo(name = "latitude")
    public double latitude = 0;

    @ColumnInfo(name = "longitude")
    public double longitude = 0;

    @ColumnInfo(name = "days_open")
    public String daysOpen = "";

    @ColumnInfo(name = "opening_time")
    public short openingTime = 0;

    @ColumnInfo(name = "closing_time")
    public short closingTime = 0;

    public Restaurant(int id, String name, String photo, double latitude, double longitude,
                      String daysOpen, short openingTime, short closingTime)
    {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.daysOpen = daysOpen;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }
}
