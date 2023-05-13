package br.com.babicakesbackend.models.entity;

import br.com.babicakesbackend.models.enumerators.UserOriginEnum;
import br.com.babicakesbackend.models.enumerators.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
public class User implements UserDetails {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_email")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_birthday")
    private Date birthday;

    @Column(name = "user_phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_origin")
    private UserOriginEnum origin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.status.isAdmin()) {
            return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
        }
        return Arrays.asList(new SimpleGrantedAuthority("CLIENT"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.status.isActive() || this.status.isAdmin();
    }
}
