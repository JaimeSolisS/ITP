package itp.pk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FinalThread implements Runnable {
    Random rand = new Random();
    String name;

    public FinalThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        String status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Starting " + name + " at " + status);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Finished " + name + " at " + status);
    }
}
