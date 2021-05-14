package kz.csse.javaProject.services.impl;

import kz.csse.javaProject.entities.Friends;
import kz.csse.javaProject.entities.FriendsRequests;
import kz.csse.javaProject.entities.Messages;
import kz.csse.javaProject.entities.Users;
import kz.csse.javaProject.exceptions.ResourceNotFoundException;
import kz.csse.javaProject.repositories.FriendsRepository;
import kz.csse.javaProject.repositories.FriendsRequestRepository;
import kz.csse.javaProject.repositories.MessagesRepository;
import kz.csse.javaProject.repositories.UsersRepository;
import kz.csse.javaProject.services.FriendService;
import kz.csse.javaProject.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MessagesRepository messagesRepository;

    @Override
    public Messages addMessage(Messages messages) {
        return messagesRepository.save(messages);
    }

    @Override
    public List<Messages> getAllMessages() {
        return messagesRepository.findAll();
    }

    @Override
    public void deleteMessage(Messages messages) {
        messagesRepository.delete(messages);
    }

    @Override
    public void deleteMessageById(Long id) {
        messagesRepository.removeById(id);
    }

    @Override
    public Messages editMessage(Messages messages) {
        return messagesRepository.save(messages);
    }

    @Override
    public List<Messages> getAllMessagesBySenderAndRequest(Long senderId, Long requesterId) {
        Users sender = getUserById(senderId);
        Users request = getUserById(requesterId);
        return messagesRepository.findAllByUserAndFriend(sender, request);
    }

    public Users getUserById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(("Users not exist with id: " + id)));
    }

    @Override
    public Messages getMessageById(Long id) {
        return messagesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(("Message not exist with id: " + id)));
    }
}
