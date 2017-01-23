package in.gilsondev.blog.builder;

import in.gilsondev.blog.domain.Author;
import in.gilsondev.blog.domain.Post;

import java.time.LocalDateTime;
import java.util.Arrays;

public class PostBuilder {
    private Post post;

    public PostBuilder() {
        this.post = new Post();
        this.post.setId(1L);
        this.post.setAuthor(new AuthorBuilder().build());
        this.post.setTitle("Post Title");
        this.post.setSlug("post-title");
        this.post.setTeaser("Post resume");
        this.post.setBody("Post Content");
        this.post.setCreatedAt(LocalDateTime.now());
        this.post.setUpdatedAt(LocalDateTime.now());
        this.post.setKeywords(Arrays.asList("Post", "Tags"));
    }

    public PostBuilder withId(Long id) {
        this.post.setId(id);
        return this;
    }

    public PostBuilder withAuthor(Author author) {
        this.post.setAuthor(author);
        return this;
    }

    public PostBuilder withTitle(String title) {
        this.post.setTitle(title);
        return this;
    }

    public PostBuilder withSlug(String slug) {
        this.post.setSlug(slug);
        return this;
    }

    public PostBuilder withTeaser(String teaser) {
        this.post.setTeaser(teaser);
        return this;
    }

    public PostBuilder withBody(String body) {
        this.post.setBody(body);
        return this;
    }

    public PostBuilder withCreatedAt(LocalDateTime createdAt) {
        this.post.setCreatedAt(createdAt);
        return this;
    }

    public PostBuilder withUpdatedAt(LocalDateTime updatedAt) {
        this.post.setUpdatedAt(updatedAt);
        return this;
    }

    public PostBuilder active() {
        this.post.setStatus(true);
        return this;
    }

    public PostBuilder inative() {
        this.post.setStatus(false);
        return this;
    }

    public PostBuilder withKeywords(String... keywords) {
        this.post.setKeywords(Arrays.asList(keywords));
        return this;
    }

    public Post build() {
        return this.post;
    }
}
