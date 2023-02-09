package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Stub
public class PostRepository {

    private volatile long counter = 0;

    private Map<Long, Post> posts = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.of(posts.get(id));
    }

    public Post save(Post post) {
        Post tempPost;

        if (post.getId() == 0) {
            counter++;
            posts.put(counter, new Post(counter, post.getContent()));
            tempPost = posts.get(counter);
        } else if (posts.containsKey(post.getId())) {
            posts.replace(post.getId(), posts.get(post.getId()), post);
            tempPost = post;
        } else {
            throw new NotFoundException("There is no post with specified id");
        }

        return tempPost;
    }

    public void removeById(long id) {
        if (posts.containsKey(id)) {
            posts.remove(id);
        } else {
            throw new NotFoundException("There is no post with specified id");
        }
    }
}
