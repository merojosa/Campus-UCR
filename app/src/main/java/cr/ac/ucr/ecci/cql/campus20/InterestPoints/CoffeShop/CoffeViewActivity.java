package cr.ac.ucr.ecci.cql.campus20.InterestPoints.CoffeShop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.BaseCommentPopUp;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.CommentPopUp;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Coffee;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class CoffeViewActivity extends BaseCommentPopUp implements ListAdapter.ListAdapterOnClickHandler {

    String coffeName;
    Coffee coffe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffe_view);

        Intent intentCoffe = getIntent();
        coffeName = intentCoffe.getStringExtra(Intent.EXTRA_TEXT);

        coffe = intentCoffe.getParcelableExtra("place");

        TextView tittle = findViewById(R.id.coffeName);
        tittle.setText(coffeName);

        TextView desc = findViewById(R.id.descripcion);
        desc.setText(coffe.description);

        TextView horario = findViewById(R.id.horario);
        horario.setText(coffe.horario);

        TextView oferta = findViewById(R.id.oferta);
        oferta.setText(coffe.oferta);

        ImageView wifi = findViewById(R.id.imageWiFi);
        AppCompatCheckBox wifiCheck = findViewById(R.id.checkboxWiFi);

        wifi.setImageResource(R.drawable.icon_wifi);
        wifiCheck.setChecked(coffe.wifi);
        wifiCheck.setEnabled(false);

        /*POPUP*/
        Button popButton = findViewById(R.id.comments);
        /*Line needed for CommentPopUp to work properly.*/
        popButton.setOnClickListener(view -> commentPopUp = new CommentPopUp(view, this, coffe));
        /*POPUP*/

    }


    @Override
    public void onClick(String title) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
