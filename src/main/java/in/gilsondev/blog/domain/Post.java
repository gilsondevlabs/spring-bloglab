package in.gilsondev.blog.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Post {
    private Long id;
    private String title;
    private String slug;
    private String teaser;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean status;
    private List<String> keywords;
}
