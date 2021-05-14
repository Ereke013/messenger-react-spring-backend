package kz.csse.javaProject.services;

import kz.csse.javaProject.entities.Friends;
import kz.csse.javaProject.entities.FriendsRequests;
import kz.csse.javaProject.entities.Messages;
import kz.csse.javaProject.entities.Users;
import org.aspectj.bridge.Message;

import java.util.List;

public interface MessageService {
    Messages addMessage(Messages messages);
    Messages getMessageById(Long id);
    List<Messages> getAllMessages();
    void deleteMessage(Messages messages);
    void deleteMessageById(Long id);
    Messages editMessage(Messages messages);
    List<Messages> getAllMessagesBySenderAndRequest(Long senderId, Long requesterId);
}
