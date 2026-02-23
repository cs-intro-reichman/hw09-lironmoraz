/** A linked list of character data objects. */
public class List {

    private Node head;
    private int listSize;
    
    /** Constructs an empty list. */
    public List() {
        head = null;
        listSize = 0;
    }
    
    /** Returns the number of elements in this list. */
    public int getSize() {
          return listSize;
    }

    /** Returns the CharData of the first element in this list. */
    public CharData getFirst() {
        if (head == null) {
            return null;
        }
        return head.cp;
    }

    /** Adds a CharData object with the given character to the beginning of this list. */
    public void addFirst(char symbol) {
        Node newNode = new Node(new CharData(symbol));
        newNode.next = head;
        head = newNode;
        listSize++;
    }
    
    /** Textual representation of this list. */
    public String toString() {
        if (listSize == 0) return "()";
        StringBuilder sb = new StringBuilder("(");
        
        Node ptr = head;
        while (ptr != null) {
            sb.append(ptr.cp + " ");
            ptr = ptr.next; 
        }
        
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    /** Returns the index of the first CharData object with the same symbol. */
    public int indexOf(char symbol) {
        Node ptr = head;
        int index = 0;
        while (ptr != null) {
            if (ptr.cp.equals(symbol)) {
                return index;
            }
            ptr = ptr.next;
            index++;
        }
        return -1;
    }

    /** Updates the counter if character exists, otherwise adds it. */
    public void update(char symbol) {
        int pos = indexOf(symbol); 
        if (pos == -1) {
            addFirst(symbol);
        } else { 
            CharData data = get(pos);
            data.count++;
        }
    }

    /** Removes the CharData object with the given symbol. */
    public boolean remove(char symbol) {
        if (indexOf(symbol) == -1) return false;
        
        Node previous = null;
        Node ptr = head;
        
        while (!ptr.cp.equals(symbol)) {
            previous = ptr;
            ptr = ptr.next;
        }
        
        if (previous == null) {
            head = ptr.next;
        } else {
            previous.next = ptr.next;
        }
        
        listSize--;
        return true;
    }

    /** Returns the CharData object at the specified index. */
    public CharData get(int index) {
          if (index < 0 || index >= this.listSize) {
                throw new IndexOutOfBoundsException("Invalid index: " + index);
          }
          Node ptr = head;
          for (int i = 0; i < index; i++) {
              ptr = ptr.next;
          }
          return ptr.cp;
    }

    /** Returns an array containing all CharData objects in this list. */
    public CharData[] toArray() {
        CharData[] result = new CharData[listSize];
        Node ptr = head;
        int i = 0;
        while (ptr != null) {
            result[i++] = ptr.cp;
            ptr = ptr.next;
        }
        return result;
    }

    /** Returns an iterator over the elements in this list. */
    public ListIterator listIterator(int index) {
        if (listSize == 0) return null;
        
        Node ptr = head;
        for (int i = 0; i < index; i++) {
            ptr = ptr.next;
        }
        return new ListIterator(ptr);
    }
}