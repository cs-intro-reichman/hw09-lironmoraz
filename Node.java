/** Represents a node in a linked list. */
public class Node {
   
    CharData data;
    Node next;

    /** Constructs a node with the given CharData object and the next node. */
    public Node(CharData data, Node next) {
       this.data = data;
       this.next = next;
    }
         
    /** Constructs a node with the given CharData object. */
    public Node(CharData data) {
       this(data, null);
    }

    /** Returns the string representation of the data in this node. */
    public String toString() {
       return "" + data;
    }
 }