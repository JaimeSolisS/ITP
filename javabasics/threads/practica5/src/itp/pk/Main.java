package itp.pk;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        CopyOnWriteArrayList<Data> arrayList = new CopyOnWriteArrayList<Data>();
        int amount = 10;

        MyThread t1 = new MyThread("T1", arrayList, amount);
        MyThread t2 = new MyThread("T2", arrayList,amount);
        MyThread t3 = new MyThread("T3", arrayList,amount);
        MyThread t4 = new MyThread("T4", arrayList,amount );
        MyThread t5 = new MyThread("T5", arrayList, amount);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

    }
}
