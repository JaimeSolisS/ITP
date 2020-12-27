package itp.pk;

public class Main {

        public static void main(String[] args) {

            FinalThread t5 = new FinalThread("thread5");
            MyThread t4 = new MyThread("thread4", t5);
            MyThread t3 = new MyThread("thread3", t4);
            MyThread t2 = new MyThread("thread2", t3);
            MyThread t1 = new MyThread("thread1", t2);

            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
        }
    }
