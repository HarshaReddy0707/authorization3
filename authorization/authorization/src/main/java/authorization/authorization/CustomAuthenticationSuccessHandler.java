package authorization.authorization;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
                    private final LoginAttemptService loginattempts;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName(); 
        loginattempts.loginSucceeded(username);
        response.sendRedirect("/default");
        // TODO Auto-generated method stub
      //  throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
    }

   
    
}
