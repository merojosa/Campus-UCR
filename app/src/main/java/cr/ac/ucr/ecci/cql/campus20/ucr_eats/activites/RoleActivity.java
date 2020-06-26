package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import cr.ac.ucr.ecci.cql.campus20.R;

public class RoleActivity extends AppCompatActivity
{

    private final String[] ROLES = {"Cliente", "Repartidor"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        this.initRoleSpinner();
    }

    private void initRoleSpinner()
    {
        ArrayAdapter<String> spinnerAdapter =
            new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, this.ROLES);

        Spinner spinner = findViewById(R.id.default_role_spinner);

        spinner.setAdapter(spinnerAdapter);
    }
}