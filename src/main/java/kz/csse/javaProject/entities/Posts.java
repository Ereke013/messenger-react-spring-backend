package kz.csse.javaProject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "t_posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "postTitle")
    private String postTitle;

    @Column(name = "postObject")
    private String postObject;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users posterUser;

    @Column(name = "postDate")
    private Date postDate;

}
