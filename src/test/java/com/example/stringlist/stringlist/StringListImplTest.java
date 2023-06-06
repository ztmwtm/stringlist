package com.example.stringlist.stringlist;

import com.example.stringlist.exception.StringListElementNotFound;
import com.example.stringlist.exception.StringListIndexOutOfBoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StringListImplTest {

    private final StringList list = new StringListImpl(5);

    @BeforeEach
    void setUp() {
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
    }

    @Test
    void addItemThenContainsThenSizeThenToArrayEqualsTrue() {
        String[] expected = new String[]{"A", "B", "C", "D", "E"};
        list.add("E");
        assertThat(list.contains("A")).isTrue();
        assertThat(list.contains("B")).isTrue();
        assertThat(list.contains("C")).isTrue();
        assertThat(list.contains("D")).isTrue();
        assertThat(list.contains("E")).isTrue();
        assertThat(list.size()).isEqualTo(5);
        assertThat(list.toArray()).isNotEmpty().isEqualTo(expected);
    }

    @Test
    void addItemsThenGrowCheckThenSizeThenToArrayEqualsTrue() {
        String[] expected = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        list.add("E");
        list.add("F");
        list.add("G");
        list.add("H");
        assertThat(list.contains("A")).isTrue();
        assertThat(list.contains("B")).isTrue();
        assertThat(list.contains("C")).isTrue();
        assertThat(list.contains("D")).isTrue();
        assertThat(list.contains("E")).isTrue();
        assertThat(list.contains("F")).isTrue();
        assertThat(list.contains("G")).isTrue();
        assertThat(list.contains("H")).isTrue();
        assertThat(list.size()).isEqualTo(8);
        assertThat(list.toArray()).isNotEmpty().isEqualTo(expected);
    }

    @Test
    void addByIndexThenGrowCheckThenSizeThenToArrayEqualsTrue() {
        String[] expected = new String[]{"H", "G", "F", "E", "A", "B", "C", "D",};
        assertThat(list.add(0, "E")).isEqualTo("E");
        assertThat(list.add(0, "F")).isEqualTo("F");
        assertThat(list.add(0, "G")).isEqualTo("G");
        assertThat(list.add(0, "H")).isEqualTo("H");
        assertThat(list.contains("A")).isTrue();
        assertThat(list.contains("B")).isTrue();
        assertThat(list.contains("C")).isTrue();
        assertThat(list.contains("D")).isTrue();
        assertThat(list.contains("E")).isTrue();
        assertThat(list.contains("F")).isTrue();
        assertThat(list.contains("G")).isTrue();
        assertThat(list.contains("H")).isTrue();
        assertThat(list.size()).isEqualTo(8);
        assertThat(list.toArray()).isNotEmpty().isEqualTo(expected);
    }

    @Test
    void setThenSizeThenToArrayEqualsTrue() {
        String[] expected = new String[]{"E", "B", "C", "D", "Z"};
        list.set(0, "E");
        list.add("X");
        list.set(4, "Z");
        assertThat(list.contains("B")).isTrue();
        assertThat(list.contains("C")).isTrue();
        assertThat(list.contains("D")).isTrue();
        assertThat(list.contains("E")).isTrue();
        assertThat(list.contains("Z")).isTrue();
        assertThat(list.size()).isEqualTo(5);
        assertThat(list.toArray()).isNotEmpty().isEqualTo(expected);
    }

    @Test
    void removeThenSizeThenToArrayEqualsTrue() {
        String[] expected = new String[]{"B", "D"};
        list.add("X");
        list.remove("X");
        list.remove("A");
        list.remove("C");
        assertThat(list.contains("B")).isTrue();
        assertThat(list.contains("D")).isTrue();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.toArray()).isNotEmpty().isEqualTo(expected);
        assertThatThrownBy(() -> list.remove("Z")).isInstanceOf(StringListElementNotFound.class);
        assertThatThrownBy(() -> list.remove("A")).isInstanceOf(StringListElementNotFound.class);

    }

    @Test
    void removeByIndexThenSizeThenToArrayEqualsTrue() {
        String[] expected = new String[]{"C", "X"};
        list.add("X");
        list.remove(0);
        list.remove(0);
        list.remove(1);
        assertThat(list.contains("X")).isTrue();
        assertThat(list.contains("C")).isTrue();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.toArray()).isNotEmpty().isEqualTo(expected);
        assertThatThrownBy(() -> list.remove(-1)).isInstanceOf(StringListIndexOutOfBoundException.class);
        assertThatThrownBy(() -> list.remove(2)).isInstanceOf(StringListIndexOutOfBoundException.class);
    }

    @Test
    void contains() {
        assertThat(list.contains("A")).isTrue();
        assertThat(list.contains("B")).isTrue();
        assertThat(list.contains("C")).isTrue();
        assertThat(list.contains("D")).isTrue();
        assertThat(list.contains("А")).isFalse();
        assertThat(list.contains("В")).isFalse();
        assertThat(list.contains("С")).isFalse();
        assertThat(list.contains("Д")).isFalse();

    }

    @Test
    void indexOf() {
        assertThat(list.indexOf("A")).isZero();
        assertThat(list.indexOf("B")).isEqualTo(1);
        assertThat(list.indexOf("C")).isEqualTo(2);
        assertThat(list.indexOf("D")).isEqualTo(3);
        assertThat(list.indexOf("Z")).isEqualTo(-1);
        list.remove("A");
        assertThat(list.indexOf("A")).isEqualTo(-1);
        assertThat(list.indexOf("B")).isZero();
        assertThat(list.indexOf("C")).isEqualTo(1);
        assertThat(list.indexOf("D")).isEqualTo(2);

    }

    @Test
    void lastIndexOfThenAddThenRemove() {
        assertThat(list.lastIndexOf("A")).isZero();
        assertThat(list.lastIndexOf("B")).isEqualTo(1);
        assertThat(list.lastIndexOf("C")).isEqualTo(2);
        assertThat(list.lastIndexOf("D")).isEqualTo(3);
        list.add("A");
        assertThat(list.lastIndexOf("A")).isEqualTo(4);
        list.add("A");
        list.add("A");
        list.add("A");
        assertThat(list.lastIndexOf("A")).isEqualTo(7);
        list.remove(7);
        assertThat(list.lastIndexOf("A")).isEqualTo(6);
        list.add("B");
        assertThat(list.lastIndexOf("B")).isEqualTo(7);
        assertThat(list.lastIndexOf("X")).isEqualTo(-1);
        list.remove("C");
        assertThat(list.lastIndexOf("C")).isEqualTo(-1);
    }

    @Test
    void getThenRemove() {
        assertThat(list.get(0)).isEqualTo("A");
        assertThat(list.get(1)).isEqualTo("B");
        assertThat(list.get(2)).isEqualTo("C");
        assertThat(list.get(3)).isEqualTo("D");
        list.remove(0);
        assertThat(list.get(0)).isEqualTo("B");
        list.remove(0);
        assertThat(list.get(0)).isEqualTo("C");
        list.remove(0);
        assertThat(list.get(0)).isEqualTo("D");
        list.remove(0);
        assertThatThrownBy(() -> list.get(0)).isInstanceOf(StringListIndexOutOfBoundException.class);
        assertThatThrownBy(() -> list.get(-1)).isInstanceOf(StringListIndexOutOfBoundException.class);
    }

    @Test
    void testEquals() {
        StringList expected = new StringListImpl(99);
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("D");
        assertThat(list.equals(expected)).isTrue();
        assertThatThrownBy(() -> list.equals(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void size() {
        assertThat(list.size()).isEqualTo(4);
        list.remove(0);
        assertThat(list.size()).isEqualTo(3);
        list.add("A");
        assertThat(list.size()).isEqualTo(4);
        list.add("A");
        assertThat(list.size()).isEqualTo(5);
    }

    @Test
    void isEmpty() {
        StringList stringList = new StringListImpl(1);
        assertThat(list.isEmpty()).isFalse();
        assertThat(stringList.isEmpty()).isTrue();
        stringList.add("A");
        assertThat(stringList.isEmpty()).isFalse();
    }

    @Test
    void clear() {
        list.clear();
        assertThat(list.isEmpty()).isTrue();
        assertThat(list.size()).isZero();
        list.add("A");
        assertThat(list.isEmpty()).isFalse();
        assertThat(list.size()).isNotZero();
        list.clear();
        assertThat(list.isEmpty()).isTrue();
        assertThat(list.size()).isZero();
    }

    @Test
    void toArray() {
        String[] expected = new String[]{"A", "B", "C", "D"};
        assertThat(list.toArray()).isEqualTo(expected);
    }
}