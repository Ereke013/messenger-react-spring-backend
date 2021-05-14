package kz.csse.javaProject.repositories;

import kz.csse.javaProject.entities.Messages;
import kz.csse.javaProject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MessagesRepository extends JpaRepository<Messages, Long> {

    List<Messages> findAllByUser(Users users);
    List<Messages> findAllByUserAndFriend(Users sender, Users request);
    void removeById(Long id);

//    @Query(value = "select * from t_users u where u.id != :id", nativeQuery = true)
//    List<Users> recommendFriends(@Param("id") Long id);
}
