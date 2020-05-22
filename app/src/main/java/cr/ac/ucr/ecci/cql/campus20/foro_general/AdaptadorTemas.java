package cr.ac.ucr.ecci.cql.campus20.foro_general;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

public class AdaptadorTemas extends RecyclerView.Adapter<AdaptadorTemas.TemaViewHolder> {

    // Define variable del listener
    private OnItemClickListener listener;

    // Define la interfaz del listener
    public interface OnItemClickListener {

        void onItemClick(View itemView, int position);
        // Método de la interfaz para el ToggleButton
        void onHeartClick(boolean check, int position);
    }

    // Define el método que permite a la actividad o fragmento llamar al listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Definición del ViewHolder
    public class TemaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView temaNombreView;
        private final TextView temaDescritionView;
        private final ImageView temaImagen;
        private final ToggleButton favoritoBoton;

        // Constructor del ViewHolder
        public TemaViewHolder(View itemView) {
            super(itemView);
            temaNombreView = itemView.findViewById(R.id.nombreTema);
            temaDescritionView = itemView.findViewById(R.id.descripcionTema);
            temaImagen = itemView.findViewById(R.id.imagenTema);
            favoritoBoton = itemView.findViewById(R.id.botonFollow);

            // Setup del click listener
            itemView.setOnClickListener(this);

            favoritoBoton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean botonChequeado = favoritoBoton.isChecked();

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // Invoca al onHeartClick del botón de favorito
                            listener.onHeartClick(botonChequeado, position);
                        }
                    }
                }
            });
        }
        @Override
        public void onClick(View v) {
            // Triggers click upwards to the adapter on click
//            if (listener != null) {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    listener.onItemClick(itemView, position);
//                }
//            }
        }
    }

    private final LayoutInflater mInflater;
    private List<Tema> mTemas; // Cached copy of Temas
    private List<Favorito> mFavoritos;  // Cached copy of Favoritos

    AdaptadorTemas(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public TemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_follow_tema, parent, false);
        return new TemaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TemaViewHolder holder, int position) {
        if (mTemas != null) {
            Tema current = mTemas.get(position);
            holder.temaNombreView.setText(current.getTitulo());
            holder.temaDescritionView.setText(current.getDescription());
            holder.temaImagen.setImageResource(current.getImagen());

            // Chequea el estado de cada ícono de Favorito
            if (mFavoritos != null) {
                if (isFollowed(current.getId()) )
                {
                    holder.favoritoBoton.setChecked(true);
                }
                else
                {
                    holder.favoritoBoton.setChecked(false);
                }
            }

        } else {
            // Covers the case of data not being ready yet.
            holder.temaNombreView.setText("No Tema wey");
            holder.temaDescritionView.setText("Nada wey");
        }
    }

    /**
     * Método que en caso de que la lista de temas haya sufrido cambios, se vuelva
     * a asignar a la lista local mTemas
     * @param temas
     */
    void setTemas(List<Tema> temas){
        mTemas = temas;
        notifyDataSetChanged();
    }


    /**
     * Método que en caso de que la lista de favoritos haya sufrido cambios, se vuelva
     * a asignar a la lista local mFavoritos
     * @param favoritos, que es la lista actual de los temas marcados como favoritos
     */
    void setFavoritos(List<Favorito> favoritos){
        mFavoritos = favoritos;
        notifyDataSetChanged();
    }

    /**
     * Método usado para saber si el tema está dentro de la lista de temas marcados como favoritos
     * @param id, que es el identificador del tema favorito
     * @return true o false, dependiendo de si el tema está o no dentro de los favoritos
     */
    boolean isFollowed(int id)
    {
        for(int i = 0; i < mFavoritos.size(); ++i)
        {
            // Obtiene el id del tema a chequear, a ver si coincide con el id del tema
            // marcado como favorito
            if (mFavoritos.get(i).getIdTema() == id)
                return true;
        }
        return false;
    }


    @Override
    public int getItemCount() {
        if (mTemas != null)
            return mTemas.size();
        else return 0;
    }
}
