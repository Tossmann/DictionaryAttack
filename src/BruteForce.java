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
            if (probabelKey.equals("strawberry"))
                System.out.println();
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

        if(message.equals("g/�R&��\u0017-����\u0004\u0012���\u0004g*p�yH��3\u0007�O0��Ǭ���a5C�c�X����Pvu��8n�{�A�c1�'l-�����\u001C�\u0014�NI\u000BN���>.��J\u000Bb(;GStart�DJH�%]+Y}9���m)Z\u001A��Pw�çc�n;{�'�\u0003t�\u007Fg\u001F�ʂ"))
            System.out.println();

		Pattern letter = Pattern.compile("[A-Za-z]");
		String [] parts = message.split("\\P{Alpha}+");

        for (String part: parts) {
            if (part.length() > 2) {

                int amountOfWords = 0;

                String[] splittetMessage;
                int maxWordLength = 10;
                if (part.length() < 10)
                    maxWordLength = part.length() + 1;
                for (int i = 3; i < maxWordLength; i ++) {
                    String filler = "";
                    for (int j = 0; j < i; j ++) {
                        splittetMessage = (filler + part).split("(?<=\\G.{" + i + "})");
                        amountOfWords += analyzeSplittetMessage(splittetMessage);
                        if (amountOfWords >= 5)
                            return true;
                        filler += "-";
                    }
                }
            }
        }
        return false;

	}
	
	private int analyzeSplittetMessage(String [] splittetMessage) {
        int amountOfFoundWords = 0;
		for (String part : splittetMessage){
		      if (part.length() > 2 && dictionary.contains(part.toLowerCase())) {
                  amountOfFoundWords ++;
		      }
		   }
		return amountOfFoundWords;
	}
}
