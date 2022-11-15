package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.NotFoundException;
import br.com.babicakesbackend.models.dto.UserForm;
import br.com.babicakesbackend.models.dto.UserView;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.models.mapper.UserMapperImpl;
import br.com.babicakesbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class UserService extends AbstractService<User, UserView, UserForm> implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapperImpl userMapper;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }
    @Override
    protected MapStructMapper<User, UserView, UserForm> getConverter() {
        return userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<br.com.babicakesbackend.models.entity.User> entity = userRepository.findByEmail(email);
        if(!entity.isPresent()) {
            throw new NotFoundException("Nenhum usuário encontrado!");
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(email, entity.get().getPassword(), getPermission(entity.get()));
        return userDetails;
    }

    private Collection<? extends GrantedAuthority> getPermission(User user) {
        return Arrays.asList(new SimpleGrantedAuthority("ALL"));
    }
}
