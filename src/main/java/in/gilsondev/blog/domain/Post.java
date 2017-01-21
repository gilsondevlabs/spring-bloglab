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

    @ManyToOne(optional = false)
    private Author author;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 70)
    private String slug;

    @Column(nullable = false, length = 70)
    private String teaser;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private boolean status = true;

    @ElementCollection
    private List<String> keywords;
}
