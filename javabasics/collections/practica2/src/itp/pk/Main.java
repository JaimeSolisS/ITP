package itp.pk;

import itp.pk.Controller.DataGenerator;
import itp.pk.Model.Data;
import java.util.*;

public class Main {

    public static double getTime(List<Data> listaNoProcesada, Collection<Data> collectionLista){

        double startTime = System.nanoTime();
        for (Data model: listaNoProcesada)
            collectionLista.add(model);
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

        ArrayList<Data> arrayList = new ArrayList<>();
        LinkedList<Data> linkedList = new LinkedList<>();
        Vector<Data> vector = new Vector<>();
        Stack<Data> stack = new Stack<>();

        List<List<Data>> listOfTypeLists = new ArrayList<>();
        listOfTypeLists.add(arrayList);
        listOfTypeLists.add(linkedList);
        listOfTypeLists.add(vector);
        listOfTypeLists.add(stack);

        double[][] tiempos = new double [4][3];
        System.out.println("Time to Store in ms");
        System.out.println("TypeList \t\t 1000 \t\t 10000 \t\t 100000");
        for (int i = 0; i<4; i++){
            System.out.printf( "%-10s", listOfTypeLists.get(i).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiempos[i][j] = getTime( listOfLists.get(j), listOfTypeLists.get(i));
                System.out.printf( "\t\t%.5f", tiempos[i][j]);
            }
            System.out.println();
        }

    }


}
