package itp.pk;

import itp.pk.Controller.DataGenerator;
import itp.pk.Model.Data;
import java.util.*;

public class Main {

    public static void getTime(List<Data> listaNoProcesada, Collection<Data> collectionLista){

        long startTime = System.nanoTime();
        for (Data model: listaNoProcesada)
            collectionLista.add(model);
        long stopTime = System.nanoTime();
        System.out.println( collectionLista.getClass().getSimpleName() + " tardo " + (stopTime - startTime) + " nanosegundos");
    }

    public static void main(String[] args) {

        List<Data> datos1000NoProcesados = DataGenerator.generateData(1000);
        List<Data> datos10000NoProcesados = DataGenerator.generateData(10000);
        List<Data> datos100000NoProcesados = DataGenerator.generateData(100000);

        ArrayList<Data> arrayList = new ArrayList<>();
        LinkedList<Data> linkedList = new LinkedList<>();
        Vector<Data> vector = new Vector<>();
        Stack<Data> stack = new Stack<>();
      

        System.out.println("1000 elementos: ");
        getTime (datos1000NoProcesados, arrayList);
        getTime (datos1000NoProcesados, linkedList);
        getTime (datos1000NoProcesados, vector);
        getTime (datos1000NoProcesados, stack);


        System.out.println("10000 elementos: ");
        getTime (datos10000NoProcesados, arrayList);
        getTime (datos10000NoProcesados, linkedList);
        getTime (datos10000NoProcesados, vector);
        getTime (datos10000NoProcesados, stack);


        System.out.println("100000 elementos: ");
        getTime (datos100000NoProcesados, arrayList);
        getTime (datos100000NoProcesados, linkedList);
        getTime (datos100000NoProcesados, vector);
        getTime (datos100000NoProcesados, stack);



    }


}
