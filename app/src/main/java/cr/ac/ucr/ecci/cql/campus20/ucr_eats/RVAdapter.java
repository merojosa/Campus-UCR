package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SodaViewHolder>
{

    public static class SodaViewHolder extends RecyclerView.ViewHolder
    {

        CardView cv;
        TextView nombreSoda;
    //    ImageView imagenSoda;

        SodaViewHolder(View itemView) {
            super(itemView);

            // Elementos del layout
            cv = (CardView)itemView.findViewById(R.id.cv);
            nombreSoda = (TextView)itemView.findViewById(R.id.nombre_soda);
    //      imagenSoda = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    List<Soda> sodas;

    RVAdapter(List<Soda> sodas){
        this.sodas = sodas;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public SodaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ucr_eats_card, viewGroup, false);
        SodaViewHolder pvh = new SodaViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(SodaViewHolder sodaViewHolder, int i) {
        sodaViewHolder.nombreSoda.setText(sodas.get(i).getNombre());
 //       sodaViewHolder.imagenSoda.setImageResource(sodas.get(i).getFoto());
    }

    @Override
    public int getItemCount() {
        return sodas.size();
    }
}