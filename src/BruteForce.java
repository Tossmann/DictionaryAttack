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

		String[] splittetMessage;
		for (int i = 2; i < 10; i ++) {
			for (int j = 0; j < i; j ++) {
				String partOfMessage = message.substring(j, j+9);
				splittetMessage = partOfMessage.split("(?<=\\G.{" + i + "})");
				if (analyzeSplittetMessage(splittetMessage) >= (1/i))
					return true;
			}
		}
		return false;
	}
	
	private static double analyzeSplittetMessage(String [] splittetMessage) {
		int result = 0;
		int amountOfParts = 0;
		for (String part : splittetMessage){
			amountOfParts ++;
		      if (part.length() > 1 && dictionary.contains(part)) {
		         result ++;
		      }
		   }
		return result / amountOfParts;
	}
}
