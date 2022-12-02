package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.exception.NotFoundException;
import br.com.babicakesbackend.models.dto.UserForm;
import br.com.babicakesbackend.models.dto.UserView;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.enumerators.UserStatusEnum;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.models.mapper.UserMapperImpl;
import br.com.babicakesbackend.repository.UserRepository;
import br.com.babicakesbackend.util.ConstantUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.classfile.Constant;
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

@Slf4j
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

    public void saveCustom(UserForm userForm) {
        try {
            log.info(">> saveCustom [userEmail={}]", userForm.getEmail());
            Optional<User> user = userRepository.findByEmail(userForm.getEmail());

            if(user.isPresent()) {
                throw new GlobalException("O email informado já está cadastrado");
            }

            String phone = ConstantUtils.validPhone(userForm.getPhone());
            userForm.setPhone(phone);

            ConstantUtils.validEmail(userForm.getEmail());
;
            User userConvert = userMapper.formToEntity(userForm);

            userRepository.save(userConvert);
            log.info("<< saveCustom [userId={}]", userConvert.getId());
        } catch (Exception e) {
            log.error("<< saveCustom [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
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
        if(user.getStatus().isAdmin()) {
            return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
        }
        return Arrays.asList(new SimpleGrantedAuthority("CLIENT"));
    }
}
