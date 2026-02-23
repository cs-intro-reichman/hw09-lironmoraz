import java.util.HashMap;
import java.util.Random;

public class LanguageModel {
    HashMap<String, List> CharDataMap; // חייב להישאר השם המקורי
    int windowLength;
    private Random randomGenerator;

    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        this.randomGenerator = new Random(seed);
        this.CharDataMap = new HashMap<String, List>();
    }

    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        this.randomGenerator = new Random();
        this.CharDataMap = new HashMap<String, List>();
    }

    public void train(String fileName) {
        String win = "";
        char ch;
        In input = new In(fileName);
        while ((!input.isEmpty()) && (win.length() < windowLength)) {
            win += input.readChar();
        }
        while (!input.isEmpty()) {
            ch = input.readChar();
            List list = CharDataMap.get(win);
            if (list == null) {
                list = new List();
                CharDataMap.put(win, list);
            }
            list.update(ch);
            win += ch;
            win = win.substring(1);
        }
        for (List list : CharDataMap.values()) calculateProbabilities(list);
    }

    // חייב להישאר השם המקורי עבור ה-Tester
    void calculateProbabilities(List list) {               
        int total = 0;
        for (int i = 0; i < list.getSize(); ++i) total += list.get(i).count;
        for (int i = 0; i < list.getSize(); ++i) {
            list.get(i).p = list.get(i).count / (double)total; 
            list.get(i).cp = list.get(i).p + (i > 0 ? list.get(i - 1).cp : 0); 
        }
    }

    // חייב להישאר השם המקורי עבור ה-Tester
    char getRandomChar(List list) {
        double r = randomGenerator.nextDouble();
        for (int i = 0; i < list.getSize(); ++i) {
            if (r < list.get(i).cp) return list.get(i).chr;
        }
        return ' ';
    }

    public String generate(String initialText, int textLength) {
        if (initialText.length() < windowLength) return initialText;
        String result = initialText;
        for (int i = 0; i < textLength; ++i) {
            String win = result.substring(result.length() - windowLength);
            List list = CharDataMap.get(win);
            if (list == null) return result;
            result += getRandomChar(list);
        }
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : CharDataMap.keySet()) {
            sb.append(key + " : " + CharDataMap.get(key) + "\n");
        }
        return sb.toString();
    }
} 