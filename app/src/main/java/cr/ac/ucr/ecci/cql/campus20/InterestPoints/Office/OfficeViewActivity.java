package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Office;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Office;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class OfficeViewActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

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
    }


    @Override
    public void onClick(String title) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}