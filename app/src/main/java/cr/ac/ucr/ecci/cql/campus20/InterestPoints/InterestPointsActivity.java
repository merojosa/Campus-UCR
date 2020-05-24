package cr.ac.ucr.ecci.cql.campus20.InterestPoints;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.CoffeShop.CoffeShopsActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.FacultiesActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.DeploymentScript;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.R;

public class InterestPointsActivity extends AppCompatActivity {

    GridLayout mainGrid;

    private FirebaseDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_points);

        db = new FirebaseDB(getApplicationContext());
        //new DeploymentScript().RunScript(getApplicationContext(), db);
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);

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

                    }
                    else if ( finalI == 2) {

                    }
                    else if ( finalI == 3) {
                        Intent intent = new Intent(InterestPointsActivity.this, FacultiesActivity.class);
                        startActivity(intent);
                    }
                    else if ( finalI == 4) {

                    }
                    else if ( finalI == 5) {

                    }
                    else if ( finalI == 6) {

                    }
                }
            });
        }
    }
}