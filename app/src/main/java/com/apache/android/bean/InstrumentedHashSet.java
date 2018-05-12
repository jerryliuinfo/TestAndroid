package com.apache.android.bean;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;

/**
 * Created by 01370340 on 2018/5/1.
 */

public class InstrumentedHashSet<E> extends HashSet<E> {

    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

   /* public boolean addAll(Collection<? extends E> c){
        addCount += c.size();
        return super.addAll(c);
    }*/

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Properties properties = new Properties();

        properties.getProperty("");
        properties.get("key");
        return super.addAll(c);
    }

    public int getAddCount(){
        return addCount;
    }

    public static void main(String[] args) {
        InstrumentedHashSet<String> set = new InstrumentedHashSet<>();
        set.addAll(Arrays.asList("one", "two", "three"));
        System.out.println("size = "+ set.getAddCount());
    }
}
