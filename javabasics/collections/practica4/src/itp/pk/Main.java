package itp.pk;

import itp.pk.Controller.DataGenerator;
import itp.pk.Model.Data;
import java.util.*;

public class Main {

    public static double getTime(List<Data> listaNoProcesada, Collection<Data> collectionSet){

        double startTime = System.nanoTime();
        for (Data model: listaNoProcesada)
            collectionSet.add(model);
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

        HashSet<Data> hashSet=new HashSet();
        LinkedHashSet<Data> LinkedSet=new LinkedHashSet();
        TreeSet<Data> TreeSet=new TreeSet();

        List<Set<Data>> listOfTypeSets = new ArrayList<>();
        listOfTypeSets.add(hashSet);
        listOfTypeSets.add(LinkedSet);
        listOfTypeSets.add(TreeSet);

        double[][] tiempos = new double [4][3];
        System.out.println("Time to Store in ms");
        System.out.println("TypeList \t\t\t 1000 \t\t 10000 \t\t 100000");
        for (int i = 0; i<3; i++){
            System.out.printf( "%-15s", listOfTypeSets.get(i).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiempos[i][j] = getTime( listOfLists.get(j), listOfTypeSets.get(i));
                System.out.printf( "\t\t%.5f", tiempos[i][j]);
            }
            System.out.println();
        }

    }


}