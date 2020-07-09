package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.R;

public class CommentsList extends RecyclerView.Adapter<CommentsList.MyViewHolderComments> {

    private List<Comment> temp;
    Context context;


    private final CommentListOnClickHandler mClickHandler;


    public interface CommentListOnClickHandler{
        void onClick(int position, boolean like);
        void onClick(Comment comment);
    }

    public CommentsList(CommentListOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    public class MyViewHolderComments extends RecyclerView.ViewHolder{

        public TextView mComment;
        public TextView mLike;
        public TextView mDislike;
        public Button cLike;
        public Button cDislike;

        public MyViewHolderComments(View view) {
            super(view);
            mComment = (TextView) view.findViewById(R.id.comment);
            cLike = (Button) view.findViewById(R.id.clike);
            cDislike = (Button) view.findViewById(R.id.cdislike);
            mLike = (TextView) view.findViewById(R.id.no_like);
            mDislike = (TextView) view.findViewById(R.id.no_dislike);

            mComment.setOnClickListener(v -> mClickHandler.onClick(temp.get(getAdapterPosition())));

            cLike.setOnClickListener(v -> {
                /*Triggers listener to save changes, changes button color and locks button.*/
                mClickHandler.onClick(getAdapterPosition(), true);
                //DrawableCompat.setTint(cLike.getCompoundDrawables()[0], ContextCompat.getColor(view.getContext(), R.color.verde_UCR));
                cLike.setClickable(false);
            });
            cDislike.setOnClickListener(v -> {
                mClickHandler.onClick(getAdapterPosition(), false);
                //DrawableCompat.setTint(cDislike.getCompoundDrawables()[0], ContextCompat.getColor(view.getContext(), R.color.rojoForo));
                cDislike.setClickable(false);
            });

        }


    }

    @NonNull
    @Override
    public MyViewHolderComments onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popup, parent, false);
        return new MyViewHolderComments(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolderComments holder, int position) {
        Comment comment = temp.get(position);
        holder.mComment.setText(comment.getDescription());
        holder.mLike.setText(String.valueOf(comment.getLike()));
        holder.mDislike.setText(String.valueOf(comment.getDislike()));
    }

    @Override
    public int getItemCount() {
        if(temp != null)
            return temp.size();
        else
            return 0;
    }

    public void setListData(List<Comment> comments){
        temp = comments;
        notifyDataSetChanged();
    }

    public void orderByRating(boolean asc){
        Collections.sort(temp, (o1, o2) -> {
            if(asc){
                return Float.compare(o1.getRating(), o2.getRating());
            }else{
                return Float.compare(o2.getRating(), o1.getRating());
            }
        });
        this.notifyDataSetChanged();
    }
}
