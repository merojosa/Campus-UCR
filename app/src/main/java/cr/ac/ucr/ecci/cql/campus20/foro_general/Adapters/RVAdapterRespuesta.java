package cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

public class RVAdapterRespuesta extends RecyclerView.Adapter<RVAdapterRespuesta.RespuestaViewHolder>
{
    private final Activity context;
    List<Respuesta> respuestas;

    public RVAdapterRespuesta(Activity context, List<Respuesta> respuestas) {
        this.context = context;
        this.respuestas = respuestas;
    }

    //set de lista con respuestas
    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //Contenido de respuesta
    public class RespuestaViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textoRespuesta;

        public RespuestaViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.respuestaView);
            textoRespuesta = itemView.findViewById(R.id.textoRespuesta);
        }
    }

    @NonNull
    @Override
    public RespuestaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foro_general_item_respuesta, viewGroup, false);
        return new RespuestaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RespuestaViewHolder holder, int position) {
        holder.textoRespuesta.setText(respuestas.get(position).texto);
    }

    @Override
    public int getItemCount() {
        if (respuestas == null) {
            return 0;
        } else {
            return respuestas.size();
        }
    }
}
