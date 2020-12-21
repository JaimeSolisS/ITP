package itp.pk;

import itp.pk.Controller.DataGenerator;
import itp.pk.Model.Data;

import java.util.*;

public class Main {

    public static double getTimeInsert(List<Data> listaNoProcesada, Map<Long, Data> collectionMap){
        Map<Long, Data> collectionMapCopy = collectionMap;
        double startTime = System.nanoTime();
        for (Data model: listaNoProcesada)
            collectionMapCopy.put(model.id, model);
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

    public static double getTimeSearch(List<Data> randomList, Map<Long, Data> collectionMap){

        Map<Long, Data> collectionMapCopy = collectionMap;

        double startTime = System.nanoTime();
        for (Data model  : randomList)
            collectionMapCopy.get(model.id);
        double stopTime = System.nanoTime();
        return (stopTime - startTime)/1000000;
    }

    public static double getTimeRemove(List<Data> randomList, Map<Long, Data> collectionMap){
        Map<Long, Data> collectionMapCopy = collectionMap;

        double startTime = System.nanoTime();
        for (Data model  : randomList)
            collectionMapCopy.remove(model.id);

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

        HashMap<Long, Data> hashMap1000 = new HashMap<>();
        HashMap<Long, Data> hashMap10000  = new HashMap<>();
        HashMap<Long, Data> hashMap100000  = new HashMap<>();
        LinkedHashMap<Long, Data> linkedHashMap1000 = new LinkedHashMap<>();
        LinkedHashMap<Long, Data> linkedHashMap10000  = new LinkedHashMap<>();
        LinkedHashMap<Long, Data> linkedHashMap100000  = new LinkedHashMap<>();
        TreeMap<Long, Data> treeMap1000 = new TreeMap<>();
        TreeMap<Long, Data> treeMap10000 = new TreeMap<>();
        TreeMap<Long, Data> treeMap100000 = new TreeMap<>();
        Hashtable<Long, Data> hashtable1000 = new Hashtable<>();
        Hashtable<Long, Data> hashtable10000 = new Hashtable<>();
        Hashtable<Long, Data> hashtable100000 = new Hashtable<>();


        List<Map> listOfTypeMap = new ArrayList<>();
        listOfTypeMap.add(hashMap1000);
        listOfTypeMap.add(hashMap10000);
        listOfTypeMap.add(hashMap100000);
        listOfTypeMap.add(linkedHashMap1000);
        listOfTypeMap.add(linkedHashMap10000);
        listOfTypeMap.add(linkedHashMap100000);
        listOfTypeMap.add(treeMap1000);
        listOfTypeMap.add(treeMap10000);
        listOfTypeMap.add(treeMap100000);
        listOfTypeMap.add(hashtable1000);
        listOfTypeMap.add(hashtable10000);
        listOfTypeMap.add(hashtable100000);

        double[][] tiempos = new double [4][3];
        int z = 0;
        for (int i = 0; i<4; i++){
            for (int j = 0; j<3; j++){
                tiempos[i][j] = getTimeInsert( listOfLists.get(j), listOfTypeMap.get(z));
                z++;
            }
        }

        double[][] tiemposSearch = new double [4][3];
        System.out.println("-------Time to Search in ms");
        System.out.println("Type \t\t\t 1000 \t\t 10000 \t\t 100000");
        z = 0;
        for (int i = 0; i<4; i++){
            System.out.printf( "%-15s", listOfTypeMap.get(z).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiemposSearch[i][j] = getTimeSearch( listOfElementsToFind.get(j), listOfTypeMap.get(z));
                System.out.printf( "\t\t%.5f", tiemposSearch[i][j]);
                z++;
            }
            System.out.println();
        }

        double[][] tiemposRemove = new double [4][3];
        System.out.println("-------Time to Remove in ms");
        System.out.println("Type \t\t\t 1000 \t\t 10000 \t\t 100000");
        z = 0;
        for (int i = 0; i<4; i++){
            System.out.printf( "%-15s", listOfTypeMap.get(z).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiemposRemove[i][j] = getTimeRemove( listOfElementsToFind.get(j), listOfTypeMap.get(z));
                System.out.printf( "\t\t%.5f", tiemposRemove[i][j]);
                z++;
            }
            System.out.println();
        }
    }
}