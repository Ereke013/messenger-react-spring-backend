package kz.csse.javaProject.services.impl;

import kz.csse.javaProject.entities.Friends;
import kz.csse.javaProject.entities.FriendsRequests;
import kz.csse.javaProject.entities.Users;
import kz.csse.javaProject.exceptions.ResourceNotFoundException;
import kz.csse.javaProject.repositories.FriendsRepository;
import kz.csse.javaProject.repositories.FriendsRequestRepository;
import kz.csse.javaProject.repositories.PostsRepository;
import kz.csse.javaProject.repositories.UsersRepository;
import kz.csse.javaProject.services.FriendService;
import kz.csse.javaProject.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private FriendsRequestRepository friendsRequestRepository;


    @Override
    public Friends addFriend(Friends friend) {
        return friendsRepository.save(friend);
    }

    @Override
    public List<Friends> getAllFriends(Users users) {
        return friendsRepository.findAllByUser(users);
    }

    @Override
    public void deleteFriend(Friends friend) {
        friendsRepository.delete(friend);
    }

    @Override
    public List<Friends> getAllList() {
        return friendsRepository.findAll();
    }

    @Override
    public List<Users> getAllListWithoutMyFriend(Users user) {
        List<Users> usersList = usersRepository.findAll();
        List<Friends> myFriends = getAllFriends(user);
        List<Users> recomendFriends = new ArrayList<>();
        System.out.println("myFriends: " + myFriends);
        int i = 0;
        for(Friends friends: myFriends){
            if(usersList.contains(friends.getFriend())){
                usersList.remove(friends.getFriend());
            }
        }
        usersList.remove(user);
        System.out.println("recommend friends: " + usersList);
        return usersList;
    }

    @Override
    public List<FriendsRequests> getAllRequests() {
        return friendsRequestRepository.findAll();
    }

    @Override
    public List<FriendsRequests> getAllRequestsSendByUser(Users user) {
        return friendsRequestRepository.findAllByUserFrom(user);
    }

    @Override
    public List<FriendsRequests> getAllRequestsReceiveByUser(Users user) {
        return friendsRequestRepository.findAllByUserTo(user);
    }

    @Override
    public FriendsRequests addReceive(FriendsRequests friendsRequests) {
        return friendsRequestRepository.save(friendsRequests);
    }

    @Override
    public void removeReceive(FriendsRequests friendsRequests) {
        friendsRequestRepository.delete(friendsRequests);
    }

    @Override
    public FriendsRequests oneRequestByUser(Users users) {
        return friendsRequestRepository.findByUserFrom(users);
    }

    @Override
    public Friends getFriendsById(Long id) {
        return friendsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(("Friends not exist with id: " + id)));
    }
}
