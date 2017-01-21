package in.gilsondev.blog.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorEntityTest {
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
}
