package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.exception.NotFoundException;
import br.com.babicakesbackend.models.dto.UserForm;
import br.com.babicakesbackend.models.dto.UserFormGoogle;
import br.com.babicakesbackend.models.dto.UserSimpleForm;
import br.com.babicakesbackend.models.dto.UserView;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.enumerators.UserOriginEnum;
import br.com.babicakesbackend.models.enumerators.UserStatusEnum;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.models.mapper.UserMapperImpl;
import br.com.babicakesbackend.repository.UserRepository;
import br.com.babicakesbackend.util.ConstantUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class UserService extends AbstractService<User, UserView, UserForm> implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapperImpl userMapper;

//    private final AuthenticationService authenticationService;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }
    @Override
    protected MapStructMapper<User, UserView, UserForm> getConverter() {
        return userMapper;
    }

    public UserView saveCustom(UserForm userForm) {
        try {
            log.info(">> saveCustom [userEmail={}]", userForm.getEmail());
            Optional<User> user = userRepository.findByEmail(userForm.getEmail());

            if(user.isPresent()) {
                return getConverter().entityToView(user.get());
            }

            /**USUÁRIO QUE SE CADASTRAM PELO GOOGLE NÃO POSSUEM TELEFONE INICIALMENTE, ENTÃO ESSA CONDIÇÃO NÂO IMPEDE
             * DE SEREM CADASTRADOS*/
            if(Objects.isNull(userForm.getOrigin()) || !Objects.equals(userForm.getOrigin(), UserOriginEnum.GOOGLE)) {
                String phone = ConstantUtils.validPhone(userForm.getPhone());
                userForm.setPhone(phone);
            }

            if(Objects.isNull(userForm.getOrigin())) {
                userForm.setOrigin(UserOriginEnum.APP);
            }

            userForm.setStatus(UserStatusEnum.ACTIVE);

            ConstantUtils.validEmail(userForm.getEmail());
;
            User userConvert = userMapper.formToEntity(userForm);

            userConvert = userRepository.save(userConvert);

            log.info("<< saveCustom [userId={}]", userConvert.getId());

            return getConverter().entityToView(userConvert);

        } catch (Exception e) {
            log.error("<< saveCustom [error={}]", e.getMessage());
            throw new GlobalException("Não foi possivel criar seu usuário");
        }
    }

    public UserView updateCustom(UserSimpleForm userSimpleForm) {
        try {
            log.info(">> updateCustom [userSimpleForm={}]", userSimpleForm.getEmail());

            Optional<User> user = userRepository.findByEmail(userSimpleForm.getEmail());

            user.orElseThrow(() -> new GlobalException("Usuário não encontrado"));

            String phone = ConstantUtils.validPhone(userSimpleForm.getPhone());

            ConstantUtils.validEmail(userSimpleForm.getEmail());

            User userConvert = user.get();

            userConvert.setName(userSimpleForm.getName());
            userConvert.setEmail(userSimpleForm.getEmail());
            userConvert.setBirthday(userSimpleForm.getBirthday());
            userConvert.setPhone(phone);

            userConvert = userRepository.save(userConvert);

            return getConverter().entityToView(userConvert);

        } catch (Exception e) {
            log.error("<< saveCustom [error={}]", e.getMessage());
            throw new GlobalException("Não foi possivel atualizar seu usuário");
        }

    }

    public UserView saveGoogleCustom(UserFormGoogle formGoogle) {

        Optional<User> user = findByEmail(formGoogle.getEmail());

        if(user.isPresent()) {
            return getConverter().entityToView(user.get());
        }

        UserForm userForm = UserForm.builder()
                .name(StringUtils.isEmpty(formGoogle.getName()) ? formGoogle.getEmail() : formGoogle.getName())
                .email(formGoogle.getEmail())
                .password(formGoogle.getPassword())
                .origin(UserOriginEnum.GOOGLE).build();

        return saveCustom(userForm);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserView getUserViewByEmail(String email) {
        Optional<User> user = findByEmail(email);
        return getConverter().entityToView(user.get());
    }

    public void validCompletedFormUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        user.orElseThrow(() -> new GlobalException("Usuário não encontrado"));

        if(StringUtils.isBlank(user.get().getPhone())) {
            throw new GlobalException("Complete seu cadastro, informe um telefone válido");
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
