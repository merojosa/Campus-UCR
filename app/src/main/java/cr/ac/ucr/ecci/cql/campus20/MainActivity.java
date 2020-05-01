package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cr.ac.ucr.ecci.cql.campus20.IPModel.DeploymentScript;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DeploymentScript.RunScript(getApplicationContext());
        Button buttonInterestPoints = findViewById(R.id.buttonInterestPoints);
        buttonInterestPoints.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goInterestPoints();
            }
        });
    }

    private void goInterestPoints() {
        Intent intent = new Intent(this, InterestPointsActivity.class);
        startActivity(intent);
    }
}