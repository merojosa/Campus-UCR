package cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

public class TemasFavoritosAdapter extends RecyclerView.Adapter<TemasFavoritosAdapter.FavoritoViewHolder> {

    // Define variable del listener
    private OnItemClickListener listener;

    // Define el listener interface para ponerlo en cada item del RecyclewView
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define el método que permite a la actividad o fragmento llamar al listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Definición del ViewHolder
    public class FavoritoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView favoritoNombreView;
        private final TextView favoritoDescritionView;
        private final ImageView favoritoImagen;

        // Constructor
        public FavoritoViewHolder(View itemView)
        {
            super(itemView);
            // Se obtienen los widgets
            favoritoNombreView = itemView.findViewById(R.id.nameTema);
            favoritoDescritionView = itemView.findViewById(R.id.description);
            favoritoImagen = itemView.findViewById(R.id.img);

            // Setup the click listener
            itemView.setOnClickListener(this);
        }

        // Se sobre escribe el metodo onClick, para el evento de captura del Toggle
        @Override
        public void onClick(View v) {
            // Triggers click upwards to the adapter on click
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, position);
                }
            }
        }
    }

    // Definición del Inflater y de la lista de Temas Favoritos
    private final LayoutInflater mInflater;
    private List<Favorito> mFavoritos;

    // Traer todos los temas favoritos
    private List<Tema> mTemas;

    public TemasFavoritosAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public FavoritoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_tema, parent, false);
        return new FavoritoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavoritoViewHolder holder, int position) {

        if (mFavoritos != null && mFavoritos.size() > 0) {
            // Se instancia cada tema favorito
            Favorito current = mFavoritos.get(position);

            // Se obtiene la posición del tema que se encuentra como favorito
            int posTema = posicionTema(current.getIdTema());

            // Evitar que se caiga la app porque en este punto los temas aún no están jalados de la
            // BD (ver porqué)
            if (mTemas != null && mTemas.size() > 0)
            {
                holder.favoritoNombreView.setText(mTemas.get(posTema).getTitulo());
                holder.favoritoDescritionView.setText(mTemas.get(posTema).getDescription());
                holder.favoritoImagen.setImageResource(mTemas.get(posTema).getImagen());
            }

        }
        else {
            // Covers the case of data not being ready yet.
            holder.favoritoNombreView.setText("No Favorito");
            holder.favoritoDescritionView.setText("Descripcion de que no hay temas favoritos aún");
        }
    }

    /**
     * Método que en caso de que la lista de temas haya sufrido cambios, se vuelva
     * a asignar a la lista local mTemas
     * @param temas
     */
    public void setTemas(List<Tema> temas)
    {
        mTemas = temas;
    }

    /**
     * Método que en caso de que la lista de favoritos haya sufrido cambios, se vuelva
     * a asignar a la lista local mFavoritos
     * @param favoritos, que es la lista actual de los temas marcados como favoritos
     */
    public void setFavoritos(List<Favorito> favoritos){
        mFavoritos = favoritos;
        notifyDataSetChanged();
    }

    /**
     * Método usado para saber si el tema está dentro de la lista de temas marcados como favoritos
     * @param idFavorito, que es el identificador del tema favorito
     * @return true o false, dependiendo de si el tema está o no dentro de los favoritos
     */
    int posicionTema(int idFavorito)
    {
        for(int i = 0; i < mTemas.size(); ++i)
        {
            if (mTemas.get(i).getId() == (idFavorito))
                return i;
        }
        return 0;
    }


    @Override
    public int getItemCount() {
        if (mFavoritos != null)
            return mFavoritos.size();
        else return 0;
    }
}
