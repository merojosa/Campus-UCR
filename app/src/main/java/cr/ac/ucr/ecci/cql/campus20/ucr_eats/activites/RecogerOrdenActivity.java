package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import cr.ac.ucr.ecci.cql.campus20.R;

public class RecogerOrdenActivity extends AppCompatActivity
{
    private String idOrden;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoger_pedido);

        // Nombre de la soda
        String soda = getIntent().getStringExtra("Soda");

        // Id de la orden
        idOrden = getIntent().getStringExtra("IdOrder");

        TextView sodaTexto = findViewById(R.id.titulo_soda_recoger);
        sodaTexto.setText(soda);
    }
}