package itp.pk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MyThread extends Thread {

    ArrayList<Data> arrayList;


    public MyThread(String name, ArrayList<Data> arrayList){
        setName(name);
        this.arrayList = arrayList;
    }

    public void run(){
        int i =0;
        synchronized (arrayList){
            while (arrayList.size() < 10) {
                Data model = new Data(i, getName());
                arrayList.add(model);
                System.out.println(arrayList);
                i++;
            }
        }

    }

}
