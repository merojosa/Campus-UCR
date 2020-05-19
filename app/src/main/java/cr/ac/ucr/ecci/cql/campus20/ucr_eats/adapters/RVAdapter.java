package cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.SodaCard;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites.MealsActivity;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;
import timber.log.Timber;

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


    public void setSodaCards(List<Restaurant> restaurants)
    {
        this.sodaCards = convertToSodaCards(restaurants);

        notifyDataSetChanged();
    }

    public List<SodaCard> convertToSodaCards(List<Restaurant> restaurants)
    {
        List<SodaCard> cards = new ArrayList<SodaCard>();

        for(Restaurant restaurant : restaurants)
        {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            String horario = "Cerrado";

            Date d = new Date();
            String nameDay = (String) android.text.format.DateFormat.format("EEE", d);

            if (hour >= restaurant.openingTime && hour < restaurant.closingTime && restaurant.daysOpen.indexOf(nameDay) != -1) {
                horario = "Abierto";
            }

            cards.add(
                    new SodaCard(restaurant.id, restaurant.name, restaurant.photo, horario)
            );
        }

        return cards;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NotNull
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
        sodaViewHolder.horarioSoda.setText(sodaCards.get(i).getHorario());
        loadCardImage(sodaViewHolder, i);

    }

    @Override
    public int getItemCount()
    {
        return sodaCards == null ? 0 : sodaCards.size();
    }

    // El holder del adapter. Aqui va el contenido del card.
    public class SodaViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView nombreSoda;
        TextView horarioSoda;
        ImageView imagenSoda;

        public SodaViewHolder(View itemView)
        {
            super(itemView);

            // Elementos del layout
            cardView = itemView.findViewById(R.id.cv);
            nombreSoda = itemView.findViewById(R.id.nombre_soda);
            horarioSoda = itemView.findViewById(R.id.horario_soda);
            imagenSoda = itemView.findViewById(R.id.imagen_soda);

            for(SodaCard soda : sodaCards) {
                if (soda.getHorario() == "Abierto")
                    horarioSoda.setTextColor(context.getResources().getColor(R.color.verdeUCR));
                if (soda.getHorario() == "Cerrado")
                    horarioSoda.setTextColor(context.getResources().getColor(R.color.red));
            }

            // Opens meals activity when card is clicked
            cardView.setOnClickListener(view -> {
                SodaCard card = sodaCards.get(getAdapterPosition());

                Intent intent = new Intent(view.getContext(), MealsActivity.class);
                intent.putExtra("SODACARD", card);
                view.getContext().startActivity(intent);
            });
        }

    }

    public void filter(ArrayList<SodaCard> filtroSodas) {
        this.sodaCards = filtroSodas;
        notifyDataSetChanged();
    }

    private void loadCardImage(SodaViewHolder sodaViewHolder, int i)
    {
        // Acá la primera opción debería ser extraer la imagen de la memoria, pero dado que aún no
        // se descarga, se usará imágenes en resources/drawable para pruebas de concepto
        // por ahora, el id de la soda = R.drawable..., lo que nos es útil para esta prueba
        this.picasso.load(sodaCards.get(i).getId() /* ToDo: acá la imagen que está en memoria*/)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.soda_placeholder)
                .into(sodaViewHolder.imagenSoda, new Callback() {
                    // Si la imagen no está en cache, se busca en linea
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError(Exception e) {
                        picasso.load("https://url-a-la-imagen")
                                .placeholder(R.drawable.soda_placeholder)
                                .into(sodaViewHolder.imagenSoda);
                    }
                });
    }
}