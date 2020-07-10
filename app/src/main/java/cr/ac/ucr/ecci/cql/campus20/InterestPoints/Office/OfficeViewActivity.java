package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Office;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.BaseCommentPopUp;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.CommentPopUp;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Office;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class OfficeViewActivity extends BaseCommentPopUp implements ListAdapter.ListAdapterOnClickHandler {

    String officeName;
    Office office;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_view);

        Intent intentLibrary = getIntent();
        officeName = intentLibrary.getStringExtra(Intent.EXTRA_TEXT);

        TextView tittle = findViewById(R.id.officeName);
        tittle.setText(officeName);

        office = intentLibrary.getParcelableExtra("place");


        TextView desc = findViewById(R.id.descripcion);
        desc.setText(office.description);

        TextView horario = findViewById(R.id.horario);
        horario.setText(office.horario);

        TextView telefono = findViewById(R.id.telefono);
        telefono.setText(office.telefono);

        /*POPUP*/
        Button popButton = findViewById(R.id.comments);
        /*Line needed for CommentPopUp to work properly.*/
        popButton.setOnClickListener(view -> commentPopUp = new CommentPopUp(view, this, office));
        /*POPUP*/
    }


    @Override
    public void onClick(String title) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}