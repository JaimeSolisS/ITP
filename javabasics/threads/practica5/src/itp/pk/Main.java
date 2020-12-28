package itp.pk;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {

       List<Data> arrayList = Collections.synchronizedList(new ArrayList<>());
       CopyOnWriteArrayList<Data> arrayListCopy = new CopyOnWriteArrayList<Data>();
        ArrayList<Data> arrayListPlain = new ArrayList<Data>();
        List<Data> linkedList = Collections.synchronizedList(new LinkedList<>());
        List<Data> vector = Collections.synchronizedList(new Vector<>());
        List<Data> stack = Collections.synchronizedList(new Stack<>());

        int amount = 1000;

        MyThread t1 = new MyThread("Thread1", arrayList,amount);
        MyThread t2 = new MyThread("Thread2", arrayList,amount);
        MyThread t3 = new MyThread("Thread3", arrayList,amount);
        MyThread t4 = new MyThread("Thread4", arrayList,amount );
        MyThread t5 = new MyThread("Thread5", arrayList,amount);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        for (Thread t : new Thread[] { t1, t2, t3, t4, t5 })
            t.join();

        System.out.println(arrayList);
        System.out.println("Elements: " + arrayList.size());

    }
}
