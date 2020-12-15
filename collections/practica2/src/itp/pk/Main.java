package itp.pk;

import itp.pk.Controller.DataGenerator;
import itp.pk.Model.Data;
import java.util.*;

public class Main {

    public static void getTimeArrayList(List<Data> lista){
        ArrayList<Data> arrayList = new ArrayList<>();
        long startTime = System.nanoTime();
        for (Data model: lista)
            arrayList.add(model);

        long stopTime = System.nanoTime();
        System.out.println("ArrayList tardo " + (stopTime - startTime) + " nanosegundos");
    }

    public static void getTimeLinkedList(List<Data> lista){
        LinkedList<Data> arrayList = new LinkedList<>();
        long startTime = System.nanoTime();
        for (Data model: lista)
            arrayList.add(model);

        long stopTime = System.nanoTime();
        System.out.println("LinkedList tardo " + (stopTime - startTime) + " nanosegundos");
    }

    public static void getTimeVector(List<Data> lista){
        Vector<Data> arrayList = new Vector<>();

        long startTime = System.nanoTime();
        for (Data model: lista)
            arrayList.add(model);
        long stopTime = System.nanoTime();
        System.out.println("Vector tardo " + (stopTime - startTime) + " nanosegundos");
    }

    public static void getTimeStack(List<Data> lista){
        Stack<Data> arrayList = new Stack<>();

        long startTime = System.nanoTime();
        for (Data model: lista)
            arrayList.add(model);
        long stopTime = System.nanoTime();
        System.out.println("Stack tardo " + (stopTime - startTime) + " nanosegundos");
    }

    public static void main(String[] args) {

        List<Data> datos1000NoProcesados = DataGenerator.generateData(1000);
        List<Data> datos10000NoProcesados = DataGenerator.generateData(10000);
        List<Data> datos100000NoProcesados = DataGenerator.generateData(100000);
      

        System.out.println("1000 elementos: ");
        getTimeArrayList  (datos1000NoProcesados);
        getTimeLinkedList  (datos1000NoProcesados);
        getTimeVector  (datos1000NoProcesados);
        getTimeStack  (datos1000NoProcesados);

        System.out.println("10000 elementos: ");
        getTimeArrayList  (datos10000NoProcesados);
        getTimeLinkedList (datos10000NoProcesados);
        getTimeVector     (datos10000NoProcesados);
        getTimeStack      (datos10000NoProcesados);

        System.out.println("100000 elementos: ");
        getTimeArrayList  (datos100000NoProcesados);
        getTimeLinkedList (datos100000NoProcesados);
        getTimeVector     (datos100000NoProcesados);
        getTimeStack      (datos100000NoProcesados);


    }


}
