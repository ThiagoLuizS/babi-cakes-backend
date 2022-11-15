package br.com.babicakesbackend.config;

import br.com.babicakesbackend.exception.ExactaExceptionHandler;
import br.com.babicakesbackend.service.JwtManager;
import br.com.babicakesbackend.util.ConstantUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AuthorizationFilter extends OncePerRequestFilter {

	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(Objects.isNull(jwt) || !jwt.startsWith(ConstantUtils.JWT_PROVIDER)) {
			String authExceptionString = new ObjectMapper()
					.writeValueAsString(ExactaExceptionHandler.handleErrorBadCrendentialUnauthorizadTokenException());
			response.getWriter().write(authExceptionString);
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return;
		}
		
		try {
			jwt = jwt.replace(ConstantUtils.JWT_PROVIDER, "");
			Claims claims = new JwtManager().parseToken(jwt);
			List<String> roles = (List<String>) claims.get(ConstantUtils.JWT_ROLE_KEY);
			List<GrantedAuthority> authorities = roles.stream().map(r -> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities));
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			String authExceptionString = new ObjectMapper()
					.writeValueAsString(ExactaExceptionHandler.handleErrorBadCrendentialUnauthorizadTokenException());
			response.getWriter().write(authExceptionString);
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}

}
