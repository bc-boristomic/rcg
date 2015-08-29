package example1;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Example1 {

	public static String getEncriptedPasswordMD5(String password) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes(), 0, password.length());
			String result = new BigInteger(1, md5.digest()).toString(16);
			md5.reset();
			return result;
		} catch (NoSuchAlgorithmException e) {
			// TODO add to logger
		}
		return "INVALID PASSWORD";
	}

	/**
	 * Testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(getEncriptedPasswordMD5("password"));
		
	}

}
