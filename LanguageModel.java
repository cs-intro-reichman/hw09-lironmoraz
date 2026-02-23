import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // Maps sequences to their corresponding character data lists
    HashMap<String, List> usageMap;
    
    int winSize;
    
    private Random randomizer;

    /** Constructs a language model with the given window length and a seed. */
    public LanguageModel(int winSize, int seed) {
        this.winSize = winSize;
        this.randomizer = new Random(seed);
        this.usageMap = new HashMap<String, List>();
    }

    /** Constructs a language model with the given window length. */
    public LanguageModel(int winSize) {
        this.winSize = winSize;
        this.randomizer = new Random();
        this.usageMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file. */
    public void train(String fileName) {
        String currentWindow = "";
        char nextChar;

        In inputReader = new In(fileName);
        
        while ((!inputReader.isEmpty()) && (currentWindow.length() < winSize)) {
            nextChar = inputReader.readChar();
            currentWindow += nextChar;
        }
        
        while (!inputReader.isEmpty()) {
            nextChar = inputReader.readChar();
            
            List stats = usageMap.get(currentWindow);
            if (stats == null) {
                stats = new List();
                usageMap.put(currentWindow, stats);
            }
            stats.update(nextChar);

            currentWindow += nextChar;
            currentWindow = currentWindow.substring(1);
        }

        for (List stats : usageMap.values())
            computeDistributions(stats);
    }

    // Sets the probabilities for all characters in the list.
    void computeDistributions(List stats) {               
        int totalCount = 0;
        for (int i = 0; i < stats.getSize(); ++i) {
            totalCount += stats.get(i).count;
        }

        for (int i = 0; i < stats.getSize(); ++i) {
            stats.get(i).prob = stats.get(i).count / (double)totalCount; 
            stats.get(i).cumProb = stats.get(i).prob + (i > 0 ? stats.get(i - 1).cumProb : 0); 
        }
    }

    // Returns a random character based on the learned distribution.
    char pickRandom(List stats) {
        double r = randomizer.nextDouble();
        char result = ' ';
        for (int i = 0; i < stats.getSize(); ++i) {
            if (r < stats.get(i).cumProb) {
                result = stats.get(i).symbol; // Using 'symbol' from our updated CharData
                break;
            }
        }
        return result;
    }

    /** Generates a random text based on the learned probabilities. */
    public String generate(String prefix, int length) {
        if (prefix.length() < winSize)
            return prefix;

        String output = prefix;
        for (int i = 0; i < length; ++i) {
            String currentWin = output.substring(output.length() - winSize);
            List stats = usageMap.get(currentWin);
            if (stats == null)
                return output;

            output += pickRandom(stats);
        }
        return output;
    }

    /** Returns a string representing the map of this language model. */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : usageMap.keySet()) {
            List stats = usageMap.get(key);
            sb.append(key + " : " + stats + "\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int winSize = Integer.parseInt(args[0]);
        String prefix = args[1];
        int totalLength = Integer.parseInt(args[2]);
        Boolean isRandom = args[3].equals("random");
        String file = args[4];

        LanguageModel model;
        if (isRandom)
            model = new LanguageModel(winSize);
        else
            model = new LanguageModel(winSize, 20);

        model.train(file);
        System.out.println(model.generate(prefix, totalLength));
    }
}