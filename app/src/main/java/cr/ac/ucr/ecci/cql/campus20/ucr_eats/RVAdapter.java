package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SodaViewHolder>
{
    private final Activity context;
    List<SodaCard> sodaCards;
    Picasso picasso;

    public RVAdapter(Activity context, List<SodaCard> sodaCards)
    {
        this.context = context;
        this.sodaCards = sodaCards;
        this.picasso = new Picasso.Builder(this.context)
                .indicatorsEnabled(true)
                .loggingEnabled(true) //add other settings as needed
                .build();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public SodaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ucr_eats_card, viewGroup, false);

        return new SodaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SodaViewHolder sodaViewHolder, int i)
    {
        sodaViewHolder.nombreSoda.setText(sodaCards.get(i).getNombre());

        //sodaViewHolder.imagenSoda.setImageResource(sodaCards.get(i).getFoto());

        // Acá la primera opción debería ser extraer la imagen de la memoria, pero dado que aún no
        // se descarga, se usará imágenes en resources/drawable para pruebas de concepto
        // por ahora, el id de la soda = R.drawable..., lo que nos es útil para esta prueba
        this.picasso.load(sodaCards.get(i).getId() /* ToDo: acá la imagen que está en memoria*/)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(sodaViewHolder.imagenSoda, new Callback() {
                // Si la imagen no está en cache, se busca en linea
                        @Override
                        public void onSuccess() {
                        }
                        @Override
                        public void onError(Exception e) {
                            picasso.load("https://url-a-la-imagen")
                                    .into(sodaViewHolder.imagenSoda);
                        }
                    });
    }

    @Override
    public int getItemCount()
    {
        return sodaCards.size();
    }

    // El holder del adapter. Aqui va el contenido del card.
    public static class SodaViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView nombreSoda;
        ImageView imagenSoda;

        public SodaViewHolder(View itemView)
        {
            super(itemView);

            // Elementos del layout
            cardView = itemView.findViewById(R.id.cv);
            nombreSoda = itemView.findViewById(R.id.nombre_soda);
            imagenSoda = itemView.findViewById(R.id.imagen_soda);
        }
    }

    public void filter(ArrayList<SodaCard> filtroSodas) {
        this.sodaCards = filtroSodas;
        notifyDataSetChanged();
    }
}