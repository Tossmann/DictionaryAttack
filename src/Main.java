import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


public class Main {

	private static HashSet<String> dictionary = new HashSet<>();

	public static void main(String[] args) throws Exception{
		testFuncionality();
		importWordList();
		ourInteface();
		simpleEncryption();
	}

	private static void testFuncionality() throws Exception {
		String testKey = "hohoho";
		String testMessage = "blablub";
		String encryptionResult = encrypt(testKey, testMessage);
		String decryptionResult = decrypt(testKey, encryptionResult).trim();
		System.out.println(decryptionResult.equals(testMessage));
	}

	private static void ourInteface() throws Exception {
		String input = "";
		Scanner in = new Scanner(System.in);

		while (!input.equals("exit")) {
			printOptions();
			input = in.nextLine();
			runOption(input);
		}
	}

	private static void runOption(String input) throws Exception {
		switch (input) {
		case "encrypt": simpleEncryption();
		break;
		case "exit" : 	printExitText();
		break;
		case "bruteforce": bruteForce();
		break;
		}
	}

	private static void simpleEncryption() throws Exception {
		String password = getPassword();
		String message = getString("Please enter your message: ");
		String cypher = encrypt(password,message);
		System.out.println("Cypher: " + cypher);
	}

	private static void bruteForce() {
		String cypher = getString("Please enter the cypher-message: ");
		System.out.println(cypher);
	}

	private static String getPassword(){
		String password = findRandomWord();
		System.out.println("Your randomly choosed Password is: '" + password + "'");
		return password;
	}

	private static String getString(String message) {
		System.out.println(message);
		Scanner scanInput = new Scanner(System.in);
		String input = scanInput.nextLine();
		return input;
	}



	private static String encrypt(String password, String message) throws Exception {

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
	
	private static String decrypt(String password, String ciphertextHex) throws Exception {

		byte[] keyBytes = new byte[16];
		byte[] input = new byte[16];
		byte[] cipherText;

		String hexPw = toHex(password);
		input = toByteArray(ciphertextHex);
		keyBytes = toByteArray(hexPw);


		SecretKeySpec key = new SecretKeySpec(keyBytes, "Blowfish");
		Cipher        cipher = Cipher.getInstance("Blowfish/ECB/ISO10126Padding", "BC");
		cipher.init(Cipher.DECRYPT_MODE, key);
		cipherText = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		
		return new String(cipherText, "UTF-8");
	}

	private static void printExitText() {
		System.out.println("Bye"); 
	}

	private static void printOptions() {
		System.out.println();
		System.out.println("what you want to do? Your options are:");
		System.out.println("'exit' 'encrypt' 'bruteforce'");
	}	

	private static void encryption(){
	}

	public static byte[] toByteArray(String hexString){
		return DatatypeConverter.parseHexBinary(hexString);
	}

	public static String toHex(String arg) {
		return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
	}

	private static void importWordList() throws IOException {
		System.out.println("Import of Wordlists starts ...");

		BufferedReader br = null;
		String sCurrentLine;
		br = new BufferedReader(new FileReader("src/words.txt"));
		while ((sCurrentLine = br.readLine()) != null) {
			dictionary.add(sCurrentLine);
		}
		if (br != null) br.close();

		System.out.println("Import of Wordlists succeded");
		System.out.println("Dictanary has " + dictionary.size() + " entries.");
	}

	public static String findRandomWord(){
		Random rn = new Random();
		int randomNumber = rn.nextInt(dictionary.size());
		Iterator it = dictionary.iterator();
		int counter = 0;

		while(counter < randomNumber){
			it.next();
			counter++;
		}
		return (String)it.next();	
	}
}
