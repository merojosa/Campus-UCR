package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters.PendingOrdersAdapter;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.OrderStatus;

public class RecogerOrdenActivity extends AppCompatActivity
{
    private String idOrden;
    private String repartidor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoger_pedido);

        String soda = getIntent().getStringExtra("Soda");
        String platillo = getIntent().getStringExtra("Platillo");
        idOrden = getIntent().getStringExtra("IdOrder");
        repartidor = getIntent().getStringExtra("Repartidor");

        TextView sodaTexto = findViewById(R.id.titulo_soda_recoger);
        sodaTexto.setText(soda);

        TextView platilloTexto = findViewById(R.id.texto_platillo_recoger);
        platilloTexto.setText(platillo);

        Button button = findViewById(R.id.orden_recogida_button);
        button.setOnClickListener(v -> cambiarEstatusOrden());
    }

    private void cambiarEstatusOrden()
    {
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();
        db.escribirDatos(CompraActivity.PATH_PEDIDOS + "/" + idOrden + "/status", OrderStatus.HACIA_CASA);
        db.eliminarDato(PendingOrdersAdapter.PATH_REPARTIDORES + "/" + repartidor);
        Toast.makeText(this, "El cliente ha sido notificado", Toast.LENGTH_LONG).show();
    }
}