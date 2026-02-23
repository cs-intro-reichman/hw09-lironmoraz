/** Represents a character data object. */
public class CharData {

    char symbol;
    int times; 
    double prob;    
    double cumProb;

    /** Creates and initializes a character data object. */
    public CharData(char symbol) {
        this.symbol = symbol;
        this.times = 1;
        this.prob = 0;
        this.cumProb = 0;
    }

    /** Checks if the character of this CharData object equals the given character. */
    public boolean equals(char other) {
        return this.symbol == other;
    }
    
    /** Returns a textual representation of this CharData object. */
    public String toString() {
        return "(" + symbol + " " + times + " " + prob + " " + cumProb + ")";
    }
}