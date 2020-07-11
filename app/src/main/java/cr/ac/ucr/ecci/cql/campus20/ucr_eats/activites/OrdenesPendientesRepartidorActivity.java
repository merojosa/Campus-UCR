package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters.PendingOrdersAdapter;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.OrderStatus;

public class OrdenesPendientesRepartidorActivity extends AppCompatActivity
{
    private PendingOrdersAdapter adapter;
    private List<Order> listaOrdenes;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes_pendientes_repartidor);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        this.loadPendingMeals();
    }

    private void loadPendingMeals()
    {
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();
        DatabaseReference ref = db.getPendingOrdersRef();

        this.setupRecyclerView();

        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> orderData = dataSnapshot.getChildren();
                ArrayList<Order> orders = new ArrayList<>();

                // Iterate array
                for(final DataSnapshot order : orderData)
                {
                    if(order.exists())
                    {
                        Order pendingOrder = order.getValue(Order.class);
                        // Mostrar solo los pendientes
                        if(pendingOrder != null && pendingOrder.getStatus() == OrderStatus.PENDIENTE)
                            orders.add(pendingOrder);
                    }
                }

                if(orders.size() > 0)
                    adapter.setOrders(orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupRecyclerView()
    {
        // Create adapter with empty dataset
        this.adapter = new PendingOrdersAdapter(this, listaOrdenes);

        // Set recycler a 2 column grid and the adapter
        recyclerView = findViewById(R.id.assigned_orders_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.adapter);
    }
}
