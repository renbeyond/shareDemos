package com.example.demo.bass.designPattern.cursor;

import java.util.Vector;

/**
 * 本章在应用中几乎不用
 *
 * 因为 JDK也提供了迭代接口进行java collection的遍历：

 Iterator it = list.iterator(); 　　
 while(it.hasNext()){ 　　

 //using it.next();

 }

 * Created by bass on 16/3/23.
 */
public class Cursor {
    public static void test()
    {
        final Aggregat agg = new ConcreteAggregat();
        final Iterator iterator = agg.createIterator();
        System.out.println(iterator.first());
        while (!iterator.isDone())
        {
            System.out.println(iterator.next());
        }
    }
}

interface Iterator
{
    Object first();

    Object next();

    Object currentItem();

    boolean isDone();
}

class ConcreteIterator implements Iterator
{
    private int currentIndex = 0;
    private Vector vector = null;

    public ConcreteIterator(final Vector vector)
    {
        this.vector = vector;
    }

    @Override
    public Object first()
    {
        currentIndex = 0;
        return vector.get(currentIndex);
    }

    @Override
    public Object next()
    {
        currentIndex++;
        return vector.get(currentIndex);
    }

    @Override
    public Object currentItem()
    {
        return vector.get(currentIndex);
    }

    @Override
    public boolean isDone()
    {
        if (currentIndex >= this.vector.size() - 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

interface Aggregat
{
     Iterator createIterator();
}

class ConcreteAggregat implements Aggregat
{
    private Vector vector = null;

    public Vector getVector()
    {
        return vector;
    }

    public void setVector(final Vector vector)
    {
        this.vector = vector;
    }

    public ConcreteAggregat()
    {
        vector = new Vector();
        vector.add("vector 1");
        vector.add("vector 2");
    }

    @Override
    public Iterator createIterator()
    {
        return new ConcreteIterator(vector);
    }
}


/**
结果：
        vector 1
        vector 2



        JDK也提供了迭代接口进行java collection的遍历：

        Iterator it = list.iterator(); 　　
        while(it.hasNext()){ 　　

        //using it.next();

        }
 *
 */
