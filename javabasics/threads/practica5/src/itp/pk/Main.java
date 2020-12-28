package itp.pk;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Data> arrayList = new ArrayList<>();

        MyThread t1 = new MyThread("T1", arrayList);
        MyThread t2 = new MyThread("T2", arrayList);
        MyThread t3 = new MyThread("T3", arrayList);
        MyThread t4 = new MyThread("T4", arrayList);
        MyThread t5 = new MyThread("T5", arrayList);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

    }
}
