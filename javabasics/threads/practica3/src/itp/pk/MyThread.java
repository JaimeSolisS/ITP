package itp.pk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MyThread extends Thread {

    Random rand = new Random();
    Thread threadToWaitFor;

    public MyThread(String name, Thread threadToWaitFor){
        setName(name);
        this.threadToWaitFor = threadToWaitFor;
    }

    @Override
    public void run(){

        String status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Starting " + getName() + " at " + status);

        try {
            threadToWaitFor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            MyThread.sleep(500 + rand.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Finished " + getName() + " at " + status);
    }


}