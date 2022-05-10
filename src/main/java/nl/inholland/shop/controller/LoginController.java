package nl.inholland.shop.controller;

import nl.inholland.shop.model.LoginDTO;
import nl.inholland.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService loginService;

    @PostMapping
    public String login(@RequestBody LoginDTO login) {


        return loginService.login(login.getUsername(), login.getPassword());
    }

}
