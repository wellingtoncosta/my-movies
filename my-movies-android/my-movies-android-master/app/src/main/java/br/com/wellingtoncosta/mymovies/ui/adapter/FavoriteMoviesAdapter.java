package br.com.wellingtoncosta.mymovies.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import br.com.wellingtoncosta.mymovies.R;
import br.com.wellingtoncosta.mymovies.domain.FavoriteMovie;
import br.com.wellingtoncosta.mymovies.ui.listener.OnImageClickListenter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Wellington Costa on 06/05/2017
 */
public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMoviesViewHolder> {

    private List<FavoriteMovie> favoriteMovies;
    private OnImageClickListenter imageClickListenter;

    public FavoriteMoviesAdapter(List<FavoriteMovie> favoriteMovies, OnImageClickListenter imageClickListenter) {
        this.favoriteMovies = favoriteMovies;
        this.imageClickListenter = imageClickListenter;
    }

    @Override
    public int getItemCount() {
        return this.favoriteMovies.size();
    }

    @Override
    public FavoriteMoviesAdapter.FavoriteMoviesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_favorite_movies_item, viewGroup, false);
        return new FavoriteMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteMoviesViewHolder viewHolder, int position) {
        viewHolder.bind(this.favoriteMovies.get(position), imageClickListenter);
    }


    static class FavoriteMoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movieImage)
        SimpleDraweeView movieImage;

        @BindView(R.id.movieTitle)
        TextView movieTitle;

        @BindView(R.id.movieGenre)
        TextView movieGenre;

        @BindView(R.id.movieYear)
        TextView movieYear;

        FavoriteMoviesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(final FavoriteMovie favoriteMovie, final OnImageClickListenter imageClickListenter) {
            movieTitle.setText(favoriteMovie.getMovieTitle());
            movieGenre.setText(favoriteMovie.getMovieGenre());
            movieYear.setText(favoriteMovie.getMovieYear());
            loadMovieImage(favoriteMovie.getMovieImageUrl(), imageClickListenter);
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

            if (imageClickListenter != null) {
                movieImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageClickListenter.onClick(v, imageUrl);
                    }
                });
            }
        }
    }
}
