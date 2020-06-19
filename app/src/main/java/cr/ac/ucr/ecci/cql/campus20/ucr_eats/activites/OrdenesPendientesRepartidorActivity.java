package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;

public class OrdenesPendientesRepartidorActivity extends AppCompatActivity
{
    private List<Order> listaOrdenes;
    private CampusBD db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes_pendientes_repartidor);

        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();

        DatabaseReference ref = db.getAssignedOrdersRef();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> sodaData = dataSnapshot.getChildren();
                ArrayList<AssignedOrder> orders = new ArrayList<>();
                // Iterate array
                int id = 1;
                for(final DataSnapshot order : sodaData)
                {
                    Log.e("Nombre:", ""+order.getValue());
                    AssignedOrder assignedOrder = order.getValue(AssignedOrder.class);
//                    assignedOrder.setId(id++);
                    if (assignedOrder != null)
                    {
//                        assignedOrder.setFirebaseId(order.getKey());
//                        assignedOrder.setServings(order);
                    }

                    if(order.exists()) {
                        Log.e("datos", "" + order.getValue());
                        orders.add(assignedOrder);
                    }
                }

                if (orders.size() > 0)

//                    sodasAdapter.setSodaCard(orders);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
