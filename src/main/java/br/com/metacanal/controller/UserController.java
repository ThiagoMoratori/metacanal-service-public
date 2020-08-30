package br.com.metacanal.controller;

import br.com.metacanal.entity.User;
import br.com.metacanal.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Check localhost:8081/swagger-ui.html for the Swagger Documentation!
 */

@Api(description = "User Controller")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    private UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "This route will create a new User")
    @PostMapping(path = "/user", produces = "application/json", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody JsonNode user) throws JsonProcessingException {
        return new ResponseEntity<>(userService.create(user), HttpStatus.OK);
    }

    @ApiOperation(value = "This route deletes a User by ID")
    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "This route patches a User with a new name")
    @PatchMapping(path = "/user/{id}")
    public ResponseEntity<?> patchUser(@RequestBody Map<String, String> updates, @PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.updateName(updates, id), HttpStatus.OK);
    }

    @ApiOperation(value = "This route fetches all Users from the database")
    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @ApiOperation(value = "This route fetches a User by it's name")
    @GetMapping(path = "/user/name/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        return new ResponseEntity<>(userService.getByName(name), HttpStatus.OK);
    }

    @ApiOperation(value = "This route fetches a User by it's ID, if it exists on the database")
    @GetMapping(path = "/user/id/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }
}
