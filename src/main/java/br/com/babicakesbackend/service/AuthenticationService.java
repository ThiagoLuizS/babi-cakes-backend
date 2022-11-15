package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.NotFoundException;
import br.com.babicakesbackend.models.dto.LoginForm;
import br.com.babicakesbackend.models.dto.TokenDTO;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.enumerators.UserStatusEnum;
import br.com.babicakesbackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class AuthenticationService {

	@Autowired private AuthenticationManager authenticationManager;	
	@Autowired private UserRepository userRepository;
	@Autowired private JwtManager jwtManager;

	public TokenDTO authenticationManagerSignJwt(LoginForm form) {
		Optional<User> entity =  userRepository.findByEmail(form.getEmail());
		if (!entity.isPresent()) {
			throw new NotFoundException("Usuário não existe");
		}
		if(!entity.get().isEnabled()){
			throw new NotFoundException("Usuário não está ativo");
		}
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(auth);
		
		org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
		String email = userDetails.getUsername();
		List<String> roles = userDetails.getAuthorities()
			      .stream()
			      .map(authority -> authority.getAuthority())
			      .collect(Collectors.toList());
		return TokenDTO.builder()
				.token(jwtManager.createToken(email, roles))
				.type("JWT")
				.name(entity.get().getName())
				.phone(entity.get().getPhone())
				.build();
	}
}
