package in.gilsondev.blog.repository;

import in.gilsondev.blog.builder.AuthorBuilder;
import in.gilsondev.blog.domain.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void shouldPersistData() {
        Author author = new AuthorBuilder()
                .withId(null)
                .build();

        Author authorPersisted = authorRepository.save(author);

        assertThat(authorPersisted.getId()).isNotNull();
        assertThat(authorPersisted.getFirstName()).isEqualTo(author.getFirstName());
        assertThat(authorPersisted.getLastName()).isEqualTo(author.getLastName());
        assertThat(authorPersisted.getEmail()).isEqualTo(author.getEmail());
    }

    @Test
    public void shouldFindAllAuthors() {
        authorRepository.save(new AuthorBuilder().withId(null).build());
        authorRepository.save(new AuthorBuilder().withId(null).build());

        List<Author> authors = authorRepository.findAll();

        assertThat(authors).isNotEmpty();
        assertThat(authors.size()).isEqualTo(2);
    }
}
