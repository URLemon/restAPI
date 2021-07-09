package rest.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import rest.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();
    void saveUser(User user);
    User findById(Long id);
    void deleteById(Long id);
}
