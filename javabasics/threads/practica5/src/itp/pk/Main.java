package itp.pk;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Data> numList = new ArrayList<>();

        MyThread t1 = new MyThread("T1", numList);
        MyThread t2 = new MyThread("T2", numList);
        MyThread t3 = new MyThread("T3", numList);
        MyThread t4 = new MyThread("T4", numList);
        MyThread t5 = new MyThread("T5", numList);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

    }
}
