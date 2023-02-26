package com.definex;

import com.definex.api.request.UserPostRequest;
import com.definex.model.Role;
import com.definex.repository.RoleRepository;
import com.definex.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DefinexFinalApplication implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserService userService;

    public DefinexFinalApplication(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DefinexFinalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       roleRepository.save(new Role("ADMIN"));

        UserPostRequest request = new UserPostRequest();
        request.setFirstname("Ümit");
        request.setLastname("Sayın");
        request.setPassword("kalem123");
        request.setEmail("umitsayin1661@gmail.com");

        userService.createUser(request);
    }
}
