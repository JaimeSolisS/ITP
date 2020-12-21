package itp.pk;

import itp.pk.Controller.DataGenerator;
import itp.pk.Model.Data;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static double getTime(List<Data> listaNoProcesada, Collection<Data> collectionSet){

        double startTime = System.nanoTime();
        for (Data model: listaNoProcesada)
            collectionSet.add(model);
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

    public static double getTimeSearch(List<Data> randomList, Collection<Data> collectionSet){

        double startTime = System.nanoTime();
        int i = 0;
        for (Data model  : randomList)
            collectionSet.contains(model) ;

        double stopTime = System.nanoTime();
        return (stopTime - startTime)/1000000;
    }

    public static double getTimeRemove(List<Data> randomList, Collection<Data> collectionSet){
        Collection<Data> collectionSetCopy = collectionSet;
        double startTime = System.nanoTime();
        for (Data model  : randomList)
            collectionSetCopy.remove(model) ;

        double stopTime = System.nanoTime();
        return (stopTime - startTime)/1000000;
    }

    public static double getTimeReplace(List<Data> randomList, Collection<Data> collectionSet){
        Collection<Data> collectionSetCopy = collectionSet;
        double startTime = System.nanoTime();
        int i = 0;
        for (Data model  : randomList){
            String newStatus = new SimpleDateFormat("HH:mm:ss").format(new Date());
            Data replacement = new Data(model.id, model.name, model.key, newStatus);
            collectionSetCopy.remove(model);
            collectionSetCopy.add(replacement);
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

        HashSet<Data> HashSet1000 = new HashSet<>();
        HashSet<Data> HashSet10000 = new HashSet<>();
        HashSet<Data> HashSet100000 = new HashSet<>();
        LinkedHashSet<Data> LinkedHashSet1000 = new LinkedHashSet<>();
        LinkedHashSet<Data> LinkedHashSet10000 = new LinkedHashSet<>();
        LinkedHashSet<Data> LinkedHashSet100000 = new LinkedHashSet<>();
        TreeSet<Data> TreeSet1000 = new TreeSet<>();
        TreeSet<Data> TreeSet10000 = new TreeSet<>();
        TreeSet<Data> TreeSet100000 = new TreeSet<>();

        List<Set<Data>> listOfTypeSets = new ArrayList<>();
        listOfTypeSets.add(HashSet1000);
        listOfTypeSets.add(HashSet10000);
        listOfTypeSets.add(HashSet100000);
        listOfTypeSets.add(LinkedHashSet1000);
        listOfTypeSets.add(LinkedHashSet10000);
        listOfTypeSets.add(LinkedHashSet100000);
        listOfTypeSets.add(TreeSet1000);
        listOfTypeSets.add(TreeSet10000);
        listOfTypeSets.add(TreeSet100000);

        double[][] tiempos = new double [4][3];
        System.out.println("Time to Store in ms");
        System.out.println("TypeSet \t\t\t 1000 \t\t 10000 \t\t 100000");
        int z = 0;
        for (int i = 0; i<3; i++){
            System.out.printf( "%-15s", listOfTypeSets.get(i).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiempos[i][j] = getTime( listOfLists.get(j), listOfTypeSets.get(z));
                System.out.printf( "\t\t%.5f", tiempos[i][j]);
            }
            System.out.println();
        }

        double[][] tiemposSearch = new double [4][3];
        System.out.println("-------Time to Search in ms");
        System.out.println("TypeSet \t\t\t 1000 \t\t 10000 \t\t 100000");
        z = 0;
        for (int i = 0; i<3; i++){
            System.out.printf( "%-15s", listOfTypeSets.get(z).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiemposSearch[i][j] = getTimeSearch( listOfElementsToFind.get(j), listOfTypeSets.get(z));
                System.out.printf( "\t\t%.5f", tiemposSearch[i][j]);
                z++;
            }
            System.out.println();
        }

        double[][] tiemposRemove = new double [4][3];
        System.out.println("-------Time to Remove in ms");
        System.out.println("TypeSet \t\t\t 1000 \t\t 10000 \t\t 100000");
        z = 0;
        for (int i = 0; i<3; i++){
            System.out.printf( "%-15s", listOfTypeSets.get(z).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiemposRemove[i][j] = getTimeRemove( listOfElementsToFind.get(j), listOfTypeSets.get(z));
                System.out.printf( "\t\t%.5f", tiemposRemove[i][j]);
                z++;
            }
            System.out.println();
        }

        double[][] tiemposReplace = new double [4][3];
        System.out.println("-------Time to Replace in ms");
        System.out.println("TypeSet \t\t\t 1000 \t\t 10000 \t\t 100000");
        z = 0;
        for (int i = 0; i<3; i++){
            System.out.printf( "%-15s", listOfTypeSets.get(z).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiemposReplace[i][j] = getTimeReplace( listOfElementsToFind.get(j), listOfTypeSets.get(z));
                System.out.printf( "\t\t%.5f", tiemposReplace[i][j]);
                z++;
            }
            System.out.println();
        }

    }


}