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

            // Setup the click listener
            itemView.setOnClickListener(this);

            favoritoBoton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean botonChequeado = favoritoBoton.isChecked();
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
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
            holder.temaImagen.setImageResource(R.drawable.foro1);

            // CODIGO PARA CHEQUEAR EL ESTADO DE CADA CORAZÓN
            // CON BASE EN LA LISTA DE mFavoritos que es la lista instanciada y que está
            // EN CACHÉ!!
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

    void setTemas(List<Tema> temas){
        mTemas = temas;
        notifyDataSetChanged();
    }

    void setFavoritos(List<Favorito> favoritos){
        mFavoritos = favoritos;
        notifyDataSetChanged();
    }

    // MÉTODO PARA SABER SI LA PERSONA QUE ESTÁ SIENDO RENDERIZADA O DIBUJADA
    // ESTÁ TAMBIÉN DENTRO DE LA LISTA DE FOLLOWS, PARA DE UNA VEZ MARCAR SU
    // CORAZÓN ROJO
    boolean isFollowed(int id)
    {
        for(int i = 0; i < mFavoritos.size(); ++i)
        {
            if (mFavoritos.get(i).getIdTema() == id)
                return true;
        }
        return false;
    }
//
//    private Context context;
//    private List<Tema> listItems;
//
//    /**
//     * Constructor del adaptador personalizado de temas
//     *
//     * @param context   indica en que contexto se encuentra el objeto actual (la lista)
//     */
//    public AdaptadorTemas(Context context) {
//        this.context = context;
//    }
//
//    /**
//     * lleva la cuenta de cuantos elementos hay en la lista
//     *
//     * @return listItems.size que es el numero total de elementos en la lista
//     */
//    @Override
//    public int getCount() {
//        if (listItems != null)
//            return listItems.size();
//        else
//            return 0;
//    }
//
//    /**
//     * Este método devuelve el objeto que se encuentra en la posición dada
//     *
//     * @param position es la posición en el array del objeto actual
//     * @return
//     */
//    @Override
//    public Object getItem(int position) {
//        return listItems.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    /**
//     * Este método procesa los elementos para que puedan ser presentados como una lista
//     *
//     * @param position    la posición actual en la lista
//     * @param convertView espacio para la lista personalizada
//     * @param parent
//     * @return la lista a presentar
//     */
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Tema Item = (Tema) getItem(position);
//        View rowView;
//
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            rowView = inflater.inflate(R.layout.item_follow_tema, null);
//        } else {
//            rowView = convertView;
//        }
//
//        ImageView img = rowView.findViewById(R.id.imagenTema);
//        TextView name = rowView.findViewById(R.id.nombreTema);
//        TextView description = rowView.findViewById(R.id.descripcionTema);
//
//        img.setImageResource(Item.getImg());
//        name.setText(Item.getTitulo());
//        description.setText(Item.getDescription());
//        return rowView;
//    }
//

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTemas != null)
            return mTemas.size();
        else return 0;
    }
}
