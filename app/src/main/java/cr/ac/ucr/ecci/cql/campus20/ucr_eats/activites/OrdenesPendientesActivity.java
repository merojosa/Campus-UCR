package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters.OrdersAdapter;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.AssignedOrder;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;

public class OrdenesPendientesActivity extends AppCompatActivity
{
    private List<Order> listaOrdenes;
    private CampusBD db;
    private final boolean ROL_REPARTIDOR = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes_pendientes);

        db = new FirebaseBD();

        listaOrdenes = new ArrayList<>();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference referencia = mDatabase.getReference(CompraActivity.PATH_PEDIDOS);

        referencia.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // Para obtener el map con el formato especificado, sino, devuelve un HashMap
                GenericTypeIndicator<HashMap<String, Order>> t = new GenericTypeIndicator<HashMap<String, Order>>() {};
                Map<String, Order> mapOrdenes = dataSnapshot.getValue(t);

                if(mapOrdenes != null)
                {
                    for (Map.Entry<String, Order> entrada : mapOrdenes.entrySet())
                    {
                        listaOrdenes.add(entrada.getValue());
                    }
                }

                setupRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {}
        });
    }

    public void setupRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.ordenes_pendientes_rv);
        String mensaje = "";
        String pregunta = "";

        if(ROL_REPARTIDOR == true)
        {
            // Repartidor
            mensaje = "Escoger orden";
            pregunta = "¿Desea escoger esta orden?";
        }
        else
        {
            // Usuario
            mensaje = "Completar orden";
            pregunta = "¿Desea completar esta orden?";
        }

        String finalPregunta = pregunta;
        String finalMensaje = mensaje;

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, (view, position) ->
                {
                    new AlertDialog.Builder(this)
                            .setTitle("Orden")
                            .setMessage(finalPregunta)
                            .setIcon(R.drawable.info_personalizado)
                            .setPositiveButton(finalMensaje, (dialog, whichButton) ->
                            {
                                if(ROL_REPARTIDOR == true)
                                {
                                    eventoRepartidor(position);
                                }

                                //db.eliminarDato(CompraActivity.PATH_PEDIDOS + "/" + listaOrdenes
                                //        .get(position).getIdOrder());

                                
                                listaOrdenes.clear();
                            })
                            .setNegativeButton("Cancelar", (dialog, which) ->
                            {}).show();
                })
        );

        // Performance
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        RecyclerView.Adapter adapterRV = new OrdersAdapter(listaOrdenes);
        recyclerView.setAdapter(adapterRV);
    }

    private void eventoUsuario()
    {

    }

    private void eventoRepartidor(int posicion)
    {
        String repartidor = db.obtenerCorreoActual();
        Order pedidoCliente = listaOrdenes.get(posicion);

        AssignedOrder assignedOrder = new AssignedOrder(repartidor, pedidoCliente);

        // Agregar orden pendiente a Firebase,
        db.escribirDatos("ucr_eats/assignedOrders/", assignedOrder);

    }

    private void enviarNotificacion(AssignedOrder assignedOrder)
    {
        // ... hacer cambio en Firebase
    }
}