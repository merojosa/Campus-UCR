package cr.ac.ucr.ecci.cql.campus20.InterestPoints;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;
import java.util.Objects;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.CoffeShop.CoffeShopsActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.FacultiesActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.DeploymentScript;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.ActivityInfoDao;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.IPRoomDatabase;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Library.LibraryActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox.Map;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox.MapUtilities;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Office.OfficeActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Photocopier.PhotocopierActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Soda.SodaActivity;
import cr.ac.ucr.ecci.cql.campus20.R;

public class InterestPointsActivity extends AppCompatActivity {

    GridLayout mainGrid;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private Double currentLatitude;
    private Double currentLongitude;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_points);

        FirebaseDB db = new FirebaseDB();
        this.callLocation();

        new DeploymentScript().RunScript(db, getApplicationContext());
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        setActivityTitle();

        //Set Event
        setSingleEvent(mainGrid);

    }

    private void callLocation() {
        MapUtilities utilities = new MapUtilities(this.currentLatitude, this.currentLongitude);
        Double[] par = utilities.requestForUpdates();
        this.currentLatitude = par[0];
        this.currentLongitude = par[1];
    }

    private void setActivityTitle(){
        IPRoomDatabase roomDatabase = Room.databaseBuilder(getApplicationContext(), IPRoomDatabase.class, "IPRoomDatabase").allowMainThreadQueries().build();
        ActivityInfoDao activityInfoDao = roomDatabase.activityInfoDao();
        TextView title = findViewById(R.id.textGrid);
        String name = activityInfoDao.getActivityName(DeploymentScript.ActivityNames.INTEREST_POINTS.ordinal());
        Objects.requireNonNull(title).setText(name != null? name : "Puntos de interés");
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;

            Bundle params = new Bundle();
            params.putDouble("currentLatitude", this.currentLatitude);
            params.putDouble("currentLongitude", this.currentLongitude);

            cardView.setOnClickListener(view -> {

                if (finalI == 0) {
                    Intent intent = new Intent(InterestPointsActivity.this, CoffeShopsActivity.class);
                    intent.putExtras(params);
                    startActivity(intent);
                }
                else if ( finalI == 1) {
                    Intent intent = new Intent(InterestPointsActivity.this, SodaActivity.class);
                    intent.putExtras(params);
                    startActivity(intent);
                }
                else if ( finalI == 2) {
                    Intent intent = new Intent(InterestPointsActivity.this, PhotocopierActivity.class);
                    intent.putExtras(params);
                    startActivity(intent);
                }
                else if ( finalI == 3) {
                    Intent intent = new Intent(InterestPointsActivity.this, FacultiesActivity.class);
                    intent.putExtras(params);
                    startActivity(intent);
                }
                else if ( finalI == 4) {
                    Intent intent = new Intent(InterestPointsActivity.this, LibraryActivity.class);
                    intent.putExtras(params);
                    startActivity(intent);
                }
                else if ( finalI == 5) {
                    Intent intent = new Intent(InterestPointsActivity.this, OfficeActivity.class);
                    intent.putExtras(params);
                    startActivity(intent);
                }
            });
        }
    }
}