package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Soda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.BaseCommentPopUp;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.CommentPopUp;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Soda;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class SodaViewActivity extends BaseCommentPopUp implements ListAdapter.ListAdapterOnClickHandler {

    String sodaName;
    Soda soda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soda_view);

        Intent intentLibrary = getIntent();
        sodaName = intentLibrary.getStringExtra(Intent.EXTRA_TEXT);

        TextView tittle = findViewById(R.id.sodaName);
        tittle.setText(sodaName);

        soda = intentLibrary.getParcelableExtra("place");

        TextView desc = findViewById(R.id.descripcion);
        desc.setText(soda.description);

        TextView horario = findViewById(R.id.horario);
        horario.setText(soda.horario);

        TextView oferta = findViewById(R.id.oferta);
        oferta.setText(soda.oferta);

        ImageView wifi = findViewById(R.id.imageWiFi);
        AppCompatCheckBox wifiCheck = findViewById(R.id.checkboxWiFi);

        wifi.setImageResource(R.drawable.icon_wifi);
        wifiCheck.setChecked(soda.wifi);
        wifiCheck.setEnabled(false);

        ImageView express = findViewById(R.id.imageExpress);
        AppCompatCheckBox expressCheck = findViewById(R.id.checkboxExpress);

        express.setImageResource(R.drawable.repartidor);
        expressCheck.setChecked(soda.express);
        expressCheck.setEnabled(false);

        /*POPUP*/
        Button popButton = findViewById(R.id.comments);
        /*Line needed for CommentPopUp to work properly.*/
        popButton.setOnClickListener(view -> commentPopUp = new CommentPopUp(view, this, soda));
        /*POPUP*/
    }

    @Override
    public void onClick(String title) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}