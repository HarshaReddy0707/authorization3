package authorization.authorization;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

private  LoginAttemptService loginattempts;

public CustomAuthenticationFailureHandler( LoginAttemptService loginattempts) {
    this.loginattempts = loginattempts;
}

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        loginattempts.loginFailed(username);
        response.sendRedirect("/access-denied");
        // TODO Auto-generated method stub
    }
    
}
