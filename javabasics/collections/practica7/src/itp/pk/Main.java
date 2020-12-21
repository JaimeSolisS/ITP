package itp.pk;

import itp.pk.Controller.DataGenerator;
import itp.pk.Model.Data;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static double getTimeInsert(List<Data> listaNoProcesada, Collection<Data> collectionQueue){
        Collection<Data> collectionQueueCopy = collectionQueue;
        double startTime = System.nanoTime();
        for (Data model: listaNoProcesada)
            collectionQueueCopy.add(model);
        double stopTime = System.nanoTime();
        return (stopTime - startTime)/1000000;
    }


    public static void main(String[] args) {
        List<Data> datos1000NoProcesados = DataGenerator.generateData(1000);
        List<Data> datos10000NoProcesados = DataGenerator.generateData(10000);
        List<Data> datos100000NoProcesados = DataGenerator.generateData(100000);

        List<List<Data>> listOfLists = new ArrayList<>();
        listOfLists.add(datos1000NoProcesados);
        listOfLists.add(datos10000NoProcesados);
        listOfLists.add(datos100000NoProcesados);

        PriorityQueue<Data> priorityQueue1000 = new PriorityQueue<>();
        PriorityQueue<Data> priorityQueue10000 = new PriorityQueue<>();
        PriorityQueue<Data> priorityQueue100000 = new PriorityQueue<>();
        ArrayDeque<Data> arrayDeque1000 = new ArrayDeque<>();
        ArrayDeque<Data> arrayDeque10000 = new ArrayDeque<>();
        ArrayDeque<Data> arrayDeque100000 = new ArrayDeque<>();
        LinkedList<Data> LinkedList1000 = new LinkedList<>();
        LinkedList<Data> LinkedList10000 = new LinkedList<>();
        LinkedList<Data> LinkedList100000 = new LinkedList<>();

        List<Queue<Data>> listOfTypeQueue = new ArrayList<>();
        listOfTypeQueue.add(priorityQueue1000);
        listOfTypeQueue.add(priorityQueue10000);
        listOfTypeQueue.add(priorityQueue100000);
        listOfTypeQueue.add(arrayDeque1000);
        listOfTypeQueue.add(arrayDeque10000);
        listOfTypeQueue.add(arrayDeque100000);
        listOfTypeQueue.add(LinkedList1000);
        listOfTypeQueue.add(LinkedList10000);
        listOfTypeQueue.add(LinkedList100000);

        double[][] tiempos = new double [3][3];
        System.out.println("Time to Store in ms");
        System.out.println("TypeQueue \t\t\t 1000 \t\t 10000 \t\t 100000");
        int z = 0;
        for (int i = 0; i<3; i++){
            System.out.printf( "%-15s", listOfTypeQueue.get(z).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiempos[i][j] = getTimeInsert( listOfLists.get(j), listOfTypeQueue.get(z));
                System.out.printf( "\t\t%.5f", tiempos[i][j]);
                z++;
            }
            System.out.println();
        }




    }


}