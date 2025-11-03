package authorization.authorization;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Date; 

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private final userauthrepo userRepository;
    private static final int MAX_ATTEMPTS = 5; 
    public static final long LOCK_TIME_DURATION_MINUTES = 30;

    public void loginFailed(String username) {
        authuser user = userRepository.findByUsername(username);
        if (user != null && user.getAccountNonLocked()) {
            int attempts = user.getFailedAttempt();
            attempts++;
            user.setFailedAttempt(attempts);
            
            if (attempts >= MAX_ATTEMPTS) {
                user.setAccountNonLocked(false);
                user.setLockTime(new Date());
            }
            userRepository.save(user);
        }
    }

    public void loginSucceeded(String username) {
        authuser user = userRepository.findByUsername(username);
        if (user != null) {
            user.setFailedAttempt(0);
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            userRepository.save(user);
        }
    }

    
    public boolean unlockWhenTimeExpired(authuser user) {
        if (user.getLockTime() == null) {
            return true; 
        }
        
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        long lockDurationInMillis = LOCK_TIME_DURATION_MINUTES * 60 * 1000;

        if (lockTimeInMillis + lockDurationInMillis < currentTimeInMillis) {
            
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}