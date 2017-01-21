package in.gilsondev.blog.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class Author {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
