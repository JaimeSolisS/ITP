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

    public static List<Data> pickNRandom(List<Data> listaNoProcesada, int amount) {
        List<Data> randomList = new ArrayList<Data>();
        for (int i = 0; i<amount; i++){
            int randomIndex = (int) (Math.random() * listaNoProcesada.size());
            randomList.add(listaNoProcesada.get(randomIndex));
        }
        return randomList;
    }



    public static double getTimeReplace(List<Data> randomList, Queue<Data> collectionQueue){
        Queue<Data> collectionQueueCopy = collectionQueue;
        List<Data> sortedRandomList = new ArrayList<Data>(randomList);
        Collections.sort(sortedRandomList, (d1, d2) -> {
            return (int) (d1.getId() - d2.getId());
        });

        double startTime = System.nanoTime();
        int indexFlag = 0;
        for (Data model  : randomList){
            long index = model.id -1;
            for (int i = indexFlag; i < collectionQueueCopy.size(); i ++){
                if (i == index){
                    String newStatus = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    Data replacement = new Data(model.id, model.name, model.key, newStatus);
                    collectionQueueCopy.remove();
                    collectionQueueCopy.add(replacement);
                } else collectionQueueCopy.add(collectionQueueCopy.remove());
                indexFlag++;
            }
        }
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

        List<Data> datos1000Random = pickNRandom(datos1000NoProcesados, 10);
        List<Data> datos10000Random = pickNRandom(datos10000NoProcesados, 10);
        List<Data> datos100000Random = pickNRandom(datos100000NoProcesados, 10);

        List<List<Data>> listOfElementsToFind = new ArrayList<>();
        listOfElementsToFind.add(datos1000Random);
        listOfElementsToFind.add(datos10000Random);
        listOfElementsToFind.add(datos100000Random);

        PriorityQueue<Data> priorityQueue1000 = new PriorityQueue<>();
        PriorityQueue<Data> priorityQueue10000 = new PriorityQueue<>();
        PriorityQueue<Data> priorityQueue100000 = new PriorityQueue<>();
        ArrayDeque<Data> arrayDeque1000 = new ArrayDeque<>();
        ArrayDeque<Data> arrayDeque10000 = new ArrayDeque<>();
        ArrayDeque<Data> arrayDeque100000 = new ArrayDeque<>();

        List<Queue<Data>> listOfTypeQueue = new ArrayList<>();
        listOfTypeQueue.add(priorityQueue1000);
        listOfTypeQueue.add(priorityQueue10000);
        listOfTypeQueue.add(priorityQueue100000);
        listOfTypeQueue.add(arrayDeque1000);
        listOfTypeQueue.add(arrayDeque10000);
        listOfTypeQueue.add(arrayDeque100000);


        double[][] tiempos = new double [2][3];
        int z = 0;
        for (int i = 0; i<2; i++){
            for (int j = 0; j<3; j++){
                tiempos[i][j] = getTimeInsert( listOfLists.get(j), listOfTypeQueue.get(z));
                z++;
            }
        }

        double[][] tiemposReplace = new double [2][3];
        System.out.println("-------Time to Replace in ms");
        System.out.println("TypeSet \t\t\t 1000 \t\t 10000 \t\t 100000");
        z = 0;
        for (int i = 0; i<2; i++){
            System.out.printf( "%-15s", listOfTypeQueue.get(z).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiemposReplace[i][j] = getTimeReplace( listOfElementsToFind.get(j), listOfTypeQueue.get(z));
                System.out.printf( "\t\t%.5f", tiemposReplace[i][j]);
                z++;
            }
            System.out.println();
        }
    }
}
