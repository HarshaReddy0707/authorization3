package authorization.authorization;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType; // <-- Import changed
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isAccountNonLocked=true;
    @Column(nullable = false, columnDefinition = "int default 0")
    private int failedAttempt;
    
    private LocalDateTime lockTime;

@ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(name = "user_roles_auth",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
            private Set<authrole> role=new HashSet<>();
}