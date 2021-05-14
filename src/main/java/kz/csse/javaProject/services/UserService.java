package kz.csse.javaProject.services;

import kz.csse.javaProject.entities.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    Users getUserByEmail(String email);
    Users createuser(Users users);
    Users updateProfile(Users user);
    Users updatePassword(Users user);
    Users getUser(Users users);
    Users getUserById(Long id);
    void deleteUser(Users users);
    List<Users> getAllUsers();
}
