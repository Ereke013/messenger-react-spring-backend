package kz.csse.javaProject.repositories;

import kz.csse.javaProject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
    Users deleteUsersById(Long id);
    Users deleteUsersByEmail(String email);
}
