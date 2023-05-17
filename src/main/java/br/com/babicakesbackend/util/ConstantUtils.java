package br.com.babicakesbackend.util;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.enumerators.ParameterizationEnvEnum;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.Objects;
import java.util.Random;

public class ConstantUtils {

	public static final String JWT_SUB_KEY = "sub";

	private ConstantUtils() {}
	
	public static final int JWT_EXP_DAYS = 1;
	public static final String API_KEYS = "secret";
	public static final String JWT_PROVIDER = "Bearer";
	public static final String JWT_ROLE_KEY = "role";

	public static final String ROUTE_ID_v1 = "babicakes-getdata-v1";

	@Value("${spring.profiles.active}")
	private static String activeProfile;

	public static String getSecurityHash(String text) {
		return DigestUtils.sha256Hex(text);
	}

	public static String validPhone(String phone) {

		phone = specialCharacterRemover(phone);

		if(StringUtils.length(phone) == 10 || StringUtils.length(phone) == 11) {
			return phone;
		}
		throw new GlobalException("O telefone informado não é válido. Informe o DDD + número");
	}

	public static ParameterizationEnvEnum getEnvParameterization() {
		return ParameterizationEnvEnum.getEnv(activeProfile);
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

	public static String getFirstName(String name) {
		String[] names = name.split(" ");
		if(CollectionUtils.size(names) > 0) {
			return names[0];
		}
		return name;
	}

	public static BigDecimal formatBigCentsToInt(BigDecimal value) {
		BigDecimal result = BigDecimal.ZERO;
		for (int i = 1; i < 10; i++) {
			BigDecimal compare = new BigDecimal("0.0"+i) ;
			if(value.compareTo(compare) == 0) {
				result = new BigDecimal(i);
				return result;
			}
		}
		return result;
	}

	public static String generatedRandomNumber(Integer digits) {
		if(Objects.nonNull(digits)) {
			StringBuilder text = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < digits; i++) {
				text.append(random.nextInt(10)); // gerar um número aleatório entre 0 e 9
			}
			return text.toString();
		} else {
			return String.valueOf(0);
		}
	}

	public static String formatToShar(String value) {
		return value.substring(0,8)
				.concat("-")
				.concat(value.substring(7,12))
				.concat("-")
				.concat(value.substring(11,14))
				.concat("-")
				.concat(value.substring(13,23));
	}

	public static Long generatedCode() {
		return new Random().nextLong(300000);
	}
}
