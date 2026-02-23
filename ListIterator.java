/** Represents an iterator over a list of CharData objects. */
public class ListIterator {

    Node cursor;

    /** Constructs a list iterator, starting at the given node. */
    public ListIterator(Node node) {
        this.cursor = node;
    }

    /** Checks if this iterator has more nodes to process. */
    public boolean hasNext() {
        return cursor != null;
    }
  
    /** Returns the CharData object and advances the iterator. */
    public CharData next() {
        CharData data = cursor.cp;
        cursor = cursor.next;
        return data;
    }
}