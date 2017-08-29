package br.com.wellingtoncosta.mymovies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Wellington Costa on 30/04/17.
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorite_movie")
public class FavoriteMovie {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorite_movie_sequence")
    @SequenceGenerator(name = "favorite_movie_sequence", sequenceName = "favorite_movie_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @JoinColumn(name = "movie")
    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    @NotNull
    @JoinColumn(name = "\"user\"")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
