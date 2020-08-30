package br.com.metacanal.entity;

import br.com.metacanal.model.RecommendationSystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name = "mc_user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private RecommendationSystem recommendationSystem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mc_channel_id", referencedColumnName = "id")
    private Channel ownedChannel;

    @ManyToMany
    @JoinTable(
            name = "mc_channel_subscribers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    private Collection<Channel> subscriptions = new ArrayList<>();

}
