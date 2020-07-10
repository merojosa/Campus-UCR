package cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.VerMapaRespuesta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.PreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.RankPreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.RankRespuestaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.RespuestaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankPregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.RankRespuesta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters.RVAdapter;

public class RVAdapterRespuesta extends RecyclerView.Adapter<RVAdapterRespuesta.RespuestaViewHolder> {
    private final Activity context;
    List<Respuesta> respuestas;

    private RankRespuestaViewModel mRankRespuestaViewModel;
    private RespuestaViewModel mRespuestaViewModel;
    private int[] arrayLikes;
    private int[] arrayDislikes;
    private int[] arrayStatusRank;
    private int selectedPosition = -1;
    private String usuarioActual = "";
    public int kGlobal = 0;

    // Instancia de Firebase para el foro general y sus hijos
    ForoGeneralFirebaseDatabase databaseReference;

    public RVAdapterRespuesta(Activity context, List<Respuesta> respuestas) {
        this.context = context;
        this.respuestas = respuestas;

        // Se instancia el firebaseReference y setea el usuario actual
        databaseReference = new ForoGeneralFirebaseDatabase();
        this.usuarioActual = databaseReference.obtenerUsuario();

        mRankRespuestaViewModel = new ViewModelProvider((FragmentActivity) context).get(RankRespuestaViewModel.class);
        mRespuestaViewModel = new ViewModelProvider((FragmentActivity) context).get(RespuestaViewModel.class);
    }

    //set de lista con respuestas
    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;

        if (selectedPosition == -1) {
            llenarArrays();
        }

