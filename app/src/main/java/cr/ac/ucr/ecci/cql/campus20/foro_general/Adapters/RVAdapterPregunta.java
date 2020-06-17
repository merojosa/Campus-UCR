package cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.RankPreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerPreguntas;
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
    private String usuarioActual = "";
    ArrayList<Integer> arrayRanks;
    public int kGlobal = 0;

    // Instancia de Firebase para el foro general y sus hijos
    ForoGeneralFirebaseDatabase databaseReference;

    private OnPreguntaListener mOnPreguntaListener;//Listener de clicks para cada pregunta y poder ver sus respuestas

    public RVAdapterPregunta(Activity context, List<PreguntaCard> preguntaCards, OnPreguntaListener onPreguntaListener)
    {

        this.context = context;
        this.preguntaCards = preguntaCards;
        this.picasso = new Picasso.Builder(this.context)
                .indicatorsEnabled(true)
                .loggingEnabled(true) //add other settings as needed
                .build();

        this.mOnPreguntaListener = onPreguntaListener;

        // Se instancia el firebaseReference y setea el usuario actual
        databaseReference = new ForoGeneralFirebaseDatabase();
        this.usuarioActual = databaseReference.obtenerUsuario();

        mRankPreguntaViewModel = new ViewModelProvider((FragmentActivity) context).get(RankPreguntaViewModel.class);
        mPreguntaViewModel = new ViewModelProvider((FragmentActivity) context).get(PreguntaViewModel.class);
    }

    // Setea el set de preguntas
    public void setPreguntaCards(List<Pregunta> preguntas)
    {
        this.preguntaCards = convertToPreguntaCards(preguntas);

        if(selectedPosition == -1){
            llenarArrays();
        }
        notifyDataSetChanged();
    }

    public void llenarArrays(){
        int tam = preguntaCards.size();
        arrayLikes = new int[tam];
        arrayDislikes = new int[tam];
        arrayStatusRank = new int[tam];

        for(int k = 0; k < tam; k++){
            kGlobal = k;
            arrayLikes[k] = preguntaCards.get(k).getContadorLikes();
            arrayDislikes[k] = preguntaCards.get(k).getContadorDislikes();
            arrayStatusRank[k] = 0;

            /*if(arrayRanks.get(k) != 0){
                Log.d("ANDY", "NO es 0!!!");
                arrayStatusRank[k] = arrayRanks.get(k);
                Log.d("ANDY", "arrayRank[k] = " + arrayStatusRank[k]);
            }*/
        }
    }

    // Convierte una lista de preguntas en una lista de cartas de preguntas
    public List<PreguntaCard> convertToPreguntaCards(List<Pregunta> preguntas)
    {
        List<PreguntaCard> cards = new ArrayList<PreguntaCard>();

        for(Pregunta pregunta : preguntas)
        {
            cards.add(
                    new PreguntaCard(pregunta.temaID, pregunta.id, pregunta.texto, pregunta.contadorLikes, pregunta.contadorDisLikes)
            );
        }

        return cards;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Infla la vista de la carta
    @NotNull
    @Override
    public PreguntaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foro_general_pregunta_card, viewGroup, false);

        return new PreguntaViewHolder(v, mOnPreguntaListener);
    }

    // Setea el contenido de la carta con el contenido de la pregunta
    @Override
    public void onBindViewHolder(PreguntaViewHolder preguntaViewHolder, int i)
    {
        Log.d("ANDY", "-----Entra aca-----");
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
        preguntaViewHolder.iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
        preguntaViewHolder.iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);

        String temaString = String.valueOf(preguntaCards.get(i).getTemaID());
        String idString = String.valueOf(preguntaCards.get(i).getId());

        //this.databaseReference.getRankingsRef().child("preguntas").child(temaString).child(idString).child(usuarioActual).child("isLiked")

        this.databaseReference.getRankingsRef().child("preguntas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(temaString).exists()) {
                    if(dataSnapshot.child(temaString).child(idString).exists()){
                        if(dataSnapshot.child(temaString).child(idString).child(usuarioActual).exists()){
                            Log.d("ANDY", "Firebase isLiked = " + dataSnapshot.child(temaString).child(idString).child(usuarioActual).child("isLiked").getValue(Integer.class));
                            arrayStatusRank[i] = dataSnapshot.child(temaString).child(idString).child(usuarioActual).child("isLiked").getValue(Integer.class);
                            Log.d("ANDY", "Rank: " + arrayStatusRank[i]);
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return preguntaCards == null ? 0 : preguntaCards.size();
    }

    // El holder del adapter, aqui va el contenido del card
    public class PreguntaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView cardView;
        TextView textoPregunta;
        ImageView iconLike;
        ImageView iconDislike;
        TextView contadorLikes;

        OnPreguntaListener onPreguntaListener;

        public PreguntaViewHolder(View itemView, OnPreguntaListener onPreguntaListener)
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
                    int idPregTemp = preguntaCards.get(indexPreg).getId();
                    int idTemaTemp = preguntaCards.get(indexPreg).getTemaID();
                    String textoTemp = preguntaCards.get(indexPreg).getTexto();
                    int likesTemp = preguntaCards.get(indexPreg).getContadorLikes();
                    int dislikesTemp = preguntaCards.get(indexPreg).getContadorDislikes();
                    int isLikedActual = arrayStatusRank[indexPreg];
                    Pregunta pregTemp;

                    if(isLikedActual == 1){
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayLikes[indexPreg]--;
                        pregTemp = new Pregunta(idPregTemp,usuarioActual,idTemaTemp,textoTemp,likesTemp-1,dislikesTemp);
                        mPreguntaViewModel.update(pregTemp);
                        RVAdapterPregunta.this.databaseReference.getPreguntasRef().child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp))
                                .setValue(pregTemp);
                        arrayStatusRank[indexPreg] = 0;
                        mRankPreguntaViewModel.delete(new RankPregunta(idPregTemp,usuarioActual,1));
                        RVAdapterPregunta.this.databaseReference.getRankingsRef().child("preguntas")
                                .child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp)).child(usuarioActual).removeValue();
                    }else if(isLikedActual == 2){
                        iconLike.setImageResource(R.drawable.ic_thumb_up_green_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayLikes[indexPreg]++;
                        arrayDislikes[indexPreg]--;
                        pregTemp = new Pregunta(idPregTemp,usuarioActual,idTemaTemp,textoTemp,likesTemp+1,dislikesTemp-1);
                        mPreguntaViewModel.update(pregTemp);
                        RVAdapterPregunta.this.databaseReference.getPreguntasRef().child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp))
                                .setValue(pregTemp);
                        arrayStatusRank[indexPreg] = 1;
                        RankPregunta rankPregunta = new RankPregunta(idPregTemp,usuarioActual,1);
                        mRankPreguntaViewModel.update(rankPregunta);
                        RVAdapterPregunta.this.databaseReference.getRankingsRef().child("preguntas")
                                .child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp)).child(usuarioActual).setValue(rankPregunta);
                    }else{
                        iconLike.setImageResource(R.drawable.ic_thumb_up_green_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayLikes[indexPreg]++;
                        pregTemp = new Pregunta(idPregTemp,usuarioActual,idTemaTemp,textoTemp,likesTemp+1,dislikesTemp);
                        mPreguntaViewModel.update(pregTemp);
                        RVAdapterPregunta.this.databaseReference.getPreguntasRef().child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp))
                                .setValue(pregTemp);
                        RankPregunta rankPregunta = new RankPregunta(idPregTemp,usuarioActual,1);
                        mRankPreguntaViewModel.insert(rankPregunta);
                        arrayStatusRank[indexPreg] = 1;
                        RVAdapterPregunta.this.databaseReference.getRankingsRef().child("preguntas")
                                .child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp)).child(usuarioActual).setValue(rankPregunta);
                    }
                    int difRanking = arrayLikes[indexPreg]-arrayDislikes[indexPreg];
                    contadorLikes.setText(String.valueOf(difRanking));
                    if(difRanking==0){
                        contadorLikes.setTextColor(context.getResources().getColor(R.color.gris_medio_UCR));
                    }else if(difRanking>0){
                        contadorLikes.setTextColor(context.getResources().getColor(R.color.verde_UCR));
                    }else{
                        contadorLikes.setTextColor(context.getResources().getColor(R.color.rojoForo));
                    }
                }
            });
            //Agarra el click del dislike en una pregunta y escoge que hacer
            iconDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indexPreg = getAdapterPosition();
                    int idPregTemp = preguntaCards.get(indexPreg).getId();
                    int idTemaTemp = preguntaCards.get(indexPreg).getTemaID();
                    String textoTemp = preguntaCards.get(indexPreg).getTexto();
                    int likesTemp = preguntaCards.get(indexPreg).getContadorLikes();
                    int dislikesTemp = preguntaCards.get(indexPreg).getContadorDislikes();
                    int isLikedActual = arrayStatusRank[indexPreg];
                    Pregunta pregTemp;

                    if(isLikedActual == 1){
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_red_24dp);
                        arrayLikes[indexPreg]--;
                        arrayDislikes[indexPreg]++;
                        pregTemp = new Pregunta(idPregTemp,usuarioActual,idTemaTemp,textoTemp,likesTemp-1,dislikesTemp+1);
                        mPreguntaViewModel.update(pregTemp);
                        RVAdapterPregunta.this.databaseReference.getPreguntasRef().child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp))
                                .setValue(pregTemp);
                        RankPregunta rankPregunta = new RankPregunta(idPregTemp,usuarioActual,2);
                        mRankPreguntaViewModel.update(rankPregunta);
                        arrayStatusRank[indexPreg] = 2;
                        RVAdapterPregunta.this.databaseReference.getRankingsRef().child("preguntas")
                                .child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp)).child(usuarioActual).setValue(rankPregunta);
                    }else if(isLikedActual == 2){
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayDislikes[indexPreg]--;
                        pregTemp = new Pregunta(idPregTemp,usuarioActual,idTemaTemp,textoTemp,likesTemp,dislikesTemp-1);
                        mPreguntaViewModel.update(pregTemp);
                        RVAdapterPregunta.this.databaseReference.getPreguntasRef().child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp))
                                .setValue(pregTemp);
                        RankPregunta rankPregunta = new RankPregunta(idPregTemp,usuarioActual,2);
                        mRankPreguntaViewModel.delete(rankPregunta);
                        arrayStatusRank[indexPreg] = 0;
                        RVAdapterPregunta.this.databaseReference.getRankingsRef().child("preguntas")
                                .child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp)).child(usuarioActual).removeValue();
                    }else{
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_red_24dp);
                        arrayDislikes[indexPreg]++;
                        pregTemp = new Pregunta(idPregTemp,usuarioActual,idTemaTemp,textoTemp,likesTemp,dislikesTemp+1);
                        mPreguntaViewModel.update(pregTemp);
                        RVAdapterPregunta.this.databaseReference.getPreguntasRef().child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp))
                                .setValue(pregTemp);
                        RankPregunta rankPregunta = new RankPregunta(idPregTemp,usuarioActual,2);
                        mRankPreguntaViewModel.insert(rankPregunta);
                        arrayStatusRank[indexPreg] = 2;
                        RVAdapterPregunta.this.databaseReference.getRankingsRef().child("preguntas")
                                .child(Integer.toString(idTemaTemp)).child(Integer.toString(idPregTemp)).child(usuarioActual).setValue(rankPregunta);
                    }
                    int difRanking = arrayLikes[indexPreg]-arrayDislikes[indexPreg];
                    contadorLikes.setText(String.valueOf(difRanking));
                    if(difRanking==0){
                        contadorLikes.setTextColor(context.getResources().getColor(R.color.gris_medio_UCR));
                    }else if(difRanking<0){
                        contadorLikes.setTextColor(context.getResources().getColor(R.color.rojoForo));
                    }else{
                        contadorLikes.setTextColor(context.getResources().getColor(R.color.verde_UCR));
                    }
                }
            });

            this.onPreguntaListener = onPreguntaListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPreguntaListener.onPreguntaClick(getAdapterPosition());
        }
    }

    // Para agregar el listener de onClick de la pregunta
    public interface OnPreguntaListener {
        void onPreguntaClick(int position);
    }
}