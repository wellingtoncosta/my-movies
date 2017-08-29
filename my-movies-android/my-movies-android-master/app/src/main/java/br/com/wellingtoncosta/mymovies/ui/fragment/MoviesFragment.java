package br.com.wellingtoncosta.mymovies.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import br.com.wellingtoncosta.mymovies.Application;
import br.com.wellingtoncosta.mymovies.R;
import br.com.wellingtoncosta.mymovies.domain.Movie;
import br.com.wellingtoncosta.mymovies.ui.MoviesActivity;
import br.com.wellingtoncosta.mymovies.ui.adapter.MoviesAdapter;
import br.com.wellingtoncosta.mymovies.ui.listener.OnFavoriteClickListenter;
import br.com.wellingtoncosta.mymovies.ui.listener.OnImageClickListenter;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Wellington Costa on 06/05/2017
 */
public class MoviesFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((Application) getContext().getApplicationContext()).component().inject(MoviesFragment.this);

        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        ButterKnife.bind(this, view);

        setupSwipeRefreshLayout();
        setupRecyclerView();

        ((MoviesActivity) getActivity()).getToolbar().setTitle(R.string.my_movies);

        return view;
    }

    public void loadData() {
        swipeRefreshLayout.setRefreshing(true);

        Call<List<Movie>> call = (query == null || query.isEmpty()  ) ?
                service.getAllMovies() :
                service.getAllMoviesByTitle(query);

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    recyclerView.setAdapter(new MoviesAdapter(response.body(), onFavoriteClick(), onImageClick()));
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Snackbar.make(recyclerView, R.string.server_error, Snackbar.LENGTH_LONG).show();
                    recyclerView.setAdapter(new MoviesAdapter(Collections.<Movie>emptyList(), null, null));
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(recyclerView, R.string.no_internet_connection, Snackbar.LENGTH_LONG).show();
                recyclerView.setAdapter(new MoviesAdapter(Collections.<Movie>emptyList(), null, null));
            }
        });
    }

    private OnImageClickListenter onImageClick() {
        return new OnImageClickListenter() {
            @Override
            public void onClick(View view, String imageUrl) {
                Uri imageUri = Uri.parse(imageUrl);

                if (imageUri != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, imageUri));
                }
            }
        };
    }

    private OnFavoriteClickListenter onFavoriteClick() {
        return new OnFavoriteClickListenter() {
            @Override
            public void onClick(View view, Movie movie) {
                Call<ResponseBody> call = movie.isFavorite() ?
                        service.unfavoriteMovie(movie.getId()) :
                        service.favoriteMovie(movie);

                swipeRefreshLayout.setRefreshing(true);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        loadData();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                        Snackbar.make(recyclerView, R.string.no_internet_connection, Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        };
    }
}
