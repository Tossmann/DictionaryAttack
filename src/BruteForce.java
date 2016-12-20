import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BruteForce {
	
	private  HashSet<String> dictionary = new HashSet<>();
	
	public BruteForce (HashSet<String> dictionary) {
		this.dictionary = dictionary;
	}
	
	public ArrayList<String> doBruteForcing(String message) throws Exception {
		ArrayList probabelPlainMessages = new ArrayList();
		
		Iterator iterator = dictionary.iterator();
		
		while (iterator.hasNext()) {
			String probabelKey = (String) iterator.next();
		    String probabelPlainMessage = EnAndDecryption.decrypt(probabelKey,message);

		    if (analyzeProbabelPlainMessage(probabelPlainMessage)) {
                System.out.println(probabelPlainMessage + " - " + probabelKey);
                probabelPlainMessages.add(probabelPlainMessage);
            }
		}
        System.out.println("amountOfResults: " + probabelPlainMessages.size());
		return probabelPlainMessages;
	}
	
	public Boolean analyzeProbabelPlainMessage(String message) {

		Pattern letter = Pattern.compile("[A-Za-z]");
		String [] parts = message.split("(?!^)");
		double wholeAmount = 0;
		double amountOfTrue = 0;
        String withoutTrash = "";
		for(String part : parts) {
			wholeAmount ++;
			if (letter.matcher(part).matches()) {
                amountOfTrue++;
                withoutTrash += part;
            }
		}
		if ((amountOfTrue/wholeAmount) < 0.5)
			return false;


		if (withoutTrash.equals("heyGuyWhatsUpN"))
 		    System.out.println();




		String[] splittetMessage;
        int foundWords = 0;

		for (int i = 2; i < 10; i ++) {
            String filler = "";
			for (int j = 0; j < i; j ++) {
				splittetMessage = (filler + withoutTrash).split("(?<=\\G.{" + i + "})");
				if (analyzeSplittetMessage(splittetMessage))
				    foundWords ++;
                if (foundWords >= 11)
                    return true;
                filler += "-";
			}
		}
		return false;
	}
	
	private boolean analyzeSplittetMessage(String [] splittetMessage) {
		for (String part : splittetMessage){
		      if (part.length() > 1 && dictionary.contains(part.toLowerCase())) {
		         return true;
		      }
		   }
		return false;
	}
}
