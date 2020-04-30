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
    public int restaurant_id;

    @ColumnInfo(name = "name")
    public String name = "";

    @ColumnInfo(name = "photo")
    public int photo = 0;

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
}
