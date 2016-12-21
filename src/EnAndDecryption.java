import java.math.BigInteger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class EnAndDecryption {

	public static void testFuncionality() throws Exception {
		System.out.println("Functionality Test for Encryption and Decryption runs ...");

		String testKey = "hohohop";
		String testMessage = "blablub";
		String encryptionResult = encrypt(testKey, testMessage);
		String decryptionResult = decrypt(testKey, encryptionResult).trim();

		if (decryptionResult.equals(testMessage))
		    System.out.println("Functionality Test for Encryption and Decryption was successfull");
        else
            System.out.println("Functionality Test for Encryption and Decryption failed");
        System.out.println();
	}
	
	public static String encrypt(String password, String message) throws Exception {

		byte[] keyBytes = new byte[16];
		byte[] input = new byte[16];
		byte[] cipherText;

		String hexPw = toHex(password);
		String hexMessage = toHex(message);
		input = toByteArray(hexMessage);
		keyBytes = toByteArray(hexPw);


		SecretKeySpec key = new SecretKeySpec(keyBytes, "Blowfish");
		Cipher        cipher = Cipher.getInstance("Blowfish/ECB/ISO10126Padding", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		cipherText = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);

		return Utils.toHex(cipherText);
	}
	
	public static String decrypt(String password, String ciphertextHex) throws Exception {

		byte[] keyBytes = new byte[16];
		byte[] input = new byte[16];
		byte[] cipherText;

		String hexPw = toHex(password);
		input = toByteArray(ciphertextHex);
		keyBytes = toByteArray(hexPw);


		SecretKeySpec key = new SecretKeySpec(keyBytes, "Blowfish");
		Cipher        cipher = Cipher.getInstance("Blowfish/ECB/NoPadding", "BC");
		cipher.init(Cipher.DECRYPT_MODE, key);
		cipherText = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		
		return new String(cipherText, "UTF-8");
	}
	
	public static byte[] toByteArray(String hexString){
		return DatatypeConverter.parseHexBinary(hexString);
	}

	public static String toHex(String arg) {
		return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
	}


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        String result = "";
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
            result += ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String timurToHex(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        String result = "";
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
            result += ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return result;
    }

        public static String decryptFromTimur(String probableKey, String msg) throws Exception {

            byte[] input = hexStringToByteArray(msg);

            byte[] keyBytes = hexStringToByteArray(probableKey);

            SecretKeySpec key = new SecretKeySpec(keyBytes, "Blowfish");

            Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");

            byte[] plainText = new byte[input.length];

            cipher.init(Cipher.DECRYPT_MODE, key);

            int ptLength = cipher.update(input, 0, input.length, plainText, 0);

            cipher.doFinal(plainText, ptLength);

            byte[] bytes = DatatypeConverter.parseHexBinary(Utils.toHex(plainText));

            return new String(bytes, "UTF-8");

    }
	
}
