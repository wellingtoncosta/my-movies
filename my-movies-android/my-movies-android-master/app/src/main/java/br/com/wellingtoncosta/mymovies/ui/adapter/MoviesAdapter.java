package br.com.wellingtoncosta.mymovies.ui.adapter;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import br.com.wellingtoncosta.mymovies.R;
import br.com.wellingtoncosta.mymovies.domain.Movie;
import br.com.wellingtoncosta.mymovies.ui.listener.OnFavoriteClickListenter;
import br.com.wellingtoncosta.mymovies.ui.listener.OnImageClickListenter;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Wellington Costa on 06/05/2017
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movies;
    private OnFavoriteClickListenter favoriteClickListenter;
    private OnImageClickListenter imageClickListenter;

    public MoviesAdapter(
            List<Movie> movies,
            OnFavoriteClickListenter favoriteClickListenter,
            OnImageClickListenter imageClickListenter) {
        this.movies = movies;
        this.favoriteClickListenter = favoriteClickListenter;
        this.imageClickListenter = imageClickListenter;
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_movies_item, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder viewHolder, int position) {
        viewHolder.bind(this.movies.get(position), favoriteClickListenter, imageClickListenter);
    }


    static class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movieImage)
        SimpleDraweeView movieImage;

        @BindView(R.id.movieTitle)
        TextView movieTitle;

        @BindView(R.id.movieGenre)
        TextView movieGenre;

        @BindView(R.id.movieYear)
        TextView movieYear;

        @BindView(R.id.favoriteButton)
        ImageView favoriteButton;

        @BindDrawable(R.drawable.ic_favorite_border)
        Drawable favoriteBorder;

        @BindDrawable(R.drawable.ic_favorite_red)
        Drawable favoriteRed;

        MoviesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(
                final Movie movie,
                final OnFavoriteClickListenter favoriteClickListenter,
                final OnImageClickListenter imageClickListenter) {
            movieTitle.setText(movie.getTitle());
            movieGenre.setText(movie.getGenre());
            movieYear.setText(movie.getYear());

            favoriteButton.setImageDrawable(movie.isFavorite() ? favoriteRed : favoriteBorder);

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favoriteClickListenter.onClick(v, movie);
                    favoriteButton.setClickable(false);
                }
            });

            loadMovieImage(movie.getImageUrl(), imageClickListenter);
        }

        private void loadMovieImage(final String imageUrl, final OnImageClickListenter imageClickListenter) {
            final Uri imageUri = Uri.parse(String.valueOf(imageUrl));

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                    .setResizeOptions(new ResizeOptions(100, 120))
                    .setProgressiveRenderingEnabled(true)
                    .setLocalThumbnailPreviewsEnabled(true)
                    .build();

            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .build();

            movieImage.setController(controller);
            movieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageClickListenter.onClick(v, imageUrl);
                }
            });
        }
    }
}
