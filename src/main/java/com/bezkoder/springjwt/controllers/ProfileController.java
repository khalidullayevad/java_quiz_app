package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.services.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class ProfileController {


    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    private User getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User users = userRepository.findByUsername(userDetails.getEmail()).get();
        return users;
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updatePassword")
    public String updatePassword(@RequestBody ChangePassword changePassword) {
        User users = getUserData();
        System.out.println(users.getPassword() + "----------------" + users.getEmail());
        if (encoder.matches(changePassword.getOldpassword(), users.getPassword())) {

            users.setPassword(encoder.encode(changePassword.getNewpassword()));
            userRepository.save(users);
            return "success";
        } else {
            return "are not equal";
        }
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updateRole")
    public String updateRole(@RequestBody AddRole addRole) {
        User user = userRepository.findByIdEquals(addRole.getUserId());
        Role role = roleRepository.findByIdEquals(addRole.getRoleId());


        if(user!=null && role!=null){
            Set<Role> roles = (Set<Role>) roleRepository.findAll();
            user.setRoles(roles);
            userRepository.save(user);
           return "success";
        }
        else {
            return "error";
        }
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getPassword")
    public String getPassword(@PathVariable("id") long id) {
        String users1 = userRepository.findById(id).get().getPassword();
        System.out.println(users1);
        return users1;
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/allUsers")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userRepository.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/allRoles")
    public ResponseEntity<?> getAllRoles(){
        List<Role> roles = roleRepository.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}