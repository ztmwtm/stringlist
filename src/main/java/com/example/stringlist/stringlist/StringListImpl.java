package com.example.stringlist.stringlist;

import com.example.stringlist.exception.StringListElementNotFound;
import com.example.stringlist.exception.StringListIndexOutOfBoundException;

import java.util.Arrays;

public class StringListImpl implements StringList {

    private int size;
    private int capacity;
    private String[] array;
    private final int GROWFACTOR = 2;


    public StringListImpl(int capacity) {
        if (capacity < 1) {
            capacity = 10;
        }
        this.size = 0;
        array = new String[capacity];
        this.capacity = capacity;
    }

    @Override
    public String add(String item) {
        if (size == capacity) {
            grow();
        }
        array[size++] = item;
        return item;
    }

    @Override
    public String add(int index, String item) {
        if (size == capacity) {
            grow();
        }
        checkIndex(index);
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = item;
        size++;
        return item;
    }


    @Override
    public String set(int index, String item) {
        checkIndex(index);
        String old = array[index];
        array[index] = item;
        return old;
    }

    @Override
    public String remove(String item) {
        int removeIndex = -1;
        for (int i = 0; i < size; i++) {
            if (item.equals(array[i])) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex == -1) {
            throw new StringListElementNotFound(String.format("Element %s not found", item));
        }

        return remove(removeIndex);
    }

    @Override
    public String remove(int index) {
        checkIndex(index);
        String old = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return old;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        int result = -1;
        for (int i = 0; i < size; i++) {
            if (item.equals(array[i])) {
                return i;
            }
        }
        return result;
    }

    @Override
    public int lastIndexOf(String item) {
        int result = -1;
        for (int i = size - 1; i >= 0; i--) {
            if (item.equals(array[i])) {
                return i;
            }
        }
        return result;
    }

    @Override
    public String get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        if (this == otherList) {
            return true;
        }
        if (otherList == null) {
            throw new NullPointerException("NPE Sorry gays");
        }

        StringListImpl that = (StringListImpl) otherList;

        if (size != that.size) return false;

        for (int i = 0; i < size; i++) {
            if (!array[i].equals(that.array[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public String[] toArray() {
        String[] result = new String[size];
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }

    private void grow() {
        String[] newArr = new String[capacity * GROWFACTOR];
        System.arraycopy(array, 0, newArr, 0, size);
        array = newArr;
        capacity *= GROWFACTOR;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new StringListIndexOutOfBoundException(String.format("Index %d is out of bound, list size %d", index, size));
        }
    }


}
