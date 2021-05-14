package kz.csse.javaProject.rest;


import kz.csse.javaProject.entities.*;
import kz.csse.javaProject.models.UserDTO;
import kz.csse.javaProject.services.FriendService;
import kz.csse.javaProject.services.MessageService;
import kz.csse.javaProject.services.PostService;
import kz.csse.javaProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api")
public class MainRestController {


    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private MessageService messageService;

    //User Auth

    @GetMapping(value = "/profile")
    public ResponseEntity<?> profilePage() {
        Users user = getUser();
        assert user != null;
        return new ResponseEntity<>(new UserDTO(user.getId(), user.getEmail(), user.getFullName(), user.getPassword(), user.getAva(), user.getRoles()), HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    public String logout() {
        return "ok";
    }


    @PutMapping(value = "/updateprofile")
    public ResponseEntity<?> updateProfile(@RequestBody Users user) {
        userService.updateProfile(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/updatepassword")
    public ResponseEntity<?> updatepassword(@RequestBody Users user) {
        userService.updatePassword(user);
        return ResponseEntity.ok(user);
    }

    private Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = (Users) authentication.getPrincipal();
            return user;
        }
        return null;
    }

    @GetMapping(value = "/news")
    public ResponseEntity<?> getAllNews() {
        List<Posts> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/getusernews/{email}")
    public ResponseEntity<?> getMyNews(@PathVariable(name = "email") String email) {
        System.out.println("/getusernews/{id} keldi");
        Users user = userService.getUserByEmail(email);
        if (user != null) {
            System.out.println(user);
            List<Posts> posts = postService.getPostByUser(user);
            System.out.println(posts);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }
        return new ResponseEntity<>("fail", HttpStatus.OK);
    }

    @PostMapping(value = "/addPost")
    public ResponseEntity<?> addPost(@RequestBody Posts post) {
        System.out.println("keldi add post");
        System.out.println(post);
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        post.setPostDate(date);
        postService.addPost(post);
        return ResponseEntity.ok(post);
    }

    @GetMapping(value = "/myfriends/{email}")
    public ResponseEntity<?> myFriends(@PathVariable(name = "email") String email) {
        Users user = userService.getUserByEmail(email);
        if (user != null) {
            List<Friends> friends = friendService.getAllFriends(user);
            return new ResponseEntity<>(friends, HttpStatus.OK);
        }
        return null;
    }

    @GetMapping(value = "/recommendFriends/{email}")
    public ResponseEntity<?> recommendedFriends(@PathVariable(name = "email") String email) {
        Users user = userService.getUserByEmail(email);
        System.out.println("user is recommended: " + user);
        if (user != null) {
            List<Users> recommendedfriends = friendService.getAllListWithoutMyFriend(user);
            return new ResponseEntity<>(recommendedfriends, HttpStatus.OK);
        }
        return null;
    }

    @PostMapping(value = "/addfriendrequest")
    public ResponseEntity<?> addFriendRequest(@RequestBody FriendsRequests friendsRequests) {
        System.out.println("frrrr:" + friendsRequests);
        friendService.addReceive(friendsRequests);
        return ResponseEntity.ok(friendsRequests);
    }

    @GetMapping(value = "/friendreceiveform/{email}")
    public ResponseEntity<?> getAllRequests(@PathVariable(name = "email") String email) {
        Users users = userService.getUserByEmail(email);
        if (users != null) {
            List<FriendsRequests> allReceivers = friendService.getAllRequestsReceiveByUser(users);
            return new ResponseEntity<>(allReceivers, HttpStatus.OK);
        }
        System.out.println("User is null");
        return null;
    }

    @GetMapping(value = "/requestByUser/{useremail}/{baskaemail}")
    public ResponseEntity<?> requestUser(@PathVariable(name = "useremail") String email,
                                         @PathVariable(name = "baskaemail") String anotherUserEmail) {
        Users users = userService.getUserByEmail(email);
        Users baskauser = userService.getUserByEmail(anotherUserEmail);
        if (users != null) {
            List<FriendsRequests> allRequest = friendService.getAllRequestsSendByUser(users);
            for (FriendsRequests friendsRequests : allRequest) {
                if (friendsRequests.getUserTo().equals(baskauser)) {
                    System.out.println("baska user bar");
                    return new ResponseEntity<>(baskauser, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("User[requestUser method] is null");
        return null;
    }

    @GetMapping(value = "/allRequests")
    public ResponseEntity<?> getAllRequests() {
        List<FriendsRequests> friendsRequests = friendService.getAllRequests();
        return new ResponseEntity<>(friendsRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/allUsers")
    public ResponseEntity<?> getAllUsers() {
        List<Users> allUsers = userService.getAllUsers();
        System.out.println("all users");
        System.out.println(allUsers);
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping(value = "allUsers/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") Long id) {
        System.out.println("id: " + id);
        Users user = userService.getUserById(id);
        if (user != null) {
            System.out.println("allUsers/{id}");
            System.out.println(user);
            return ResponseEntity.ok(user);
        }
        return null;
    }

    @PutMapping(value = "/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody Users users) {
        System.out.println("sirttan kelgen user: " + users);
//        System.out.println("sirttan kelgen id: " + id);
        Users user = userService.getUserById(users.getId());
        user.setAva(users.getAva());
        user.setEmail(users.getEmail());
        user.setPassword(users.getPassword());
        user.setFullName(users.getFullName());
        user.setRoles(users.getRoles());

        userService.updateProfile(user);
//        if (user != null) {
//            System.out.println("id arkili tapkan user: " + user);
//            user.setAva(users.getAva());
//            user.setEmail(users.getEmail());
//            user.setPassword(users.getPassword());
//            user.setFullName(users.getFullName());
//
////            user.setRoles(users.getRoles());
//            return ResponseEntity.ok(user);
//        }
        return ResponseEntity.ok(users);
    }

    @DeleteMapping(value = "deleteUser/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable(name = "id") Long id) {
        System.out.println("deleteUser");
        Users users = userService.getUserById(id);
        if (users != null) {
            userService.deleteUser(users);
            return ResponseEntity.ok(users);
        }
        System.out.println("end deleteUser");
        return null;
    }

    @GetMapping(value = "news/{id}")
    public ResponseEntity<?> getPostById(@PathVariable(name = "id") Long id) {
        System.out.println("id: " + id);
        Posts post = postService.getPostById(id);
        if (post != null) {
            System.out.println("news/{id}");
            System.out.println(post);
            return ResponseEntity.ok(post);
        }
        return null;
    }

    @PutMapping(value = "/updatePost")
    public ResponseEntity<?> updatePost(@RequestBody Posts posts) {
        System.out.println("sirttan kelgen post: " + posts);
//        System.out.println("sirttan kelgen id: " + id);
        Posts post = postService.getPostById(posts.getId());
        post.setPostTitle(posts.getPostTitle());
        post.setPostObject(posts.getPostObject());
        post.setPosterUser(posts.getPosterUser());

        postService.savePost(post);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping(value = "deletePost/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable(name = "id") Long id) {
        System.out.println("deletePost");
        Posts posts = postService.getPostById(id);
        if (posts != null) {
            postService.deletePostById(posts.getId());
            return ResponseEntity.ok(posts);
        }
        System.out.println("end deleteUser");
        return null;
    }


    @GetMapping(value = "/friends")
    public ResponseEntity<?> getAllFriends() {
        List<Friends> allFriends = friendService.getAllList();
        System.out.println("all friends");
        System.out.println(allFriends);
        return new ResponseEntity<>(allFriends, HttpStatus.OK);
    }

    @GetMapping(value = "/friends/{id}")
    public ResponseEntity<?> getFriendsById(@PathVariable(name = "id") Long id) {
        System.out.println("id: " + id);
        Friends friend = friendService.getFriendsById(id);
        if (friend != null) {
            System.out.println("friends/{id}");
            System.out.println(friend);
            return ResponseEntity.ok(friend);
        }
        return null;
    }

    @PostMapping(value = "/addFriends")
    public ResponseEntity<?> addFriends(@RequestBody Friends friends){
        System.out.println("add friends method");
        System.out.println(friends);
        if(friends!=null){
            friendService.addFriend(friends);
            return new ResponseEntity<>(friends, HttpStatus.OK);
        }
        System.out.println("if kirmedi");
        return null;
    }

    @DeleteMapping(value = "deleteFriend/{id}")
    public ResponseEntity<?> deleteFriendsById(@PathVariable(name = "id") Long id) {
        System.out.println("deleteFriends");
        Friends friends = friendService.getFriendsById(id);
        if (friends != null) {
            friendService.deleteFriend(friends);
            return ResponseEntity.ok(friends);
        }
        System.out.println("end deleteUser");
        return null;
    }

    @GetMapping(value = "/messages")
    public ResponseEntity<?> getAllMessages() {
        List<Messages> allMessages = messageService.getAllMessages();
        System.out.println("all messages");
        System.out.println(allMessages);
        return new ResponseEntity<>(allMessages, HttpStatus.OK);
    }

    @GetMapping(value = "/messages/{id}")
    public ResponseEntity<?> getMessagesById(@PathVariable(name = "id") Long id) {
        System.out.println("id: " + id);
        Messages message = messageService.getMessageById(id);
        if (message != null) {
            System.out.println("messages/{id}");
            System.out.println(message);
            return ResponseEntity.ok(message);
        }
        return null;
    }

    @PostMapping(value = "/addMessage")
    public ResponseEntity<?> addMessages(@RequestBody Messages messages){
        System.out.println("add friends method");
        System.out.println(messages);
        if(messages!=null){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            messages.setSend_time(Timestamp.valueOf(LocalDateTime.now()));
            messageService.addMessage(messages);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
        System.out.println("if kirmedi");
        return null;
    }

    @DeleteMapping(value = "deleteMessage/{id}")
    public ResponseEntity<?> deleteMessageById(@PathVariable(name = "id") Long id) {
        System.out.println("deleteMessage");
        Messages messages = messageService.getMessageById(id);
        if (messages != null) {
            messageService.deleteMessage(messages);
            return ResponseEntity.ok(messages);
        }
        System.out.println("end deleteUser");
        return null;
    }

    @PutMapping(value = "/updateMessage")
    public ResponseEntity<?> updateMessage(@RequestBody Messages messages) {
        System.out.println("sirttan kelgen message: " + messages);
//        System.out.println("sirttan kelgen id: " + id);
        Messages message = messageService.getMessageById(messages.getId());
        message.setUser(messages.getUser());
        message.setFriend(messages.getFriend());
        message.setMessage_text(messages.getMessage_text());

        messageService.editMessage(message);
        return ResponseEntity.ok(messages);
    }

//    @GetMapping(value = "/allcards")
//    public ResponseEntity<?> getAllCards(){
//        List<Cards> cards = cardService.getAllCards();
//        return new ResponseEntity<>(cards, HttpStatus.OK);
//    }
//    @PostMapping(value = "/addcard")
//    public ResponseEntity<?> addItem(@RequestBody Cards card){
//        cardService.addCard(card);
//        return ResponseEntity.ok(card);
//    }
//    @GetMapping(value = "/getcard/{id}")
//    public ResponseEntity<?> getCard(@PathVariable(name="id") Long id){
//        Cards card = cardService.getCardById(id);
//        System.out.println(card);
//        return ResponseEntity.ok(card);
//    }
//
//    @PutMapping(value = "/savecard")
//    public ResponseEntity<?> saveCard(@RequestBody Cards card){
//        cardService.saveCard(card);
//        return ResponseEntity.ok(card);
//    }
//
//    @DeleteMapping(value = "/deletecard")
//    public ResponseEntity<?> deleteItem(@RequestBody Cards card){
//        Cards checkCard = cardService.getCardById(card.getId());
//        if(checkCard!=null){
//            cardService.deleteCard(checkCard);
//            return ResponseEntity.ok(checkCard);
//        }
//        return ResponseEntity.ok(card);
//    }
//
//
//    @GetMapping(value = "/cardtasksbycard/{id}")
//    public ResponseEntity<?> getAllCardTasksByCard(@PathVariable(name="id") Long id){
//        Cards card = cardService.getCardById(id);
//        List<CardTasks> cardTask = cardTaskService.getAllCardTasksOfCard(card);
//        return new ResponseEntity<>(cardTask, HttpStatus.OK);
//    }
//
//    @PutMapping(value = "/cardtaskdone")
//    public ResponseEntity<?> saveCardTaskDone(@RequestBody CardTasks cardTask){
//        cardTaskService.saveTaskDone(cardTask);
//        return ResponseEntity.ok(cardTask);
//    }
//
//    @PostMapping(value = "/addcardtask")
//    public ResponseEntity<?> addItem(@RequestBody CardTasks cardTasks){
//       cardTaskService.addCardTask(cardTasks);
//        return ResponseEntity.ok(cardTasks);
//    }
//
//    @GetMapping(value = "/getcardtask/{id}")
//    public ResponseEntity<?> getCardTask(@PathVariable(name="id") Long id){
//
//        CardTasks cardTask = cardTaskService.getCardTaskById(id);
//
//        return ResponseEntity.ok(cardTask);
//    }
//
//    @GetMapping(value = "/searchcardbyname/{input_name}")
//    public ResponseEntity<?> getSearchCard(@PathVariable(name="input_name") String inputName
//                                           ){
//        List<Cards> searchCards = cardService.getSearchCards(inputName);
//
//
//
//        return ResponseEntity.ok(searchCards);
//    }

}
