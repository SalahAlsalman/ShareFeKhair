package com.example.sharefekhair.service;

import com.example.sharefekhair.model.MyUser;
import com.example.sharefekhair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> myUser = userRepository.findMyUsersByUsername(username);
        if (myUser.isEmpty()){
            throw new UsernameNotFoundException("username or password is wrong!");
        }
        return myUser.get();
    }
}
