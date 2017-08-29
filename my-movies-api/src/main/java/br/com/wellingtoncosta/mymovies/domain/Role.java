package br.com.wellingtoncosta.mymovies.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.jdo.annotations.Unique;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author Wellington Costa on 07/05/17.
 */
@Entity
@Table(name = "\"role\"")
public class Role {

    @Id
    @Getter
    @Setter
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    @SequenceGenerator(name = "role_sequence", sequenceName = "role_id_seq", allocationSize = 1)
    private Long id;

    @Getter
    @Setter
    @Column
    @Unique
    @NotNull
    private String name;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;


}
