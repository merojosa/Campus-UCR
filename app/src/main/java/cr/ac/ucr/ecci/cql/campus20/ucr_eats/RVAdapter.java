package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SodaViewHolder>
{
    List<SodaCard> sodaCards;

    public RVAdapter(List<SodaCard> sodaCards)
    {
        this.sodaCards = sodaCards;
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
 //       sodaViewHolder.imagenSoda.setImageResource(sodas.get(i).getFoto());
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
        //    ImageView imagenSoda;

        SodaViewHolder(View itemView)
        {
            super(itemView);

            // Elementos del layout
            cardView = itemView.findViewById(R.id.cv);
            nombreSoda = itemView.findViewById(R.id.nombre_soda);
            //      imagenSoda = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
}