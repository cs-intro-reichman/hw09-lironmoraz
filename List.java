public class List {
    private Node first; // חייב להישאר first כי אולי ה-Tester ניגש אליו
    private int size;
    
    public List() {
        first = null;
        size = 0;
    }
    
    public int getSize() {
          return size;
    }

    public CharData getFirst() {
        if (first == null) return null;
        return first.cp;
    }

    public void addFirst(char chr) {
        Node n = new Node(new CharData(chr));
        n.next = first;
        first = n;
        size++;
    }
    
    public String toString() {
        if (size == 0) return "()";
        StringBuilder sb = new StringBuilder("(");
        Node ptr = first;
        while (ptr != null) {
            sb.append(ptr.cp + " ");
            ptr = ptr.next; 
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    public int indexOf(char chr) {
        Node ptr = first;
        int idx = 0;
        while (ptr != null) {
            if (ptr.cp.equals(chr)) return idx;
            ptr = ptr.next;
            idx++;
        }
        return -1;
    }

    public void update(char chr) {
        int res = indexOf(chr); 
        if (res == -1) {
            addFirst(chr);
        } else { 
            get(res).count++;
        }
    }

    public boolean remove(char chr) {
        if (indexOf(chr) == -1) return false;
        Node prev = null;
        Node curr = first;
        while (!curr.cp.equals(chr)) {
            prev = curr;
            curr = curr.next;
        }
        if (prev == null) first = curr.next;
        else prev.next = curr.next;
        size--;
        return true;
    }

    public CharData get(int index) {
          if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
          Node ptr = first;
          for (int i = 0; i < index; i++) ptr = ptr.next;
          return ptr.cp;
    }

    public CharData[] toArray() {
        CharData[] res = new CharData[size];
        Node ptr = first;
        int i = 0;
        while (ptr != null) {
            res[i++] = ptr.cp;
            ptr = ptr.next;
        }
        return res;
    }

    public ListIterator listIterator(int index) {
        if (size == 0) return null;
        Node ptr = first;
        for (int i = 0; i < index; i++) ptr = ptr.next;
        return new ListIterator(ptr);
    }
}