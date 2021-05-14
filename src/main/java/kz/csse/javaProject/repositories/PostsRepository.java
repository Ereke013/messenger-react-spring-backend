package kz.csse.javaProject.repositories;

import kz.csse.javaProject.entities.Posts;
import kz.csse.javaProject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostsRepository extends JpaRepository<Posts, Long> {

    List<Posts> findAllByPosterUser(Users users);
}
