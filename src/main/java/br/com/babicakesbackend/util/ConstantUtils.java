package br.com.babicakesbackend.util;

import org.apache.commons.codec.digest.DigestUtils;

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
}
