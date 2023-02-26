package com.definex.util.helper;

import com.definex.constant.GlobalConstant;
import com.definex.model.User;
import com.definex.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class SystemHelper {
    private final UserRepository userRepository;

    public SystemHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication)) {
            final String userName = authentication.getName();

            final Optional<User> currentUser = userRepository.findByEmail(userName);

            if (currentUser.isPresent()) {
                return currentUser.get();
            }
        }

        throw new UsernameNotFoundException(GlobalConstant.USER_NOT_FOUND);
    }
}
