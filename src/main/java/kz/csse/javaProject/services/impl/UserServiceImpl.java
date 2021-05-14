package kz.csse.javaProject.services.impl;

import kz.csse.javaProject.entities.Roles;
import kz.csse.javaProject.entities.Users;
import kz.csse.javaProject.exceptions.ResourceNotFoundException;
import kz.csse.javaProject.repositories.RolesRepository;
import kz.csse.javaProject.repositories.UsersRepository;
import kz.csse.javaProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Users createuser(Users users) {
        Users user = usersRepository.findByEmail(users.getEmail());
        if(user == null){
            Roles role = rolesRepository.findByRole("ROLE_USER");
            if(role != null){
                ArrayList<Roles> rolesList= new ArrayList<>();
                rolesList.add(role);
                users.setRoles(rolesList);
                users.setPassword(passwordEncoder.encode(users.getPassword()));
                users.setAva("http://cdn.onlinewebfonts.com/svg/img_91484.png");
                return usersRepository.save(users);
            }
        }
        return null;
    }

    @Override
    public Users updateProfile(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public Users updatePassword(Users user) {
        if(user != null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return usersRepository.save(user);
        }
        return null;
    }

    @Override
    public Users getUser(Users users) {
        return usersRepository.findByEmail(users.getEmail());
    }

    @Override
    public Users getUserById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(("User not exist with id: " + id)));
    }

    @Override
    public void deleteUser(Users users) {
//        Users users1 = getUserById(users.getId());
//        if(users1!=null){
//            usersRepository.delete(users);
//        }
        usersRepository.delete(users);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(s);
        if(user!=null){
            return user;
        }else {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
}
