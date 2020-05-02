package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cr.ac.ucr.ecci.cql.campus20.Mapbox.Map;

public class FacultyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view);

        Intent intentFaculties = getIntent();
        String facultyName = intentFaculties.getStringExtra(Intent.EXTRA_TEXT);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(facultyName);
            getSupportActionBar().show();
        }
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
}
