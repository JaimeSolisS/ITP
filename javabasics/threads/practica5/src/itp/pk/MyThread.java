package itp.pk;

import java.util.List;
import java.util.Random;


/*https://blog.codingblocks.com/2019/keeping-arraylists-safe-across-threads-in-java/*/

public class MyThread extends Thread {

    List<Data> list;
    int amount;
    Random rand = new Random();

    public MyThread(String name, List<Data> list, int amount){
        setName(name);
       this.list = list;
        this.amount = amount;
    }


    public void run(){
        System.out.println("Hey I'm " + getName() + " and I'm working with " + list.getClass().getSimpleName());
        while (list.size() < amount) {
             Data model = new Data( getName());
             list.add(model);

             try {
                 MyThread.sleep(rand.nextInt(25));
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
        }
    }

}
