package br.com.wellingtoncosta.mymovies.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import javax.inject.Inject;

import br.com.wellingtoncosta.mymovies.Application;
import br.com.wellingtoncosta.mymovies.R;
import br.com.wellingtoncosta.mymovies.domain.FavoriteMovie;
import br.com.wellingtoncosta.mymovies.domain.User;
import br.com.wellingtoncosta.mymovies.retrofit.AuthenticationService;
import br.com.wellingtoncosta.mymovies.ui.fragment.FavoriteMoviesFragment;
import br.com.wellingtoncosta.mymovies.ui.fragment.ListFragment;
import br.com.wellingtoncosta.mymovies.ui.fragment.MoviesFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MoviesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.searchView)
    MaterialSearchView searchView;

    @BindView(R.id.content_movies)
    RelativeLayout contentMovies;

    @Inject
    AuthenticationService authenticationService;

    @Inject
    Realm realm;

    @Inject
    SharedPreferences preferences;

    private Drawer drawer;

    private ListFragment currentFragment;

    private MoviesFragment moviesFragment;

    private FavoriteMoviesFragment favoriteMoviesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Application) getApplication()).component().inject(MoviesActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        moviesFragment = new MoviesFragment();
        favoriteMoviesFragment = new FavoriteMoviesFragment();

        setupMaterialDrawer();
        setupSearchView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentFragment.setQuery(query);
                currentFragment.loadData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() { }

            @Override
            public void onSearchViewClosed() {
                currentFragment.setQuery(null);
                currentFragment.loadData();
            }
        });
    }

    private void setupMaterialDrawer() {
        View header = createHeaderDrawer();
        PrimaryDrawerItem moviesItem = createMoviesDrawerItem();
        PrimaryDrawerItem favoriteMoviesItem = createFavoriteMoviesDrawerItem();
        PrimaryDrawerItem logoutItem = createLogoutDrawerItem();
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(moviesItem, favoriteMoviesItem, logoutItem)
                .withHeader(header)
                .build();

        drawer.setSelection(1);
    }

    private View createHeaderDrawer() {
        View headerView = getLayoutInflater().inflate(R.layout.header, contentMovies, false);
        TextView nameField = (TextView) headerView.findViewById(R.id.nameField);
        TextView emailField = (TextView) headerView.findViewById(R.id.emailField);
        User currentUser = realm.where(User.class).findFirst();

        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());

        return headerView;
    }

    private PrimaryDrawerItem createMoviesDrawerItem() {
        return new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.movies)
                .withIcon(GoogleMaterial.Icon.gmd_list)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(
                                R.id.content_movies,
                                moviesFragment,
                                "MOVIES_FRAGMENT"
                        );

                        currentFragment = moviesFragment;
                        transaction.commit();
                        return false;
                    }
                });
    }

    private PrimaryDrawerItem createFavoriteMoviesDrawerItem() {
        return new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.favorite_movies)
                .withIcon(GoogleMaterial.Icon.gmd_favorite)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(
                                R.id.content_movies,
                                favoriteMoviesFragment,
                                "FAVORITE_MOVIES_FRAGMENT"
                        );
                        currentFragment = favoriteMoviesFragment;
                        transaction.commit();
                        return false;
                    }
                });
    }

    private PrimaryDrawerItem createLogoutDrawerItem() {
        return new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.logout)
                .withIcon(GoogleMaterial.Icon.gmd_exit_to_app)
                .withSelectable(false)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        logout();
                        return false;
                    }
                });
    }

    private void logout() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.delete(FavoriteMovie.class);
                bgRealm.delete(User.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                preferences.edit().clear().apply();
                MoviesActivity.this.startActivity(new Intent(MoviesActivity.this, LoginActivity.class));
                finish();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("exception", error.getMessage(), error);
                Snackbar.make(toolbar, R.string.logout_failure, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
