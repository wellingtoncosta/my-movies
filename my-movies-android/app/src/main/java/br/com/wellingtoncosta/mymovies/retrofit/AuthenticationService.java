package br.com.wellingtoncosta.mymovies.retrofit;

import br.com.wellingtoncosta.mymovies.domain.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Wellington Costa on 03/05/17.
 */

public interface AuthenticationService {

    @POST("login")
    Call<User> login(@Body User user);

}
