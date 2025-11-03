package authorization.authorization;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private  userauthrepo userRepo;

 @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        authuser user = userRepo.findByUsername(username);

        if (loginAttemptService.isLocked(user)==false) {
            throw new LockedException("Your account has been locked due to multiple failed login attempts. Please try again later.");
            
        }
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

      
       List<GrantedAuthority> authorities = new java.util.ArrayList<>();
        for (authrole role : user.getRole()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(user.getUsername(), user.getPassword(),authorities);
    }
    
}
