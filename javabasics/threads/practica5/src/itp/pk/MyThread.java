package itp.pk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MyThread extends Thread {

    Random rand = new Random();
    ArrayList<Data> numList;


    public MyThread(String name, ArrayList<Data> numList){
        setName(name);
        this.numList = numList;
    }

    public void run(){
        int i =0;
            while (numList.size() < 10) {
                Data model = new Data(i, getName());
                numList.add(model);
                System.out.println(numList);
                i++;
            }
    }

}
