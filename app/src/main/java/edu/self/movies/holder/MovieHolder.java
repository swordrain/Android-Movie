package edu.self.movies.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import edu.self.movies.R;

/**
 * Created by lianli on 2017/3/5.
 */

public class MovieHolder extends ViewHolder {
    private CardView cardView;
    private SimpleDraweeView imageView;
    private TextView cnNameTextView;
    private TextView enNameTextView;
    private TextView yearTextView;


    public MovieHolder(View view){
        super(view);
        cardView = (CardView)view.findViewById(R.id.movie_item_card);
        imageView = (SimpleDraweeView)view.findViewById(R.id.movie_item_image);
        cnNameTextView = (TextView)view.findViewById(R.id.movie_item_cn);
        enNameTextView = (TextView)view.findViewById(R.id.movie_item_en);
        yearTextView = (TextView)view.findViewById(R.id.movie_item_year);
    }

    public CardView getCardView() {
        return cardView;
    }

    public SimpleDraweeView getImageView() {
        return imageView;
    }

    public TextView getCnNameTextView() {
        return cnNameTextView;
    }

    public TextView getEnNameTextView() {
        return enNameTextView;
    }

    public TextView getYearTextView() {
        return yearTextView;
    }
}
