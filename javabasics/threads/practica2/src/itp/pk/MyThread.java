package itp.pk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MyThread implements Runnable {

    private String name;
    Random rand = new Random();

    public MyThread(String name){
        this.name = name;
    }
    
    public void run(){
        String status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Starting " + name + " at " + status);

        try {
            Thread.sleep(500 + rand.nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Finished " + name + " at " + status);
    }

}
