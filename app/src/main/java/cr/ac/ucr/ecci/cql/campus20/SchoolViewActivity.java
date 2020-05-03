package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cr.ac.ucr.ecci.cql.campus20.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.IPModel.School;
import cr.ac.ucr.ecci.cql.campus20.Mapbox.Map;

public class SchoolViewActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    private School school;
    private Place place;
    private Faculty faculty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_view);

        Intent intentSchool = getIntent();
        String schoolName = intentSchool.getStringExtra(Intent.EXTRA_TEXT);

        getSupportActionBar().hide();

        TextView tittle = findViewById(R.id.schoolName);
        tittle.setText(schoolName);

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
