package kz.csse.javaProject.repositories;

import kz.csse.javaProject.entities.Friends;
import kz.csse.javaProject.entities.FriendsRequests;
import kz.csse.javaProject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FriendsRequestRepository extends JpaRepository<FriendsRequests, Long> {
    List<FriendsRequests> findAllByUserTo(Users users);
    List<FriendsRequests> findAllByUserFrom(Users users);
    FriendsRequests findByUserFrom(Users users);

//    @Query(value = "select * from t_users u where u.id != :id", nativeQuery = true)
//    List<Users> recommendFriends(@Param("id") Long id);
}
