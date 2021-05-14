package kz.csse.javaProject.services.impl;

import kz.csse.javaProject.entities.Posts;
import kz.csse.javaProject.entities.Users;
import kz.csse.javaProject.exceptions.ResourceNotFoundException;
import kz.csse.javaProject.repositories.PostsRepository;
import kz.csse.javaProject.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public Posts addPost(Posts post) {
        return postsRepository.save(post);
    }

    @Override
    public List<Posts> getAllPosts() {
        return postsRepository.findAll();
    }

    @Override
    public void deletePostById(Long id) {
        Posts post = postsRepository.getOne(id);

        postsRepository.delete(post);
    }

    @Override
    public Posts savePost(Posts post) {
        return postsRepository.save(post);
    }

    @Override
    public List<Posts> getPostByUser(Users users) {
        return postsRepository.findAllByPosterUser(users);
    }

    @Override
    public Posts getPostById(Long id) {
        return postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(("User not exist with id: " + id)));
    }
}
