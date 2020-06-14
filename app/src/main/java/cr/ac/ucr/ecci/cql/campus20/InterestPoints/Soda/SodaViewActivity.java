package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Soda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class SodaViewActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    String sodaName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soda_view);

        Intent intentLibrary = getIntent();
        sodaName = intentLibrary.getStringExtra(Intent.EXTRA_TEXT);

        TextView tittle = findViewById(R.id.sodaName);
        tittle.setText(sodaName);
    }

    //    /**
//     * EFE: send the user to the location in maps
//     * REQ:
//     * view: send by the button that calls this method
//     * latitude : latitude of the point that the user wants to go.
//     * longitude: longitude of the point that the user wants to go.
//     * MOD: ---
//     * */
//    public void goTo(View view) {
//        Intent intent = new Intent(this, Map.class);
//        startActivity(intent);
//    }

    @Override
    public void onClick(String title) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}