package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ActivityInfoDao {
    @Query("SELECT * FROM ActivityInfo")
    List<ActivityInfo> getAll();

    @Query("SELECT name FROM ActivityInfo WHERE id = :activityId")
    String getActivityName(int activityId);

    @Insert
    void insert(ActivityInfo activityInfo);
}
