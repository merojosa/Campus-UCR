package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class InterestPointsActivity extends AppCompatActivity {

    GridLayout mainGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_points);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);
//
//        Button buttonFacultiesList = findViewById(R.id.buttonFacultiesList);
//        buttonFacultiesList.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                goFacultiesList();
//            }
//        });
//    }
//
//    private void goFacultiesList() {
//        Intent intent = new Intent(this, FacultiesActivity.class);
//        startActivity(intent);
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