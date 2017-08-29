package br.com.wellingtoncosta.mymovies.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.wellingtoncosta.mymovies.Application;
import br.com.wellingtoncosta.mymovies.R;
import br.com.wellingtoncosta.mymovies.domain.FavoriteMovie;
import br.com.wellingtoncosta.mymovies.domain.FavoriteMovieTO;
import br.com.wellingtoncosta.mymovies.ui.MoviesActivity;
import br.com.wellingtoncosta.mymovies.ui.adapter.FavoriteMoviesAdapter;
import br.com.wellingtoncosta.mymovies.ui.listener.OnImageClickListenter;
import butterknife.ButterKnife;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Wellington Costa on 06/05/2017
 */
public class FavoriteMoviesFragment extends ListFragment {

    @Inject
    Gson gson;

    @Inject
    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((Application) getContext().getApplicationContext()).component().inject(FavoriteMoviesFragment.this);

        View view = inflater.inflate(R.layout.fragment_favorite_movies, container, false);

        ButterKnife.bind(this, view);

        setupSwipeRefreshLayout();
        setupRecyclerView();

        ((MoviesActivity) getActivity()).getToolbar().setTitle(R.string.my_favorite_movies);

        return view;
    }

    private boolean hasQuery() {
        return !(query == null || query.isEmpty());
    }

    public void loadData() {
        swipeRefreshLayout.setRefreshing(true);

        if (isConnected()) {
            if (hasQuery()) {
                loadFavoriteMoviesByTitle();
            } else {
                loadAllFavoriteMovies();
            }
        } else {
            loadFavoriteMoviesLocal();
        }
    }

    private void loadAllFavoriteMovies() {
        service.getAllFavoriteMovies().enqueue(new Callback<List<FavoriteMovieTO>>() {
            @Override
            public void onResponse(Call<List<FavoriteMovieTO>> call, Response<List<FavoriteMovieTO>> response) {
                if (response.isSuccessful()) {
                    List<FavoriteMovie> favoriteMovies = adjustFavoriteMovies(response.body());
                    recyclerView.setAdapter(new FavoriteMoviesAdapter(favoriteMovies, onImageClick()));
                    saveFavoriteMoviesLocal(favoriteMovies);
                } else {
                    Snackbar.make(recyclerView, R.string.server_error, Snackbar.LENGTH_LONG).show();
                    recyclerView.setAdapter(new FavoriteMoviesAdapter(Collections.<FavoriteMovie>emptyList(), null));
                }

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<FavoriteMovieTO>> call, Throwable t) {
                onRequestFailure();
            }
        });
    }

    private void loadFavoriteMoviesByTitle() {
        service.getAllFavoriteMoviesByTitle(query).enqueue(new Callback<List<FavoriteMovieTO>>() {
            @Override
            public void onResponse(Call<List<FavoriteMovieTO>> call, Response<List<FavoriteMovieTO>> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    List<FavoriteMovie> favoriteMovies = adjustFavoriteMovies(response.body());
                    recyclerView.setAdapter(new FavoriteMoviesAdapter(favoriteMovies, onImageClick()));
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Snackbar.make(recyclerView, R.string.server_error, Snackbar.LENGTH_LONG).show();
                    recyclerView.setAdapter(new FavoriteMoviesAdapter(Collections.<FavoriteMovie>emptyList(), null));
                }
            }

            @Override
            public void onFailure(Call<List<FavoriteMovieTO>> call, Throwable t) {
                onRequestFailure();
            }
        });
    }

    private void onRequestFailure() {
        swipeRefreshLayout.setRefreshing(false);
        Snackbar.make(recyclerView, R.string.server_communication_error, Snackbar.LENGTH_LONG).show();
        recyclerView.setAdapter(new FavoriteMoviesAdapter(Collections.<FavoriteMovie>emptyList(), null));
    }

    private List<FavoriteMovie> adjustFavoriteMovies(List<FavoriteMovieTO> favoriteMovieTOs) {
        List<FavoriteMovie> favMovies = new ArrayList<>();

        for (FavoriteMovieTO favoriteMovieTO  : favoriteMovieTOs) {
            favMovies.add(new FavoriteMovie(favoriteMovieTO));
        }

        return favMovies;
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

    private boolean isConnected() {
        ConnectivityManager connectivityManager = ((ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    private void loadFavoriteMoviesLocal() {
        RealmQuery<FavoriteMovie> favoriteMovieRealmQuery = realm.where(FavoriteMovie.class);

        if (hasQuery()) {
            favoriteMovieRealmQuery.contains("movieTitle", query, Case.INSENSITIVE);
        }

        List<FavoriteMovie> favoriteMovies = favoriteMovieRealmQuery.findAll();
        recyclerView.setAdapter(new FavoriteMoviesAdapter(favoriteMovies, null));
        swipeRefreshLayout.setRefreshing(false);
    }

    private void saveFavoriteMoviesLocal(final List<FavoriteMovie> favoriteMovies ) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.delete(FavoriteMovie.class);

                for (FavoriteMovie favoriteMovie : favoriteMovies) {
                    bgRealm.copyToRealm(favoriteMovie);
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("exception", error.getMessage(), error);
                Snackbar.make(recyclerView, R.string.save_data_local_failure, Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
