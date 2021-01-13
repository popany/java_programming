// https://www.runoob.com/design-pattern/iterator-pattern.html

package org.example.iterator_pattern;

public class IteratorPatternDemo {
    public static void main(String[] args) {
        Container container = new LinearContainer();
        container.add(new String("a"));
        container.add(new String("b"));
        container.add(new String("c"));

        Iterator iterator = container.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
