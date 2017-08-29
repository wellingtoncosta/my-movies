package br.com.wellingtoncosta.mymovies.ui.listener;

import android.view.View;

import br.com.wellingtoncosta.mymovies.domain.Movie;

/**
 * @author Wellington Costa on 07/05/17.
 */
public interface OnFavoriteClickListenter {

    void onClick(View view, Movie movie);

}
