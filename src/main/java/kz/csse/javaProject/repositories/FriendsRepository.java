package kz.csse.javaProject.repositories;

import kz.csse.javaProject.entities.Friends;
import kz.csse.javaProject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FriendsRepository extends JpaRepository<Friends, Long> {

    List<Friends> findAllByUser(Users users);

//    @Query(value = "select * from t_users u where u.id != :id", nativeQuery = true)
//    List<Users> recommendFriends(@Param("id") Long id);
}
