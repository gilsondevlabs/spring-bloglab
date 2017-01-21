package in.gilsondev.blog.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class AuthorEntityTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void shouldHaveLombokProperties() {
        Author author = new Author();
        author.setId(1L);
        author.setFirstName("Author");
        author.setLastName("Test");
        author.setEmail("author.test@mail.com");

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getFirstName()).isEqualTo("Author");
        assertThat(author.getLastName()).isEqualTo("Test");
        assertThat(author.getEmail()).isEqualTo("author.test@mail.com");
    }

    @Test
    public void shouldPersistData() {
        Author author = new Author();
        author.setFirstName("Author");
        author.setLastName("Test");
        author.setEmail("author.test@mail.com");

        Author authorPersisted = entityManager.persistFlushFind(author);

        assertThat(authorPersisted.getId()).isEqualTo(1L);
        assertThat(authorPersisted.getFirstName()).isEqualTo(author.getFirstName());
        assertThat(authorPersisted.getLastName()).isEqualTo(author.getLastName());
        assertThat(authorPersisted.getEmail()).isEqualTo(author.getEmail());
    }
}
