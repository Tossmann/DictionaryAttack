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
	
	public void doBruteForcing(String message) throws Exception {
        System.out.println("Bruteforcing runs ...");
        long start_time = System.currentTimeMillis();

		Iterator iterator = dictionary.iterator();
		int amountOfResults = 0;

		while (iterator.hasNext()) {
			String probabelKey = (String) iterator.next();
            if (probabelKey.length() > 0) {
                String probabelPlainMessage = EnAndDecryption.decrypt(probabelKey,message);

                if (analyzeProbabelPlainMessage(probabelPlainMessage)) {
                    System.out.println(probabelPlainMessage + " - " + probabelKey);
                    amountOfResults ++;
                }
            }
		}
        long end_time = System.currentTimeMillis();
		System.out.println("Bruteforcing ends. And needed " + (end_time - start_time) + " ms");
        System.out.println("AmountOfResults: " + amountOfResults);
	}

    public Boolean analyzeProbabelPlainMessage(String message) {

		if (! doesMessageHaveMinimumOfCharacters(message,0.5))
		    return false;

		return doesMessageHaveMinimumOfWordsInIt(message,2);
    }

	private boolean doesMessageHaveMinimumOfCharacters(String message, double procentage) {
        Pattern letter = Pattern.compile("[A-Za-z]");
        String [] parts = message.split("(?!^)");
        double wholeAmount = 0;
        double amountOfTrue = 0;
        for(String part : parts) {
            wholeAmount ++;
            if (letter.matcher(part).matches()) {
                amountOfTrue++;
            }
        }
        if ((amountOfTrue/wholeAmount) >= procentage)
            return true;
        return false;
    }

    private boolean doesMessageHaveMinimumOfWordsInIt(String message, int minAmountOfWords) {
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
                        if (amountOfWords >= minAmountOfWords)
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
