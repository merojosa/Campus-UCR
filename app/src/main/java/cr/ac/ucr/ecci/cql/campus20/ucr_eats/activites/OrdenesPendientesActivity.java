package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;

public class OrdenesPendientesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes_pendientes);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference referencia = mDatabase.getReference(CompraActivity.PATH_PEDIDOS);

        referencia.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // Para obtener el map con el formato especificado, sino, devuelve un HashMap
                GenericTypeIndicator<HashMap<String, Order>> t = new GenericTypeIndicator<HashMap<String, Order>>() {};
                Map<String, Order> td = dataSnapshot.getValue(t);

                List<Order> listaOrdenesPendientes = new ArrayList<>();

                for (Map.Entry<String, Order> entrada : td.entrySet())
                {
                    listaOrdenesPendientes.add(entrada.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {}
        });
    }

    private void imprimir(List<Order> lista)
    {
        System.out.println(lista.get(0).getMeal().getName());
    }

}