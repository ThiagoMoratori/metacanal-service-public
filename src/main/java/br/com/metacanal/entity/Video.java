package br.com.metacanal.entity;

import br.com.metacanal.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "mc_video")
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    //TODO: use this, when videos are stored on an external service
//    @Column
//    private String url;

    @Column
    private String description;

    @OneToOne
    private Channel channel;

    @Column
    @ElementCollection
    private List<Category> categories = new ArrayList<>();

    @Column
    private boolean isPublic = true;

    @Column
    private byte[] payload;

}
