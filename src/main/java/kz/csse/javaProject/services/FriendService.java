package kz.csse.javaProject.services;

import kz.csse.javaProject.entities.Friends;
import kz.csse.javaProject.entities.FriendsRequests;
import kz.csse.javaProject.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface FriendService {
    Friends addFriend(Friends friend);
    List<Friends> getAllFriends(Users users);
    void deleteFriend(Friends friend);
    List<Friends> getAllList();
    List<Users> getAllListWithoutMyFriend(Users user);

    List<FriendsRequests> getAllRequests();
    List<FriendsRequests> getAllRequestsSendByUser(Users user);
    List<FriendsRequests> getAllRequestsReceiveByUser(Users user);
    FriendsRequests addReceive(FriendsRequests friendsRequests);
    FriendsRequests oneRequestByUser(Users users);
    Friends getFriendsById(Long id);
    void removeReceive(FriendsRequests friendsRequests);
}
