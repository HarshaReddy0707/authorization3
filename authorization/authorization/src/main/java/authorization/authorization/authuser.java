package authorization.authorization;

import java.util.HashSet;
import java.util.Set;
import java.util.Date; // Use java.util.Date for this field

import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType; // No longer needed
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Data
public class authuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    
    @ManyToMany
    @JoinTable(name = "user_roles_auth",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<authrole> roles = new HashSet<>();

    @Column(name = "failed_attempt")
    private int failedAttempt;

   
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked = true;

    @Column(name = "lock_time")
    private Date lockTime;

    
}