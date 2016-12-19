import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Main {

	private static HashSet<String> dictionary = new HashSet<>();

	public static void main(String[] args) throws Exception{
		importWordList();
		
		BruteForce brute = new BruteForce(dictionary);
		System.out.println(brute.analyzeProbabelPlainMessage("dfghhguygfdfghj"));
		
		ourInteface();
	}

	private static void ourInteface() {
		String input = "";
		Scanner in = new Scanner(System.in);
		
		while (!input.equals("exit")) {
			printOptions();
			input = in.nextLine();
			runOption(input);
		}
	}
	
	private static void runOption(String input) {
		switch (input) {
        case "encrypt": simpleEncryption();
                 break;
        case "exit" : 	printExitText();
        		break;
        case "bruteforce": bruteForce();
        		break;
		}
	}
	
	private static void simpleEncryption() {
		String password = getPassword();
		String message = getString("Please enter your message: ");
		String cypher = EnAndDecryption.encrypt(password,message);
		System.out.println("Cypher: " + cypher);
	}
	
	private static void bruteForce() {
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
