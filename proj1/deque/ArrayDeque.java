package deque;

public class ArrayDeque<Item> {
    Item[] items;
    int size;
    int nextFirst;
    int nextLast;

    public ArrayDeque() {
        items = (Item[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
        size = 0;
    }

    public ArrayDeque(Item item) {
        items[3] = item;
        size = 1;
        nextFirst = 2;
        nextLast = 4;
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
        if(nextLast == items.length) {
            nextLast -= items.length;
            items[nextLast] = x;
        }
        else {
            items[nextLast] = x;
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
        nextFirst += 1;
        return x;
    }

    public Item removeLast() {
        if(isEmpty()){
            return null;
        }
        Item x = items[nextLast - 1];
        items[nextLast - 1] = null;
        nextLast -= 1;
        size -= 1;
        return x;
    }

    public Item get(int i) {
        if (i < 0 || i > size - 1) {
            return null;
        }
        int itemIndex = nextFirst + 1 + i; //*
        return items[itemIndex];
    }

}
