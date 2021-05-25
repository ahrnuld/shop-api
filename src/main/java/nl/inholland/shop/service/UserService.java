package nl.inholland.shop.service;

import nl.inholland.shop.model.User;
import nl.inholland.shop.repository.UserRepository;
import nl.inholland.shop.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public long getNumberOfUsers() {
        List<User> users = userRepository.findAll();

        Stream<User> userStream = users.stream();

        long numberOfUsers = userStream.count();

        return numberOfUsers;
    }

    public List<User> getSomeUsers() {
        List<User> users = userRepository.findAll();

        Stream<User> userStream = users.stream();

        List<User> filteredUsers = userStream.filter(u -> u.getUsername().startsWith("A"))
                .collect(Collectors.toList());

        return filteredUsers;


    }

    public String login(String username, String password)
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            return jwtTokenProvider.createToken(username, user.getRoles());
        } catch(AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username/password invalid");
        }
    }

    public User add(User user){
        if(userRepository.findByUsername(user.getUsername()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return user;
        }
        else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username already in use");
        }
    }
}
