package br.com.metacanal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name = "mc_channel")
@NoArgsConstructor
@AllArgsConstructor
public class Channel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany
    private Collection<Video> videos = new ArrayList<>();

    @ManyToMany(mappedBy = "subscriptions")
    private Collection<User> subscribers = new ArrayList<>();

}
