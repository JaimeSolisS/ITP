package itp.pk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyThread extends Thread {

    List<Data> arrayList;
    int amount;


    public MyThread(String name, List<Data> arrayList, int amount){
        setName(name);
        this.arrayList = arrayList;
        this.amount = amount;
    }


    public void run(){
            int i=0;

                while (arrayList.size() < amount) {
                    Data model = new Data(i, getName());
                    arrayList.add(model);
                    //System.out.println(arrayList);
                    i++;
                
            }

        System.out.println(arrayList.size());
    }

}
