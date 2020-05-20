package cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedHashSet;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Favorito;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

public class TemasFavoritosAdapter extends RecyclerView.Adapter<TemasFavoritosAdapter.FavoritoViewHolder> {

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface para ponerlo en cada item del RecyclewView
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
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

    // PRUEBA PARA TRAER TODOS LOS TEMAS ALMACENADOS
    private List<Tema> mTemas;

    public TemasFavoritosAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public FavoritoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_tema, parent, false);
        return new FavoritoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavoritoViewHolder holder, int position) {

        if (mFavoritos != null) {
            // Se instancia cada tema favorito
            Favorito current = mFavoritos.get(position);
            int posTema = posicionTema(current.getIdTema());
            holder.favoritoNombreView.setText(mTemas.get(posTema).getTitulo());
            holder.favoritoDescritionView.setText(mTemas.get(posTema).getDescription());

            int id = holder.itemView.getContext().getResources().getIdentifier("foro_" +
                    mTemas.get(posTema).getTitulo().toLowerCase(), "drawable",
                    holder.itemView.getContext().getPackageName());

            if (mTemas.get(posTema).getTitulo().equals("General"))
                id = holder.itemView.getContext().getResources().getIdentifier("foro1", "drawable",
                        holder.itemView.getContext().getPackageName());
            holder.favoritoImagen.setImageResource(id);

        }
        else {
            // Covers the case of data not being ready yet.
            holder.favoritoNombreView.setText("No Favorito");
            holder.favoritoDescritionView.setText("Descripcion de que no hay temas favoritos aún");
        }
    }

    public void setTemas(List<Tema> temas)
    {
        mTemas = temas;
    }

    public void setFavoritos(List<Favorito> favoritos){
        mFavoritos = favoritos;
        notifyDataSetChanged();
    }

    // Método para devolver la posición del tema que se está por renderizar
    int posicionTema(int idFavorito)
    {
        for(int i = 0; i < mTemas.size(); ++i)
        {
            if (mTemas.get(i).getId() == (idFavorito))
                return i;
        }
        return 0;
    }

    // getItemCount() is called many times, and when it is first called,
    // mFavoritos has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mFavoritos != null)
            return mFavoritos.size();
        else return 0;
    }
}
