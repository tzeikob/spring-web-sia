package com.app.ext;

import com.app.db.UserRepository;
import com.app.model.UserRole;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.app.model.User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with given username '" + username + "'");
        } else {
            List<GrantedAuthority> authorities = new ArrayList<>();

            for (UserRole role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getType()));
            }

            UserDetails userDetails = new User(user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    true, true, true, authorities);

            return userDetails;
        }
    }
}
