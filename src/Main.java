import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Main {

	private static HashSet<String> dictionary = new HashSet<>();

	public static void main(String[] args) throws Exception{

		/*

		byte[]        input = new byte[] { 
				0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 
				(byte)0x88, (byte)0x99, (byte)0xaa, (byte)0xbb,
				(byte)0xcc, (byte)0xdd, (byte)0xee, (byte)0xff };
		byte[]        keyBytes = new byte[] { 
				0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
				0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
				0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };

		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

		Cipher        cipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");


		System.out.println("input text : " + Utils.toHex(input));

		// encryption pass

		byte[] cipherText = new byte[input.length];

		cipher.init(Cipher.ENCRYPT_MODE, key);

		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);

		ctLength += cipher.doFinal(cipherText, ctLength);

		System.out.println("cipher text: " + Utils.toHex(cipherText) + " bytes: " + ctLength);

		// decryption pass

		byte[] plainText = new byte[ctLength];

		cipher.init(Cipher.DECRYPT_MODE, key);

		int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);

		ptLength += cipher.doFinal(plainText, ptLength);

		System.out.println("plain text : " + Utils.toHex(plainText) + " bytes: " + ptLength);
		 */
		BufferedReader br = null;
		String sCurrentLine;
		br = new BufferedReader(new FileReader("src/words.txt"));

		while ((sCurrentLine = br.readLine()) != null) {
			dictionary.add(sCurrentLine);
		}

		if (br != null) br.close();
		System.out.println(dictionary.size());
		findRandomWord();
		findRandomWord();
	}

	public static void findRandomWord(){
		Random rn = new Random();
		int randomNumber = rn.nextInt(dictionary.size());
		Iterator it = dictionary.iterator();
		int counter = 0;

		while(counter < randomNumber){
			it.next();
			counter++;
		}
		System.out.println(it.next());	
	}
}