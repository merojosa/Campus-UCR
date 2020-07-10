package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Photocopier;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.BaseCommentPopUp;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.CommentPopUp;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Photocopier;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class PhotocopierViewActivity extends BaseCommentPopUp implements ListAdapter.ListAdapterOnClickHandler {

    String photocopierName;
    Photocopier fotocopias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photocopier_view);

        Intent intentPhotocopier = getIntent();
        photocopierName = intentPhotocopier.getStringExtra(Intent.EXTRA_TEXT);

        TextView tittle = findViewById(R.id.photocopierName);
        tittle.setText(photocopierName);

        fotocopias = intentPhotocopier.getParcelableExtra("place");

        TextView desc = findViewById(R.id.descripcion);
        desc.setText(fotocopias.description);

        TextView horario = findViewById(R.id.horario);
        horario.setText(fotocopias.horario);

        TextView copias = findViewById(R.id.copias);
        copias.setText(fotocopias.precioHoja);

        ImageView wifi = findViewById(R.id.imageWiFi);
        AppCompatCheckBox wifiCheck = findViewById(R.id.checkboxWiFi);

        wifi.setImageResource(R.drawable.icon_wifi);
        wifiCheck.setChecked(fotocopias.wifi);
        wifiCheck.setEnabled(false);

        /*POPUP*/
        Button popButton = findViewById(R.id.comments);
        /*Line needed for CommentPopUp to work properly.*/
        popButton.setOnClickListener(view -> commentPopUp = new CommentPopUp(view, this, fotocopias));
        /*POPUP*/
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