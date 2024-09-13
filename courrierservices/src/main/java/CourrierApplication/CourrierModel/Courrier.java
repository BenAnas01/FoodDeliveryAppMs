package CourrierApplication.CourrierModel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Courrier implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courrierid;

    private String emailaddress;
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime lastModification;

    @OneToOne(mappedBy = "courrier", cascade = CascadeType.ALL)
    private CourrierProfile courrierProfile;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return getEmailaddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
