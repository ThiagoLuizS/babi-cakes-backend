package br.com.babicakesbackend.util;

import br.com.babicakesbackend.exception.GlobalException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;

public class ConstantUtils {

	public static final String JWT_SUB_KEY = "sub";

	private ConstantUtils() {}
	
	public static final int JWT_EXP_DAYS = 1;
	public static final String API_KEYS = "secret";
	public static final String JWT_PROVIDER = "Bearer";
	public static final String JWT_ROLE_KEY = "role";

	public static String getSecurityHash(String text) {
		return DigestUtils.sha256Hex(text);
	}

	public static boolean validPhone(String phone) {

		phone = specialCharacterRemover(phone);

		if(StringUtils.length(phone) == 10 || StringUtils.length(phone) == 11) {
			return true;
		}
		throw new GlobalException("O telefone informado não é válido. Informe o DDD + número");
	}

	public static String specialCharacterRemover(String string) {
		string = StringUtils.remove(string, " ");
		return Normalizer.normalize(string.trim(), Normalizer.Form.NFD).replaceAll("[^a-zZ-Z1-9 ]", "");
	}

	public static boolean validEmail(String email) {
		if(email.toLowerCase().contains("@") &&
				(email.toLowerCase().contains(".com")
				|| email.toLowerCase().contains(".br"))) {
			return true;
		}
		throw new GlobalException("O e-mail informado não é um e-mail válido");
	}
}
