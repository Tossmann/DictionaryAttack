import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class BruteForce {
	
	private static HashSet<String> dictionary = new HashSet<>();
	
	public BruteForce (HashSet<String> dictionary) {
		this.dictionary = dictionary;
	}
	
	public ArrayList<String> doBruteForcing(String message) {
		ArrayList probabelPlainMessages = new ArrayList();
		
		Iterator iterator = dictionary.iterator();
		
		while (iterator.hasNext()) {
			String probabelKey = (String) iterator.next();
		    String probabelPlainMessage = EnAndDecryption.decrypt(probabelKey,message);
		    if (analyzeProbabelPlainMessage(probabelPlainMessage)) 
		    		probabelPlainMessages.add(probabelPlainMessage);
		}
		return probabelPlainMessages;
	}
	
	public static Boolean analyzeProbabelPlainMessage(String message) {
		String firstPartOfMessage = message.substring(0, 9);
		String[] splittetMessage;
		for (int i = 2; i < 10; i ++) {
			splittetMessage = firstPartOfMessage.split("(?<=\\G.{" + i + "})");
			if (analyzeSplittetMessage(splittetMessage))
				return true;
		}
		return false;
	}
	
	private static Boolean analyzeSplittetMessage(String [] splittetMessage) {
		for (String part : splittetMessage){
		      if (part.length() > 1 && dictionary.contains(part)) {
		         return true;
		      }
		   }
		return false;
	}
}
