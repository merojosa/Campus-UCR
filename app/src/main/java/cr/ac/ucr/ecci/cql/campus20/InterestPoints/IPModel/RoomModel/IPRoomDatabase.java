package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ActivityInfo.class}, version = 1)
public abstract class IPRoomDatabase extends RoomDatabase {
    public abstract ActivityInfoDao activityInfoDao();
}
