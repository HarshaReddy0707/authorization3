package authorization.authorization;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;


@Service
public class LoginAttemptService {
    private  userauthrepo userRepository;
public LoginAttemptService( userauthrepo userRepository) {
    this.userRepository = userRepository;
}

    private static final int MAX_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = 15; // minutes


    public  void loginFailed(String username){
        authuser user = userRepository.findByUsername(username);
        if (user != null) {
            int attempts = user.getFailedAttempt();
            attempts++;
            user.setFailedAttempt(attempts);
            if (attempts >= MAX_ATTEMPTS) {
                user.setAccountNonLocked(false);
                user.setLockTime(LocalDateTime.now());
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

public boolean isLocked(authuser user) {
 if (user.getLockTime() == null) return false;

        LocalDateTime unlockTime = user.getLockTime().plusMinutes(LOCK_TIME_DURATION);
        if (LocalDateTime.now().isAfter(unlockTime)) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);
            userRepository.save(user);
            return true;
        }
        return false;
    
       
    }



}
