package itp.pk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MyThread extends Thread {

    Random rand = new Random();

    public MyThread(String name){
        setName(name);
    }

    public void run(){
        String status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Starting " + getName() + " at " + status);

        try {
            MyThread.sleep(500 + rand.nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Finished " + getName() + " at " + status);
    }


}
