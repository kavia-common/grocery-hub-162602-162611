package com.example.grocerybackend.security;

import com.example.grocerybackend.model.User;
import com.example.grocerybackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Loads user details from the database by email.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // PUBLIC_INTERFACE
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /** Loads the user by email (username) for Spring Security. */
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new AppUserDetails(user);
    }
}
