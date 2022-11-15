package br.com.babicakesbackend.config;

import br.com.babicakesbackend.util.ConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncode implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return ConstantUtils.getSecurityHash(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return StringUtils.equals(encodedPassword, encode(rawPassword));
	}

}
