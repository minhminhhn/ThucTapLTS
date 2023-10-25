package store.polyfood.thuctap.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int acountId;
    @Column
    private String userName;
    @Column
    private String avatar;
    @Column
    private String password;
    @Column
    private int status;
    @Column(name = "decentralization_id", updatable = false, insertable = false)
    private int decentralizationId;
    @Column
    private String resetPasswordToken;
    @Column
    private LocalDateTime resetPasswordTokenExpiry;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;



    @ManyToOne
    @JoinColumn(name = "decentralization_id")
    @JsonBackReference
    private Decentralization decentralization;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List< GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.decentralization.getAuthorityName()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status != 4;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != 3;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status != 2;
    }

    @Override
    public boolean isEnabled() {
        return status != 1;
    }
}
