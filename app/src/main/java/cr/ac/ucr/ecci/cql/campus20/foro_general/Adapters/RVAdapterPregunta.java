package cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.PreguntaCard;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.PreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.RankPreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankPregunta;

public class RVAdapterPregunta extends RecyclerView.Adapter<RVAdapterPregunta.PreguntaViewHolder>
{
    private final Activity context;
    List<PreguntaCard> preguntaCards;
    Picasso picasso;

    private RankPreguntaViewModel mRankPreguntaViewModel;
    private PreguntaViewModel mPreguntaViewModel;
    private int[] arrayLikes;
    private int[] arrayDislikes;
    private int[] arrayStatusRank;
    private int selectedPosition = -1;

    public RVAdapterPregunta(Activity context, List<PreguntaCard> preguntaCards)
    {

        this.context = context;
        this.preguntaCards = preguntaCards;
        this.picasso = new Picasso.Builder(this.context)
                .indicatorsEnabled(true)
                .loggingEnabled(true) //add other settings as needed
                .build();

        mRankPreguntaViewModel = new ViewModelProvider((FragmentActivity) context).get(RankPreguntaViewModel.class);
        mPreguntaViewModel = new ViewModelProvider((FragmentActivity) context).get(PreguntaViewModel.class);
    }

    public void setPreguntaCards(List<Pregunta> preguntas)
    {
        this.preguntaCards = convertToPreguntaCards(preguntas);
        if(selectedPosition == -1){
            llenarArrays();
        }
        notifyDataSetChanged();
    }

    private void llenarArrays(){
        int tam = preguntaCards.size();
        arrayLikes = new int[tam];
        arrayDislikes = new int[tam];
        arrayStatusRank = new int[tam];
        for(int k = 0; k < tam; k++){
            arrayLikes[k] = preguntaCards.get(k).getContadorLikes();
            arrayDislikes[k] = preguntaCards.get(k).getContadorDislikes();
            List<Integer> mRankPregList = mRankPreguntaViewModel.getRank(preguntaCards.get(k).getId());
            if(mRankPregList != null){
                arrayStatusRank[k] = mRankPregList.get(0);
            }
        }
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
        if(selectedPosition == -1) {
            preguntaViewHolder.textoPregunta.setText(preguntaCards.get(i).getTexto());

            int difRanking = arrayLikes[i] - arrayDislikes[i];
            preguntaViewHolder.contadorLikes.setText(String.valueOf(difRanking));
            if (difRanking > 0) {
                preguntaViewHolder.contadorLikes.setTextColor(context.getResources().getColor(R.color.verde_UCR));
            } else if (difRanking < 0) {
                preguntaViewHolder.contadorLikes.setTextColor(context.getResources().getColor(R.color.rojoForo));
            } else {
                preguntaViewHolder.contadorLikes.setTextColor(context.getResources().getColor(R.color.gris_medio_UCR));
            }

            switch (arrayStatusRank[i]) {
                case 1:
                    preguntaViewHolder.iconLike.setImageResource(R.drawable.ic_thumb_up_green_24dp);
                    preguntaViewHolder.iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                    break;
                case 2:
                    preguntaViewHolder.iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                    preguntaViewHolder.iconDislike.setImageResource(R.drawable.ic_thumb_down_red_24dp);
                    break;
                default:
                    preguntaViewHolder.iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                    preguntaViewHolder.iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                    break;
            }
        }
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
        ImageView iconLike;
        ImageView iconDislike;
        TextView contadorLikes;

        public PreguntaViewHolder(View itemView)
        {
            super(itemView);

            // Elementos del layout
            cardView = itemView.findViewById(R.id.preguntaCardView);
            textoPregunta = itemView.findViewById(R.id.textoPregunta);
            iconLike = itemView.findViewById(R.id.likeIcon);
            iconDislike = itemView.findViewById(R.id.dislikeIcon);
            contadorLikes = itemView.findViewById(R.id.textoContador);

            //Agarra el click del like en una pregunta y escoge que hacer
            iconLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indexPreg = getAdapterPosition();
                    selectedPosition = indexPreg;
                    int idPregTemp = preguntaCards.get(indexPreg).getId();

                    if(arrayStatusRank[indexPreg] == 1){
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayLikes[indexPreg]--;
                        mPreguntaViewModel.updateLikes(idPregTemp,-1);
                        arrayStatusRank[indexPreg] = 0;
                        mRankPreguntaViewModel.deleteRank(idPregTemp);
                    }else if(arrayStatusRank[indexPreg] == 2){
                        iconLike.setImageResource(R.drawable.ic_thumb_up_green_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayLikes[indexPreg]++;
                        arrayDislikes[indexPreg]--;
                        mPreguntaViewModel.updateLikes(idPregTemp,1);
                        mPreguntaViewModel.updateDislikes(idPregTemp,-1);
                        arrayStatusRank[indexPreg] = 1;
                        mRankPreguntaViewModel.updateIsLiked(1,idPregTemp);
                    }else{
                        iconLike.setImageResource(R.drawable.ic_thumb_up_green_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayLikes[indexPreg]++;
                        mPreguntaViewModel.updateLikes(idPregTemp,1);
                        arrayStatusRank[indexPreg] = 1;
                        RankPregunta rankPregunta = new RankPregunta(idPregTemp,1);
                        mRankPreguntaViewModel.insert(rankPregunta);
                    }
                    int difRanking = arrayLikes[indexPreg]-arrayDislikes[indexPreg];
                    contadorLikes.setText(String.valueOf(difRanking));
                    if(difRanking==0){
                        contadorLikes.setTextColor(context.getResources().getColor(R.color.gris_medio_UCR));
                    }else if(difRanking>0){
                        contadorLikes.setTextColor(context.getResources().getColor(R.color.verde_UCR));
                    }
                }
            });
            //Agarra el click del dislike en una pregunta y escoge que hacer
            iconDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indexPreg = getAdapterPosition();
                    selectedPosition = indexPreg;
                    int idPregTemp = preguntaCards.get(indexPreg).getId();

                    if(arrayStatusRank[indexPreg] == 1){
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_red_24dp);
                        arrayLikes[indexPreg]--;
                        arrayDislikes[indexPreg]++;
                        mPreguntaViewModel.updateLikes(idPregTemp,-1);
                        mPreguntaViewModel.updateDislikes(idPregTemp,1);
                        arrayStatusRank[indexPreg] = 2;
                        mRankPreguntaViewModel.updateIsLiked(2,idPregTemp);
                    }else if(arrayStatusRank[indexPreg] == 2){
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayDislikes[indexPreg]--;
                        mPreguntaViewModel.updateDislikes(idPregTemp,-1);
                        arrayStatusRank[indexPreg] = 0;
                        mRankPreguntaViewModel.deleteRank(idPregTemp);
                    }else{
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_red_24dp);
                        arrayDislikes[indexPreg]++;
                        mPreguntaViewModel.updateDislikes(idPregTemp,1);
                        arrayStatusRank[indexPreg] = 2;
                        RankPregunta rankPregunta = new RankPregunta(idPregTemp,2);
                        mRankPreguntaViewModel.insert(rankPregunta);
                    }
                    int difRanking = arrayLikes[indexPreg]-arrayDislikes[indexPreg];
                    contadorLikes.setText(String.valueOf(difRanking));
                    if(difRanking==0){
                        contadorLikes.setTextColor(context.getResources().getColor(R.color.gris_medio_UCR));
                    }else if(difRanking<0){
                        contadorLikes.setTextColor(context.getResources().getColor(R.color.rojoForo));
                    }
                }
            });
        }

    }
}