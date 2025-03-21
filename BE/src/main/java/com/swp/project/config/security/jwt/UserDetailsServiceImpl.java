package com.swp.project.config.security.jwt;

import com.swp.project.entity.User;
import com.swp.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid Username or Password"));
        if(!user.isActive()){
            throw new RuntimeException("Account is not active");
        }else if(user.isBanned()){
            throw new RuntimeException("Account is banned");
        }
        return UserDetailsImpl.build(user);
    }
}
