package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import android.os.Bundle;
import android.util.Log;
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

import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;
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

        try
        {
            boolean stayInActivity = getIntent().getExtras().getBoolean("NO_REDIRECT");
            Log.d("REDIRECT", "STAY");
        }
        catch (NullPointerException e)
        {
            Log.d("REDIRECT", "REDIRECT");
        }

        this.initRoleSpinner();
        this.initButton();

    }

    public static Class getDefaultActivity()
    {
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();
        DatabaseReference config = db.getRoleReference();

        final Class[] act = new Class[1];

        FirebaseListener listener = new FirebaseListener()
        {
            @Override
            public void exito(DataSnapshot dataSnapshot) {
                Integer role = dataSnapshot.getValue(Integer.class);

                if(role == 1)
                    act[0] = MainUcrEats.class;
                else if(role == 2)
                    act[0] = OrdenesPendientesRepartidorActivity.class;
                else
                    act[0] = RoleActivity.class;
            }

            @Override
            public void fallo(DatabaseError databaseError) {
                act[0] = RoleActivity.class;
            }
        };

        return act[0];
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
            Class test = getDefaultActivity();

            int role = roleSpinner.getSelectedItemPosition();

            if(role > 0)
            {
                saveDefaultRole(role);
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
        DatabaseReference config = db.getRoleReference();

        config.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int spinnerIndex = NONE;

                if(dataSnapshot.exists())
                {
                    Integer role = dataSnapshot.getValue(Integer.class);

                    if(role != null && role > NONE)
                        spinnerIndex = role;
                }

                // Validation in case the value goes out of bounds
                if( spinnerIndex > ROLES.length - 1) spinnerIndex = NONE;

                roleSpinner.setSelection(spinnerIndex);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                roleSpinner.setSelection(0);
            }
        });
    }

    private void saveDefaultRole(int role)
    {
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();

        db.saveDefaultRole(role);
    }
}