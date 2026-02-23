public class Node {
    CharData cp;
    Node next;

    public Node(CharData cp, Node nextNode) {
       this.cp = cp;
       this.next = nextNode;
    }
         
    public Node(CharData data) {
       this(data, null);
    }

    public String toString() {
       return "" + cp;
    }
}