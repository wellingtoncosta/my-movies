package br.com.wellingtoncosta.mymovies.retrofit;

import br.com.wellingtoncosta.mymovies.domain.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Wellington Costa on 30/04/17.
 */
public interface UserService {

    String ENDPOINT = "api/users";

    @POST(ENDPOINT)
    Call<User> saveNewUser(@Body User user);

}
