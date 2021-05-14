package kz.csse.javaProject.services;

import kz.csse.javaProject.entities.Posts;
import kz.csse.javaProject.entities.Users;

import java.util.List;

public interface PostService {
    Posts addPost(Posts post);
    List<Posts> getAllPosts();
    void deletePostById(Long id);
    Posts savePost(Posts post);
    List<Posts> getPostByUser(Users users);
    Posts getPostById(Long id);
}
