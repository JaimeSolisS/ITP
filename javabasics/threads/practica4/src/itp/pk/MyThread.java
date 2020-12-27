package itp.pk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MyThread implements Runnable {

    Random rand = new Random();
    Thread threadToWaitFor;
    String name;

    public MyThread(String name, Thread threadToWaitFor){
        this.name = name;
        this.threadToWaitFor = threadToWaitFor;
    }

    @Override
    public void run(){

        String status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Starting " + name + " at " + status);

        try {
            threadToWaitFor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(500 + rand.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Finished " + name + " at " + status);
    }


}