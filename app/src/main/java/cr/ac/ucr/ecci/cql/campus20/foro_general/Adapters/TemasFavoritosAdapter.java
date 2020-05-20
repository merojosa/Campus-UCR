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

public class TemasFavoritosAdapter extends RecyclerView.Adapter<TemasFavoritosAdapter.FavoritoViewHolder> {

    // Definición del ViewHolder
    class FavoritoViewHolder extends RecyclerView.ViewHolder {

        private final TextView favoritoNombreView;
        private final TextView favoritoDescritionView;
        private final ImageView favoritoImagen;

        // Constructor
        private FavoritoViewHolder(View itemView)
        {
            super(itemView);
            // Se obtienen los widgets
            favoritoNombreView = itemView.findViewById(R.id.name);
            favoritoDescritionView = itemView.findViewById(R.id.description);
            favoritoImagen = itemView.findViewById(R.id.img);
        }
    }

    // Definición del Inflater y de la lista de Temas Favoritos
    private final LayoutInflater mInflater;
    private List<Favorito> mFavoritos;

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
            holder.favoritoNombreView.setText(Integer.toString(current.getIdTema()));
            holder.favoritoDescritionView.setText("DescripcionPrueba");
            holder.favoritoImagen.setImageResource(R.drawable.foro1);
        }
        else {
            // Covers the case of data not being ready yet.
            holder.favoritoNombreView.setText("No Favorito");
            holder.favoritoDescritionView.setText("Descripcion de que no hay temas favoritos aún");
        }
    }

    public void setFavoritos(List<Favorito> favoritos){
        mFavoritos = favoritos;
        //notifyDataSetChanged();
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
