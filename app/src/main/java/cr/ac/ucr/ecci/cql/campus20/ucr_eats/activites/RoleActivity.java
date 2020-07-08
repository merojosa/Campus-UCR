package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import android.content.Context;
import android.content.Intent;
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

import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;

public class RoleActivity extends AppCompatActivity
{
    private final static int NONE = 0, CLIENTE = 1, REPARTIDOR = 2;
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

    public static void startDefaultActivity(Context context)
    {
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();
        DatabaseReference config = db.getRoleReference();

        config.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Integer role = NONE;
                if(dataSnapshot.exists())
                    role = dataSnapshot.getValue(Integer.class);

                if(role == null) role = 0;

                if(role == REPARTIDOR)
                {
                    goDeliverymanActivity(context, db);
                }
                else
                {
                    Intent intent = new Intent(context, getMappedIndexToClass(role));

                    // Close current activity and open the selected one
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });

    }

    private static void goDeliverymanActivity(Context context, UcrEatsFirebaseDatabase db)
    {
        String email = db.obtenerCorreoActual();
        String usuario = email.substring(0, email.indexOf('@'));

        db.getDeliverymanOrder(usuario).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Order order = dataSnapshot.getValue(Order.class);
                Intent intent;

                if(order != null)
                {
                    intent = new Intent(context, RecogerOrdenActivity.class);
                    intent.putExtra("Soda", order.getRestaurant());
                    intent.putExtra("IdOrder", order.getIdOrder());
                    intent.putExtra("Repartidor", order.getUsername());
                }
                else
                {
                    intent = new Intent(context, OrdenesPendientesRepartidorActivity.class);
                }

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private static Class getMappedIndexToClass(int index)
    {
        if(index == CLIENTE)
            return MainUcrEats.class;
        else
            return RoleActivity.class;
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
                saveDefaultRole(role);
                Toast.makeText(this, "Guardado", Toast.LENGTH_LONG).show();
                startDefaultActivity(this);
                finishAffinity();
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