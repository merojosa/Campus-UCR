package cr.ac.ucr.ecci.cql.campus20.InterestPoints;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.CoffeShop.CoffeShopsActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.FacultiesActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.DeploymentScript;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Library.LibraryActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Office.OfficeActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Photocopier.PhotocopierActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Soda.SodaActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.ActivityInfoDao;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.IPRoomDatabase;
import cr.ac.ucr.ecci.cql.campus20.R;

public class InterestPointsActivity extends AppCompatActivity {

    GridLayout mainGrid;

    private FirebaseDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_points);

        db = new FirebaseDB();

        new DeploymentScript().RunScript(db, getApplicationContext());
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        setActivityTitle();

        //Set Event
        setSingleEvent(mainGrid);

    }

    private void setActivityTitle(){
        ActivityInfoDao activityInfoDao;
        IPRoomDatabase roomDatabase = Room.databaseBuilder(getApplicationContext(), IPRoomDatabase.class, "IPRoomDatabase").build();
        activityInfoDao = roomDatabase.activityInfoDao();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                TextView title = findViewById(R.id.textGrid);
                title.setText(activityInfoDao.getActivityName(DeploymentScript.ActivityNames.INTEREST_POINTS.ordinal()));
            }
        });
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (finalI == 0) {
                        Intent intent = new Intent(InterestPointsActivity.this, CoffeShopsActivity.class);
                        startActivity(intent);
                    }
                    else if ( finalI == 1) {
                        Intent intent = new Intent(InterestPointsActivity.this, SodaActivity.class);
                        startActivity(intent);
                    }
                    else if ( finalI == 2) {
                        Intent intent = new Intent(InterestPointsActivity.this, PhotocopierActivity.class);
                        startActivity(intent);
                    }
                    else if ( finalI == 3) {
                        Intent intent = new Intent(InterestPointsActivity.this, FacultiesActivity.class);
                        startActivity(intent);
                    }
                    else if ( finalI == 4) {
                        Intent intent = new Intent(InterestPointsActivity.this, LibraryActivity.class);
                        startActivity(intent);
                    }
                    else if ( finalI == 5) {
                        Intent intent = new Intent(InterestPointsActivity.this, OfficeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}