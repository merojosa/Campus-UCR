package cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.PreguntaCard;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;

public class RVAdapterPregunta extends RecyclerView.Adapter<RVAdapterPregunta.PreguntaViewHolder>
{
    private final Activity context;
    List<PreguntaCard> preguntaCards;
    Picasso picasso;

    public RVAdapterPregunta(Activity context, List<PreguntaCard> preguntaCards)
    {
        this.context = context;
        this.preguntaCards = preguntaCards;
        this.picasso = new Picasso.Builder(this.context)
                .indicatorsEnabled(true)
                .loggingEnabled(true) //add other settings as needed
                .build();
    }


    public void setPreguntaCards(List<Pregunta> preguntas)
    {
        this.preguntaCards = convertToPreguntaCards(preguntas);
        notifyDataSetChanged();
    }

    public List<PreguntaCard> convertToPreguntaCards(List<Pregunta> preguntas)
    {
        List<PreguntaCard> cards = new ArrayList<PreguntaCard>();

        for(Pregunta pregunta : preguntas)
        {
            cards.add(
                    new PreguntaCard(pregunta.temaID, pregunta.id, pregunta.texto, pregunta.contadorLikes, pregunta.contadorDisikes)
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
    public PreguntaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foro_general_pregunta_card, viewGroup, false);

        return new PreguntaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PreguntaViewHolder preguntaViewHolder, int i)
    {
        preguntaViewHolder.textoPregunta.setText(preguntaCards.get(i).getTexto());
    }

    @Override
    public int getItemCount()
    {
        return preguntaCards == null ? 0 : preguntaCards.size();
    }

    // El holder del adapter. Aqui va el contenido del card.
    public class PreguntaViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView textoPregunta;

        public PreguntaViewHolder(View itemView)
        {
            super(itemView);

            // Elementos del layout
            cardView = itemView.findViewById(R.id.preguntaCardView);
            textoPregunta = itemView.findViewById(R.id.textoPregunta);
        }

    }
}