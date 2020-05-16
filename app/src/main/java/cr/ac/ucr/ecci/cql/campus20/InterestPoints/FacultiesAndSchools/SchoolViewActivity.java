package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.School;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox.Map;
import cr.ac.ucr.ecci.cql.campus20.R;

public class SchoolViewActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    private School school;
    private Place place;
    private Faculty faculty;

    String schoolName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_view);

        Intent intentSchool = getIntent();
        schoolName = intentSchool.getStringExtra(Intent.EXTRA_TEXT);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0); // Quita la sobra del actionbar para que quede oculto

        TextView tittle = findViewById(R.id.schoolName);
        tittle.setText(schoolName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.go_maps_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.go_IP_Map);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent schoolLocation = new Intent(SchoolViewActivity.this, Map.class);
                schoolLocation.putExtra(Intent.EXTRA_TEXT, schoolName);
                startActivity(schoolLocation);
                finish();
                return true;
            }
        });

         return super.onCreateOptionsMenu(menu);
    }

    /**
     * EFE: send the user to the location in maps
     * REQ:
     * view: send by the button that calls this method
     * latitude : latitude of the point that the user wants to go.
     * longitude: longitude of the point that the user wants to go.
     * MOD: ---
     * */
    public void goTo(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    @Override
    public void onClick(String title) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
