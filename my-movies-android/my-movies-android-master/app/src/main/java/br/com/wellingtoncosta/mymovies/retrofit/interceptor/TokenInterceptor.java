package br.com.wellingtoncosta.mymovies.retrofit.interceptor;

import android.content.SharedPreferences;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Wellington Costa on 05/05/17.
 */
public class TokenInterceptor implements Interceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFERENCE = "token";

    private SharedPreferences preferences;

    @Inject
    public TokenInterceptor(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (isLogin(request) || isRegisterNewUser(request)) {
            Response response = chain.proceed(request);
            String token = response.header(AUTHORIZATION_HEADER);
            preferences.edit().putString(TOKEN_PREFERENCE, token).apply();
            return response;
        } else {
            String token = preferences.getString(TOKEN_PREFERENCE, "");
            Request newRequest = request.newBuilder().addHeader(AUTHORIZATION_HEADER, token).build();
            return chain.proceed(newRequest);
        }
    }

    private boolean isLogin(Request request) {
        return request.url().toString().endsWith("/login");
    }

    private boolean isRegisterNewUser(Request request) {
        String url = request.url().toString();
        String method = request.method();
        return url.endsWith("/api/users") && method.toUpperCase().equals("POST");
    }

}