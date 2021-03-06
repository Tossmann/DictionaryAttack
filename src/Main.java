import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;



public class Main {

	private static HashSet<String> dictionary = new HashSet<>();

	public static void main(String[] args) throws Exception{
		importWordList();
		createMessage("9", "feel good");
		//EnAndDecryption.testFuncionality();
		test();
		//ourInteface();
	}
	private static void createMessage(String number, String message) throws Exception {
		String encryptedMessage1 = EnAndDecryption.encrypt("library", message);
		String encryptedMessage2 = EnAndDecryption.encrypt("library" + number, message);
		System.out.println("----------------------------------");
		System.out.println("Message that we encrypted: " + message);
		System.out.println("Password #1: library");
		System.out.println("EncryptedMessage #1: "+ encryptedMessage1);
		System.out.println("Password #2: library" + number);
		System.out.println("EncryptedMessage #2: "+ encryptedMessage2);
		System.out.println("----------------------------------");
	}
	private static void test() throws Exception{

		BruteForce bruteForce = new BruteForce(dictionary);
		bruteForce.doBruteForcing("563f38edc2dd18084af8cb3cae390aa1ef03475e09460d91");
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
		String cypher = EnAndDecryption.encrypt(password,message);
		System.out.println("Cypher: " + cypher);
	}

	private static void bruteForce() throws Exception {
		String cypher = getString("Please enter the cypher-message: ");

		BruteForce bruteForce = new BruteForce(dictionary);
		bruteForce.doBruteForcing(cypher);
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

	private static void printExitText() {
		System.out.println("Bye"); 
	}

	private static void printOptions() {
		System.out.println();
		System.out.println("what you want to do? Your options are:");
		System.out.println("'exit' 'encrypt' 'bruteforce'");
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
		System.out.println("Dictionary has " + dictionary.size() + " entries.");
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
	
	public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        System.out.println(Arrays.toString(data));
        return data;
	}
}
