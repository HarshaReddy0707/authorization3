package authorization.authorization;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException; 
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private final userauthrepo userRepo;

    @Autowired
    private final LoginAttemptService loginAttemptService; 

    public CustomUserDetailsService(userauthrepo userRepo, LoginAttemptService loginAttemptService) {
        this.userRepo = userRepo;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        authuser user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        
        if (!user.getAccountNonLocked()) {
            
            if (loginAttemptService.unlockWhenTimeExpired(user)) {
                
            } else {
                
                throw new LockedException("User is blocked");
            }
        }
       

        java.util.List<GrantedAuthority> authorities = new java.util.ArrayList<>();
        for (authrole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}