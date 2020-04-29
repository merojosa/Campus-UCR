package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FacultyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view);

        Intent intentFaculties = getIntent();
        String facultyName = intentFaculties.getStringExtra(Intent.EXTRA_TEXT);
        getSupportActionBar().setTitle(facultyName);

    }
}
