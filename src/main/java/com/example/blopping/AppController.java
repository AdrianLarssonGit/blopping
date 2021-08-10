package com.example.blopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String viewHome(){
        return "home";
    }

    @GetMapping("/registrera")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());

        return "sign_up_form";
    }

    @PostMapping("/registrera_anvandare")
    public String newUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return "reg_success";

    }


    @GetMapping("/articles")
    public String listedUsers(Model model){
        List<User> listUser = userRepository.findAll();
        model.addAttribute("listUsers", listUser);

        return "articlesToEdit";
    }

   /* @PostMapping("/logut")
    public String logout(){
        return "home";
    }*/

}
