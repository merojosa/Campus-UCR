package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    public static class ComunidadViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextViewCommunityName;
        public ImageView mImageView;
        public TextView mTextViewNoMembers;
        public TextView mTextViewDescription;
        public Button buttonJoinCommunity;

        public ComunidadViewHolder(View itemView, OnItemClickListener listener)
        {
            super(itemView);
            mTextViewCommunityName = itemView.findViewById(R.id.text_Community_Name);
            mImageView = itemView.findViewById(R.id.image_Community);
            mTextViewNoMembers = itemView.findViewById(R.id.text_No_Members);
            mTextViewDescription = itemView.findViewById(R.id.text_Description_Content);

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

    public ComunidadAdapter(ArrayList<Comunidad> comunidadList)
    {
        mComunidadList = comunidadList;
    }

    @NonNull
    @Override
    public ComunidadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comunidad, parent, false );
        ComunidadViewHolder evh = new ComunidadViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ComunidadViewHolder holder, int position)
    {
        Comunidad currentCommunity = mComunidadList.get(position);

        holder.mTextViewCommunityName.setText(currentCommunity.getCommunityName());
        holder.mTextViewNoMembers.setText(currentCommunity.getCommunityNoMembers());
        holder.mImageView.setImageResource(currentCommunity.getCommunityImgRes());
        holder.mTextViewDescription.setText(currentCommunity.getCommunityDescription());
    }

    @Override
    public int getItemCount()
    {
        return mComunidadList.size();
    }
}
