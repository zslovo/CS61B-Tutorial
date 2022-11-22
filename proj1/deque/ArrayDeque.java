package deque;

public class ArrayDeque<Item> {
    Item[] items;
    int size;
    int nextFirst;
    int nextLast;

    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
    }

    public void addFirst(Item x) {
        items[nextFirst] = x;
        if(nextFirst == 0) {
            nextFirst = items.length -  1;
        }
        else {
            nextFirst -= 1;
        }

        size += 1;
    }

    public void addLast(Item x) {
        items[nextLast] = x;
        if(nextLast == items.length) {
            nextLast -= items.length;
        }
        else {
            nextLast += 1;
        }

        size += 1;

    }

    public boolean isEmpty() {
        if(size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for(int i = 0;i < items.length; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    public Item removeFirst() {
        if(isEmpty()){
            return null;
        }
        Item x = items[nextFirst + 1];
        items [nextFirst + 1] = null;
        size -= 1;
        return x;
    }

    public Item removeLast() {
        if(isEmpty()){
            return null;
        }
        Item x = items[nextLast - 1];
        items[nextLast - 1] = null;
        size -= 1;
        return x;
    }

    public Item get(int i) {
        return items[i];
    }

}
