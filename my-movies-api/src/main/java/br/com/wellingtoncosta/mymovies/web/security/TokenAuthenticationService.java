package br.com.wellingtoncosta.mymovies.web.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

/**
 * @author  Wellington Costa on 03/05/17.
 */
public class TokenAuthenticationService {

    private static final long EXPIRATION_TIME = 604_000_000; // 7 days
    private static final String SECRET = "S3cr3t";
    private static final String HEADER_AUTHORIZATION = "Authorization";

    public static void addAuthentication(HttpServletResponse res, String username) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        res.addHeader(HEADER_AUTHORIZATION, JWT);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTHORIZATION);
        Authentication authentication = null;

        if (token != null) {
            String email = getEmail(token);

            if (!isNull(email)) {
                authentication = new UsernamePasswordAuthenticationToken(email, null, emptyList());
            }
        }

        return authentication;
    }

    static void expireToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(HEADER_AUTHORIZATION);
        String email = getEmail(token);

        String newToken = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() - EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, newToken);
    }

    private static String getEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
