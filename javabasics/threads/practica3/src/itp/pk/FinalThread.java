package itp.pk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FinalThread extends Thread {
    Random rand = new Random();

    public FinalThread(String name) {
        setName(name);
    }

    @Override
    public void run() {

        String status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Starting " + getName() + " at " + status);

        try {
            itp.pk.MyThread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        System.out.println("Finished " + getName() + " at " + status);
    }
}
