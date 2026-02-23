public class CharData {
    char chr;
    int count; 
    double p;    
    double cp;

    public CharData(char chr) {
        this.chr = chr;
        this.count = 1;
        this.p = 0;
        this.cp = 0;
    }

    public boolean equals(char otherChar) {
        return this.chr == otherChar;
    }
    
    public String toString() {
        return "(" + chr + " " + count + " " + p + " " + cp + ")";
    }
}