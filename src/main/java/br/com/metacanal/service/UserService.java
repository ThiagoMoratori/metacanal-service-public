package br.com.metacanal.service;

import br.com.metacanal.entity.Channel;
import br.com.metacanal.entity.User;
import br.com.metacanal.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(JsonNode user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return userRepository.save(objectMapper.treeToValue(user, User.class));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        Optional<User> userToBeDeleted = getById(id);
        if (userToBeDeleted.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public User updateName(Map<String, String> updates, Long id) {
        Optional<User> userToBePatched = getById(id);
        if (userToBePatched.isPresent()) {
            User user = userToBePatched.get();
            String newName = updates.get("name");
            user.setName(newName);
            return userRepository.save(user);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
