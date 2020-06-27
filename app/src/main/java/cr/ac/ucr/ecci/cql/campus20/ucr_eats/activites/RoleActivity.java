package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;

public class RoleActivity extends AppCompatActivity
{
    private final int NONE = 0, CLIENTE = 1, REPARTIDOR = 2;
    private final String[] ROLES = {"Elegir rol", "Cliente", "Repartidor"};
    private Spinner roleSpinner;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        this.initRoleSpinner();
        this.initButton();

    }

    private void initRoleSpinner()
    {
        ArrayAdapter<String> spinnerAdapter =
            new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, this.ROLES);

        this.roleSpinner = findViewById(R.id.default_role_spinner);

        roleSpinner.setAdapter(spinnerAdapter);
        getDefaultRole();
    }

    private void initButton()
    {
        this.okButton = findViewById(R.id.save_role_button);

        this.okButton.setOnClickListener( view ->
        {
            int role = roleSpinner.getSelectedItemPosition();

            if(role > 0)
            {
                saveDefaultRole(ROLES[role]);
                Toast.makeText(this, "Guardado", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "Seleccione un rol", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getDefaultRole()
    {
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();

        final String[] role = new String[1];
        role[0] = "";

        DatabaseReference config = db.getRoleReference();

        config.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int spinnerIndex = 0;

                if(dataSnapshot.exists())
                {
                    String role = dataSnapshot.getValue(String.class);

                    if(role.equals(ROLES[CLIENTE]))
                        spinnerIndex = CLIENTE;
                    else if(role.equals(ROLES[REPARTIDOR]))
                        spinnerIndex = REPARTIDOR;
                    else
                        spinnerIndex = NONE;
                }

                roleSpinner.setSelection(spinnerIndex);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                roleSpinner.setSelection(0);
            }
        });
    }

    private void saveDefaultRole(String role)
    {
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();

        db.saveDefaultRole(role);
    }
}