package br.com.babicakesbackend.service;

import br.com.babicakesbackend.util.ConstantUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class JwtManager {
	
	public String createToken(String email, List<String> roles) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, ConstantUtils.JWT_EXP_DAYS);
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(calendar.getTime())
				.claim(ConstantUtils.JWT_ROLE_KEY, roles)
				.signWith(SignatureAlgorithm.HS512, ConstantUtils.API_KEYS.getBytes())
				.compact();
	}
	
	public Claims parseToken(String jwt) {
		return Jwts.parser()
				.setSigningKey(ConstantUtils.API_KEYS.getBytes())
				.parseClaimsJws(jwt)
				.getBody();
	}
}
