package deque;

public class LinkedListDeque<Type> {
    public class StuffNode
    {
        Type item;
        StuffNode next;
        StuffNode prev;

        public StuffNode(StuffNode p, Type i, StuffNode n)
        {
            prev = p;
            item = i;
            next = n;
        }
    }
    public StuffNode sentinel;
    public int size;

    public LinkedListDeque() {
        sentinel = new  StuffNode(null, null, null);
        size = 0;
    }

    public LinkedListDeque(Type x) {
        sentinel = new StuffNode(sentinel.next, null ,null);
        sentinel.next = new StuffNode(sentinel, x, sentinel);
        size += 1;
    }

    public void addFirst(Type x) {
        StuffNode p = sentinel.next;
        if(p == null) {
            sentinel.next = new StuffNode(sentinel, x ,null);
        }
        else {
            sentinel.next = new StuffNode(sentinel, x ,p);
            p.prev = sentinel.next;
            p.next = sentinel;
        }
        size += 1;

    }

    public void addLast(Type x) {
        StuffNode p = sentinel.prev;
        if(p == null) {
            sentinel.next = new StuffNode(sentinel, x, null);
            sentinel.prev = sentinel.next;
        }
        else {
            p.next = new StuffNode(p, x, sentinel);
        }

        size += 1;
    }

    public boolean isEmpty() {
        if (size == 0)
        {
            return  true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        StuffNode p = sentinel.next;
        while (p != null) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    public Type removeFirst() {
        StuffNode FirstOne = sentinel.next;
        if(FirstOne == null)
        {
            return null;
        }
        else {
            StuffNode SecondOne = FirstOne.next;
            FirstOne.prev = null;
            size -= 1;
            if(SecondOne == null)
            {
                sentinel.next = null;
                return FirstOne.item;
            }
            else{
                sentinel.next = SecondOne;
                SecondOne.prev = sentinel;
                return FirstOne.item;
            }
        }
    }

    public Type removeLast() {
        StuffNode lastOne = sentinel.prev;
        if(lastOne != null) {
            StuffNode Previous = lastOne.prev;
            sentinel.prev = Previous;
            Previous.next = sentinel;
            lastOne.next = null;
            return Previous.item;
        }
        return null;
    }

    public Type get (int i) {
        StuffNode p = sentinel;
        while (i > size) {
            p = p.next;
            i -= 1;
        }
        if (i > 0)
        {
            return null;
        }
        return p.item;
    }
}
