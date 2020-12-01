package ru.cipherapp.services;

import org.springframework.stereotype.Service;
import ru.cipherapp.models.EncryptKey;
import ru.cipherapp.models.User;
import ru.cipherapp.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

/*
Service class for handling user repository
 */

@Service
public class UserService implements CrudService<User, Long> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll(EncryptKey encryptKey) {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public void save(User object, EncryptKey encryptKey) {
        userRepository.save(object);
    }

}