        notifyDataSetChanged();
    }

    public void llenarArrays() {
        int tam = respuestas.size();
        arrayLikes = new int[tam];
        arrayDislikes = new int[tam];
        arrayStatusRank = new int[tam];

        //se llenan los arrays que se van a usar para el onClick de iconos
        for (int k = 0; k < tam; k++) {
            kGlobal = k;
            arrayLikes[k] = respuestas.get(k).getContadorLikes();
            arrayDislikes[k] = respuestas.get(k).getContadorDislikes();
            arrayStatusRank[k] = 0;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //Contenido de respuesta
    public class RespuestaViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textoRespuesta;
        ImageView iconLike;
        ImageView iconDislike;
        TextView contadorLikes;
        ImageView iconMapa;

        public RespuestaViewHolder(@NonNull View itemView) {
            super(itemView);

            // Elementos del layout
            cardView = itemView.findViewById(R.id.respuestaView);
            textoRespuesta = itemView.findViewById(R.id.textoRespuesta);
            iconLike = itemView.findViewById(R.id.likeIconR);
            iconDislike = itemView.findViewById(R.id.dislikeIconR);
            contadorLikes = itemView.findViewById(R.id.textoContadorR);

            iconMapa = itemView.findViewById(R.id.mapaRespuesta);

            iconMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indice = getAdapterPosition();
                    verMapa(indice);
                }
            });

            //Agarra el click del like en una pregunta y escoge que hacer
            iconLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indexPreg = getAdapterPosition();
                    int idRespTemp = respuestas.get(indexPreg).getId();
                    int idPregTemp = respuestas.get(indexPreg).getPreguntaID();
                    int idTemaTemp = respuestas.get(indexPreg).getTemaID();
                    String textoTemp = respuestas.get(indexPreg).getTexto();
                    int likesTemp = respuestas.get(indexPreg).getContadorLikes();
                    int dislikesTemp = respuestas.get(indexPreg).getContadorDislikes();
                    int isLikedActual = arrayStatusRank[indexPreg];
                    double latTemp = respuestas.get(indexPreg).getLatitud();
                    double lonTemp = respuestas.get(indexPreg).getLongitud();
                    boolean mapAgregadoTemp = respuestas.get(indexPreg).isMapaAgregado();
                    Respuesta respTemp;

                    if (isLikedActual == 1) { //tiene like
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayLikes[indexPreg]--;
                        respTemp = new Respuesta(idRespTemp, usuarioActual, textoTemp, idPregTemp, idTemaTemp, likesTemp - 1, dislikesTemp, latTemp, lonTemp, mapAgregadoTemp);
                        mRespuestaViewModel.update(respTemp);
                        RVAdapterRespuesta.this.databaseReference.getRespuestasRef().child(Integer.toString(idRespTemp))
                                .setValue(respTemp);
                        arrayStatusRank[indexPreg] = 0;
                        mRankRespuestaViewModel.delete(new RankRespuesta(idPregTemp, usuarioActual, 1));
                        RVAdapterRespuesta.this.databaseReference.getRankingsRef().child("respuestas")
                                .child(Integer.toString(idPregTemp)).child(Integer.toString(idRespTemp)).child(usuarioActual).removeValue();
                    } else if (isLikedActual == 2) { //tiene dislike
                        iconLike.setImageResource(R.drawable.ic_thumb_up_green_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayLikes[indexPreg]++;
                        arrayDislikes[indexPreg]--;
                        respTemp = new Respuesta(idRespTemp, usuarioActual, textoTemp, idPregTemp, idTemaTemp, likesTemp + 1, dislikesTemp - 1, latTemp, lonTemp, mapAgregadoTemp);
                        mRespuestaViewModel.update(respTemp);
                        RVAdapterRespuesta.this.databaseReference.getRespuestasRef().child(Integer.toString(idRespTemp))
                                .setValue(respTemp);
                        arrayStatusRank[indexPreg] = 1;
                        RankRespuesta rankRespuesta = new RankRespuesta(idRespTemp, usuarioActual, 1);
                        mRankRespuestaViewModel.update(rankRespuesta);
                        RVAdapterRespuesta.this.databaseReference.getRankingsRef().child("respuestas")
                                .child(Integer.toString(idPregTemp)).child(Integer.toString(idRespTemp)).child(usuarioActual).setValue(rankRespuesta);
                    } else { //no tiene nada todavia
                        iconLike.setImageResource(R.drawable.ic_thumb_up_green_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayLikes[indexPreg]++;
                        respTemp = new Respuesta(idRespTemp, usuarioActual, textoTemp, idPregTemp, idTemaTemp, likesTemp + 1, dislikesTemp, latTemp, lonTemp, mapAgregadoTemp);
                        mRespuestaViewModel.update(respTemp);
                        RVAdapterRespuesta.this.databaseReference.getRespuestasRef().child(Integer.toString(idRespTemp))
                                .setValue(respTemp);
                        RankRespuesta rankRespuesta = new RankRespuesta(idRespTemp, usuarioActual, 1);
                        mRankRespuestaViewModel.insert(rankRespuesta);
                        arrayStatusRank[indexPreg] = 1;
                        RVAdapterRespuesta.this.databaseReference.getRankingsRef().child("respuestas")
                                .child(Integer.toString(idPregTemp)).child(Integer.toString(idRespTemp)).child(usuarioActual).setValue(rankRespuesta);
                    }
                    int difRanking = arrayLikes[indexPreg] - arrayDislikes[indexPreg];
                    contadorLikes.setText(String.valueOf(difRanking));
                    if (difRanking == 0) {
                        contadorLikes.setTextColor(ContextCompat.getColor(context, R.color.gris_medio_UCR));
                    } else if (difRanking > 0) {
                        contadorLikes.setTextColor(ContextCompat.getColor(context, R.color.verde_UCR));
                    } else {
                        contadorLikes.setTextColor(ContextCompat.getColor(context, R.color.rojoForo));
                    }
                }
            });
            //Agarra el click del dislike en una pregunta y escoge que hacer
            iconDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indexPreg = getAdapterPosition();
                    int idRespTemp = respuestas.get(indexPreg).getId();
                    int idPregTemp = respuestas.get(indexPreg).getPreguntaID();
                    int idTemaTemp = respuestas.get(indexPreg).getTemaID();
                    String textoTemp = respuestas.get(indexPreg).getTexto();
                    int likesTemp = respuestas.get(indexPreg).getContadorLikes();
                    int dislikesTemp = respuestas.get(indexPreg).getContadorDislikes();
                    int isLikedActual = arrayStatusRank[indexPreg];
                    double latTemp = respuestas.get(indexPreg).getLatitud();
                    double lonTemp = respuestas.get(indexPreg).getLongitud();
                    boolean mapAgregadoTemp = respuestas.get(indexPreg).isMapaAgregado();
                    Respuesta respTemp;

                    if (isLikedActual == 1) { //tiene like
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_red_24dp);
                        arrayLikes[indexPreg]--;
                        arrayDislikes[indexPreg]++;
                        respTemp = new Respuesta(idRespTemp, usuarioActual, textoTemp, idPregTemp, idTemaTemp, likesTemp - 1, dislikesTemp + 1, latTemp, lonTemp, mapAgregadoTemp);
                        mRespuestaViewModel.update(respTemp);
                        RVAdapterRespuesta.this.databaseReference.getRespuestasRef().child(Integer.toString(idRespTemp))
                                .setValue(respTemp);
                        RankRespuesta rankRespuesta = new RankRespuesta(idRespTemp, usuarioActual, 2);
                        mRankRespuestaViewModel.update(rankRespuesta);
                        arrayStatusRank[indexPreg] = 2;
                        RVAdapterRespuesta.this.databaseReference.getRankingsRef().child("respuestas")
                                .child(Integer.toString(idPregTemp)).child(Integer.toString(idRespTemp)).child(usuarioActual).setValue(rankRespuesta);
                    } else if (isLikedActual == 2) { //tiene dislike
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                        arrayDislikes[indexPreg]--;
                        respTemp = new Respuesta(idRespTemp, usuarioActual, textoTemp, idPregTemp, idTemaTemp, likesTemp, dislikesTemp - 1, latTemp, lonTemp, mapAgregadoTemp);
                        mRespuestaViewModel.update(respTemp);
                        RVAdapterRespuesta.this.databaseReference.getRespuestasRef().child(Integer.toString(idRespTemp))
                                .setValue(respTemp);
                        RankRespuesta rankRespuesta = new RankRespuesta(idRespTemp, usuarioActual, 2);
                        mRankRespuestaViewModel.delete(rankRespuesta);
                        arrayStatusRank[indexPreg] = 0;
                        RVAdapterRespuesta.this.databaseReference.getRankingsRef().child("respuestas")
                                .child(Integer.toString(idPregTemp)).child(Integer.toString(idRespTemp)).child(usuarioActual).removeValue();
                    } else { //no tiene nada todavia
                        iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                        iconDislike.setImageResource(R.drawable.ic_thumb_down_red_24dp);
                        arrayDislikes[indexPreg]++;
                        respTemp = new Respuesta(idRespTemp, usuarioActual, textoTemp, idPregTemp, idTemaTemp, likesTemp, dislikesTemp + 1, latTemp, lonTemp, mapAgregadoTemp);
                        mRespuestaViewModel.update(respTemp);
                        RVAdapterRespuesta.this.databaseReference.getRespuestasRef().child(Integer.toString(idRespTemp))
                                .setValue(respTemp);
                        RankRespuesta rankRespuesta = new RankRespuesta(idRespTemp, usuarioActual, 2);
                        mRankRespuestaViewModel.insert(rankRespuesta);
                        arrayStatusRank[indexPreg] = 2;
                        RVAdapterRespuesta.this.databaseReference.getRankingsRef().child("respuestas")
                                .child(Integer.toString(idPregTemp)).child(Integer.toString(idRespTemp)).child(usuarioActual).setValue(rankRespuesta);
                    }
                    int difRanking = arrayLikes[indexPreg] - arrayDislikes[indexPreg];
                    contadorLikes.setText(String.valueOf(difRanking));
                    if (difRanking == 0) {
                        contadorLikes.setTextColor(ContextCompat.getColor(context, R.color.gris_medio_UCR));
                    } else if (difRanking > 0) {
                        contadorLikes.setTextColor(ContextCompat.getColor(context, R.color.verde_UCR));
                    } else {
                        contadorLikes.setTextColor(ContextCompat.getColor(context, R.color.rojoForo));
                    }
                }
            });


        }
    }

    @NonNull
    @Override
    public RespuestaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foro_general_item_respuesta, viewGroup, false);
        return new RespuestaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RespuestaViewHolder holder, int i) {
        holder.textoRespuesta.setText(respuestas.get(i).texto);

        int difRanking = arrayLikes[i] - arrayDislikes[i];
        holder.contadorLikes.setText(String.valueOf(difRanking));
        if (difRanking > 0) {
            holder.contadorLikes.setTextColor(ContextCompat.getColor(context, R.color.verde_UCR));
        } else if (difRanking < 0) {
            holder.contadorLikes.setTextColor(ContextCompat.getColor(context, R.color.rojoForo));
        } else {
            holder.contadorLikes.setTextColor(ContextCompat.getColor(context, R.color.gris_medio_UCR));
        }
        holder.iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
        holder.iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);

        //Si el mapa esta agregado se despliega el imageview de mapa
        if (respuestas.get(i).isMapaAgregado()) {
            holder.iconMapa.setImageResource(R.drawable.map256);
            holder.iconMapa.setClickable(true);
        }
        //En caso de que no se agrega mapa a respuesta entonces se quita la opcion de hacer click a este imageview de la respuesta
        else {
            holder.iconMapa.setClickable(false);
        }

        String pregString = String.valueOf(respuestas.get(i).getPreguntaID());
        String idString = String.valueOf(respuestas.get(i).getId());

        //busca si el usuario tiene likes o dislikes en las respuesta de esta pregunta especifica
        this.databaseReference.getRankingsRef().child("respuestas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(pregString).exists()) {
                    if (dataSnapshot.child(pregString).child(idString).exists()) {
                        if (dataSnapshot.child(pregString).child(idString).child(usuarioActual).exists()) {
                            arrayStatusRank[i] = dataSnapshot.child(pregString).child(idString).child(usuarioActual).child("isLiked").getValue(Integer.class);
                            switch (arrayStatusRank[i]) {
                                case 1:
                                    holder.iconLike.setImageResource(R.drawable.ic_thumb_up_green_24dp);
                                    holder.iconDislike.setImageResource(R.drawable.ic_thumb_down_grey_24dp);
                                    break;
                                case 2:
                                    holder.iconLike.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
                                    holder.iconDislike.setImageResource(R.drawable.ic_thumb_down_red_24dp);
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
    public int getItemCount() {
        if (respuestas == null) {
            return 0;
        } else {
            return respuestas.size();
        }
    }

    private void verMapa(int indice) {
        Intent intent = new Intent(context.getApplicationContext(), VerMapaRespuesta.class);
        //Para poder inicia una actividad fuera del contexto de una actividad (no es lo mas apropiado)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        double lat = respuestas.get(indice).latitud;
        double lon = respuestas.get(indice).longitud;
        intent.putExtra("latitud", lat);
        intent.putExtra("longitud", lon);
        context.getApplicationContext().startActivity(intent);
    }
}
