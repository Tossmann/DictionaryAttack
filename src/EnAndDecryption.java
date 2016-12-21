import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

//import org.bouncycastle.util.encoders.Hex;

public class EnAndDecryption {

	public static void testFuncionality() throws Exception {
		System.out.println("Functionality Test for Encryption and Decryption runs ...");

		String testKey = "tree";
		String testMessage = "home is berlin";
		String encryptionResult = encrypt(testKey, testMessage);
		String decryptionResult = decrypt(testKey, encryptionResult);
		System.out.println(decryptionResult);

		if (decryptionResult.equals(testMessage))
		    System.out.println("Functionality Test for Encryption and Decryption was successfull");
        else
            System.out.println("Functionality Test for Encryption and Decryption failed");
        System.out.println();
	}
	
	public static String encrypt(String password, String message) throws Exception {

		byte[] keyBytes;
		byte[] input;
		byte[] cipherText;

		String hexPw = convertStringToHex(password);
		String hexMessage = convertStringToHex(message);
		hexPw = doPadding(hexPw);
		hexMessage = doPadding(hexMessage);
		input = toByteArray(hexMessage);
		keyBytes = toByteArray(hexPw);

		SecretKeySpec key = new SecretKeySpec(keyBytes, "Blowfish");
		Cipher        cipher = Cipher.getInstance("Blowfish/ECB/NoPadding", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		cipherText = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);

		return Utils.toHex(cipherText);
	}
	
	private static String doPadding(String s) {
		while((s.length()/2) % 8 != 0){
			s += "0";
		}
		return s;
	}

	public static String decrypt(String password, String ciphertextHex) throws Exception {

		byte[] keyBytes;
		byte[] input;
		byte[] cipherText;

		String hexPw = convertStringToHex(password);
		//hexPw = doPadding(hexPw);
		input = toByteArray(ciphertextHex);
		keyBytes = toByteArray(hexPw);

		SecretKeySpec key = new SecretKeySpec(keyBytes, "Blowfish");
		Cipher        cipher = Cipher.getInstance("Blowfish/ECB/NoPadding", "BC");
		cipher.init(Cipher.DECRYPT_MODE, key);
		cipherText = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		
		return new String(cipherText, "UTF-8").trim();
	}
	
	public static byte[] toByteArray(String hexString){
		return DatatypeConverter.parseHexBinary(hexString);
	}


	private static String convertStringToHex(String s) { 
        if (s.length() == 0) 
            return ""; 
        char c; 
        StringBuffer buff = new StringBuffer(); 
        for (int i = 0; i < s.length(); i++) { 
            c = s.charAt(i); 
            buff.append(Integer.toHexString(c)); 
        } 
        return buff.toString().trim(); 
    } 
}
