package in.gilsondev.blog.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Author author;
    private String title;
    private String slug;
    private String teaser;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean status;

    @ElementCollection
    private List<String> keywords;
}
