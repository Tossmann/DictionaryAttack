import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;



public class Main {

	private static HashSet<String> dictionary = new HashSet<>();

	public static void main(String[] args) throws Exception{
		importWordList();
		test();
	//	ourInteface();
	}
	private static void test() throws Exception{
		//EnAndDecryption.testFuncionality();

		//BruteForce bruteForce = new BruteForce(dictionary);
		//ArrayList<String> answers = bruteForce.doBruteForcing("2d9d562a1e9c0e2aaf60d1d4cdfff0e163696ab3581b486707c462917488cca4e5dfde6ffdd30820ca53308a2dd55f93bc65d31c9874d99f730769c152401151c9c928126df4be1a52f5838516d3b439496d2780b8b3a74b5c354f03f0dbc2ea2c7de4f268856c02803747dea0b84cd24387c3ce2e0cc3db9938667a1a3c5617e9b2cac8fe9a78985c10c20c93a177c5");

		//System.out.println(EnAndDecryption.decryptFromTimur("strawberry","2d9d562a1e9c0e2aaf60d1d4cdfff0e163696ab3581b486707c462917488cca4e5dfde6ffdd30820ca53308a2dd55f93bc65d31c9874d99f730769c152401151c9c928126df4be1a52f5838516d3b439496d2780b8b3a74b5c354f03f0dbc2ea2c7de4f268856c02803747dea0b84cd24387c3ce2e0cc3db9938667a1a3c5617e9b2cac8fe9a78985c10c20c93a177c5"));
		//System.out.println(EnAndDecryption.decrypt("strawberry","2d9d562a1e9c0e2aaf60d1d4cdfff0e163696ab3581b486707c462917488cca4e5dfde6ffdd30820ca53308a2dd55f93bc65d31c9874d99f730769c152401151c9c928126df4be1a52f5838516d3b439496d2780b8b3a74b5c354f03f0dbc2ea2c7de4f268856c02803747dea0b84cd24387c3ce2e0cc3db9938667a1a3c5617e9b2cac8fe9a78985c10c20c93a177c5"));

		System.out.println(EnAndDecryption.timurToHex("strawberry"));
		System.out.println((EnAndDecryption.toHex("strawberry")));


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
		ArrayList<String> answers = bruteForce.doBruteForcing(cypher);
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
