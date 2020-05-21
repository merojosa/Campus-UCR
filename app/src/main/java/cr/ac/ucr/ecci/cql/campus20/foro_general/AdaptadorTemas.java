package cr.ac.ucr.ecci.cql.campus20.foro_general;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

public class AdaptadorTemas extends BaseAdapter {
    private Context context;
    private List<Tema> listItems;

    /**
     * Constructor del adaptador personalizado de temas
     *
     * @param context   indica en que contexto se encuentra el objeto actual (la lista)
     */
    public AdaptadorTemas(Context context) {
        this.context = context;
    }

    /**
     * lleva la cuenta de cuantos elementos hay en la lista
     *
     * @return listItems.size que es el numero total de elementos en la lista
     */
    @Override
    public int getCount() {
        if (listItems != null)
            return listItems.size();
        else
            return 0;
    }

    /**
     * Este método devuelve el objeto que se encuentra en la posición dada
     *
     * @param position es la posición en el array del objeto actual
     * @return
     */
    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Este método procesa los elementos para que puedan ser presentados como una lista
     *
     * @param position    la posición actual en la lista
     * @param convertView espacio para la lista personalizada
     * @param parent
     * @return la lista a presentar
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tema Item = (Tema) getItem(position);
        View rowView;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.item_follow_tema, null);
        } else {
            rowView = convertView;
        }

        ImageView img = rowView.findViewById(R.id.imagenTema);
        TextView name = rowView.findViewById(R.id.nombreTema);
        TextView description = rowView.findViewById(R.id.descripcionTema);

        img.setImageResource(Item.getImg());
        name.setText(Item.getTitulo());
        description.setText(Item.getDescription());
        return rowView;
    }

    public void setTemas(List<Tema> temas) {
        listItems = temas;
    }
}
