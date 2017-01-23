package in.gilsondev.blog.repository;

import in.gilsondev.blog.builder.AuthorBuilder;
import in.gilsondev.blog.builder.PostBuilder;
import in.gilsondev.blog.domain.Author;
import in.gilsondev.blog.domain.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void shouldPersistData() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());
        Post post = new PostBuilder()
                .withId(null)
                .withAuthor(authorPersisted)
                .build();

        Post postPersisted = postRepository.save(post);

        assertThat(postPersisted.getId()).isNotNull();
        assertThat(postPersisted.getAuthor()).isNotNull();
        assertThat(postPersisted.getTitle()).isEqualTo(post.getTitle());
        assertThat(postPersisted.getTeaser()).isEqualTo(post.getTeaser());
        assertThat(postPersisted.getSlug()).isEqualTo(post.getSlug());
        assertThat(postPersisted.getBody()).isEqualTo(post.getBody());
    }

    @Test
    public void shouldFindAllPosts() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());
        postRepository.save(new PostBuilder().withId(null).withAuthor(authorPersisted).build());
        postRepository.save(new PostBuilder().withId(null).withAuthor(authorPersisted).build());

        List<Post> posts = postRepository.findAll();

        assertThat(posts).isNotNull();
        assertThat(posts.size()).isEqualTo(2);

    }
}
