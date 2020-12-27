package itp.pk;

public class Main {

    public static void main(String[] args) {

        Thread t5 = new Thread(new FinalThread("thread5"));
        Thread t4 = new Thread(new MyThread("thread4", t5));
        Thread t3 = new Thread(new MyThread("thread3", t4));
        Thread t2 = new Thread(new MyThread("thread2", t3));
        Thread t1 = new Thread(new MyThread("thread1", t2));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
