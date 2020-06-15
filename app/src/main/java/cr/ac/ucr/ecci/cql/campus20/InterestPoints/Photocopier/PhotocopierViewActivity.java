package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Photocopier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class PhotocopierViewActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    String photocopierName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photocopier_view);

        Intent intentPhotocopier = getIntent();
        photocopierName = intentPhotocopier.getStringExtra(Intent.EXTRA_TEXT);

        TextView tittle = findViewById(R.id.photocopierName);
        tittle.setText(photocopierName);

    }

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