package org.example.iterator_pattern;

import java.util.List;
import java.util.ArrayList;

public class LinearContainer implements Container {

    List<Object> list = new ArrayList<Object>();

    @Override
    public Iterator getIterator() {
        return new LinearIterator();
    }

    @Override
    public void add(Object object) {
        list.add(object);
    }

    private class LinearIterator implements Iterator {
        int i = 0;

        public boolean hasNext() {
            return i < list.size();
        }

        public Object next() {
            try {
                return list.get(i);
            } finally {
                i++;
            }
        }
    }
}
