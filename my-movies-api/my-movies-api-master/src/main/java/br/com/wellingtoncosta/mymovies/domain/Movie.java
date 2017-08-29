package br.com.wellingtoncosta.mymovies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Wellington Costa on 29/04/17.
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie")
public class Movie {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sequence")
    @SequenceGenerator(name = "movie_sequence", sequenceName = "movie_id_seq", allocationSize = 1)
    private Long id;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String year;

    @Column
    @NotNull
    private String genre;

    @Column
    @NotNull
    private String imageUrl;

    @Transient
    private boolean favorite;

}
