package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

/**Clase para el paso de datos y el layout*/

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import cr.ac.ucr.ecci.cql.campus20.R;

public class ComunidadAdapter extends RecyclerView.Adapter<ComunidadAdapter.ComunidadViewHolder>
{
    private ArrayList<Comunidad> mComunidadList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;

    }

    //Holder para la instanciación de los elementos del layout y asignación de valores a los mismos
    public static class ComunidadViewHolder extends RecyclerView.ViewHolder
    {
        //Variables para el manejo de las instancias de los views
        public TextView mTextViewCommunityName;
        public ImageView mImageView;
        public TextView mTextViewNoMembers;
        public TextView mTextViewDescription;
        public Button buttonJoinCommunity;

        public ComunidadViewHolder(View itemView, OnItemClickListener listener)
        {
            super(itemView);
            //Referencias de los views del layout
            mTextViewCommunityName = itemView.findViewById(R.id.text_Community_Name);
            mImageView = itemView.findViewById(R.id.image_Community);
            mTextViewNoMembers = itemView.findViewById(R.id.text_No_Members);
            mTextViewDescription = itemView.findViewById(R.id.text_Description_Content);

            //Definición del comportamiento de un card cuando es pulsado
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    //Constructor que recibe como parámetro la lista con las comunidades por desplegar
    public ComunidadAdapter(ArrayList<Comunidad> comunidadList)
    {
        mComunidadList = comunidadList;
    }

    @NonNull
    @Override
    //Función para pasar el layout al adaptador
    public ComunidadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creación de la vista asociada al layout para el despliege de los cards que están en el recyclerviewer
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comunidad, parent, false );
        //Asignación de la vista recién creada al holder
        ComunidadViewHolder evh = new ComunidadViewHolder(v, mListener);
        return evh;
    }

    @Override
    // Método que asigna a cada uno de los views la información que tiene que mostrar según la comunidad que corresponda
    public void onBindViewHolder(@NonNull ComunidadViewHolder holder, int position)
    {
        Comunidad currentCommunity = mComunidadList.get(position);  //Selección de una comunidad específica para el despliegue de sus detalles

        //Asignación de los valores por mostrar en la vista
        holder.mTextViewCommunityName.setText(currentCommunity.getCommunityName());
        holder.mTextViewNoMembers.setText("Miembros: "+currentCommunity.getCommunityNoMembers());
        holder.mImageView.setImageResource(currentCommunity.getCommunityImgRes());
        holder.mTextViewDescription.setText(currentCommunity.getCommunityDescription());
    }

    @Override
    //Función que retorna la cantidad de elementos que contendrá el recyclerviewer
    public int getItemCount()
    {
        return mComunidadList.size();
    }
}
