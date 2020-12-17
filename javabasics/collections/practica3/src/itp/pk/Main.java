package itp.pk;
import itp.pk.Controller.DataGenerator;
import itp.pk.Model.Data;
import java.util.*;
public class Main {
    public static void getTime(List<Data> listaNoProcesada, Collection<Data> collectionLista){

        double startTime = System.nanoTime();
        for (Data model: listaNoProcesada)
            collectionLista.add(model);
        double stopTime = System.nanoTime();
       // System.out.println( collectionLista.getClass().getSimpleName() + " tardo " + (stopTime - startTime)/1000000  + " ms");
    }

    public static List<Data> pickNRandom(List<Data> listaNoProcesada, int amount) {
        List<Data> randomList = new ArrayList<Data>();
        for (int i = 0; i<amount; i++){
            int randomIndex = (int) (Math.random() * listaNoProcesada.size());
            randomList.add(listaNoProcesada.get(randomIndex));
            //anadir verificacion para no agregar el mismo elemento
        }
        return randomList;
    }

    public static void getTimeSearch(List<Data> randomList, Collection<Data> collectionLista){

        double startTime = System.nanoTime();
        //int i = 0;
        for (Data model  : randomList){
          //  System.out.println("FOUND " + randomList.get(i));
            collectionLista.contains(model);

            //i++;
        }
        double stopTime = System.nanoTime();
        System.out.println( collectionLista.getClass().getSimpleName() + " tardo " + (stopTime - startTime)/1000000  + " ms");
    }

    public static void getTimeRemove(List<Data> randomList, Collection<Data> collectionLista){

        Collection<Data> collectionListCopy = collectionLista;
        double startTime = System.nanoTime();
        //int i = 0;
        for (Data model  : randomList){
            collectionListCopy.remove(model);
        }
        double stopTime = System.nanoTime();
      //  System.out.println( collectionListCopy.getClass().getSimpleName() + " tardo " + (stopTime - startTime)/1000000  + " ms");
    }
    public static void main(String[] args) {

            List<Data> datos1000NoProcesados = DataGenerator.generateData(1000);
            List<Data> datos10000NoProcesados = DataGenerator.generateData(10000);
            List<Data> datos100000NoProcesados = DataGenerator.generateData(100000);

            ArrayList<Data> arrayList1000 = new ArrayList<>();
            ArrayList<Data> arrayList10000 = new ArrayList<>();
            ArrayList<Data> arrayList100000 = new ArrayList<>();

            LinkedList<Data> linkedList1000 = new LinkedList<>();
            LinkedList<Data> linkedList10000 = new LinkedList<>();
            LinkedList<Data> linkedList100000 = new LinkedList<>();

            Vector<Data> vector1000 = new Vector<>();
            Vector<Data> vector10000 = new Vector<>();
            Vector<Data> vector100000 = new Vector<>();

            Stack<Data> stack1000 = new Stack<>();
            Stack<Data> stack10000 = new Stack<>();
            Stack<Data> stack100000 = new Stack<>();

            //STORE
            System.out.println("1000 elementos: ");
            getTime (datos1000NoProcesados, arrayList1000);
            getTime (datos1000NoProcesados, linkedList1000);
            getTime (datos1000NoProcesados, vector1000);
            getTime (datos1000NoProcesados, stack1000);

            System.out.println("10000 elementos: ");
            getTime (datos10000NoProcesados, arrayList10000);
            getTime (datos10000NoProcesados, linkedList10000);
            getTime (datos10000NoProcesados, vector10000);
            getTime (datos10000NoProcesados, stack10000);

            System.out.println("100000 elementos: ");
            getTime (datos100000NoProcesados, arrayList100000);
            getTime (datos100000NoProcesados, linkedList100000);
            getTime (datos100000NoProcesados, vector100000);
            getTime (datos100000NoProcesados, stack100000);

        List<Data> datos1000Random = pickNRandom(datos1000NoProcesados, 10);
        List<Data> datos10000Random = pickNRandom(datos10000NoProcesados, 10);
        List<Data> datos100000Random = pickNRandom(datos100000NoProcesados, 10);

        //SEARCH
        System.out.println("SEARCH");
        System.out.println("1000 elementos: ");
        getTimeSearch(datos1000Random, arrayList1000);
        getTimeSearch(datos1000Random, linkedList1000);
        getTimeSearch(datos1000Random, vector1000);
        getTimeSearch(datos1000Random, stack1000);

        System.out.println("10,000 elementos: ");
        getTimeSearch(datos10000Random, arrayList10000);
        getTimeSearch(datos10000Random, linkedList10000);
        getTimeSearch(datos10000Random, vector10000);
        getTimeSearch(datos10000Random, stack10000);

        System.out.println("100,000 elementos: ");
        getTimeSearch(datos100000Random, arrayList100000);
        getTimeSearch(datos100000Random, linkedList100000);
        getTimeSearch(datos100000Random, vector100000);
        getTimeSearch(datos100000Random, stack100000);

        //REMOVE
        /*
        System.out.println("REMOVE");
        System.out.println("1000 elementos: ");
        getTimeRemove(datos1000Random, arrayList1000);
        getTimeRemove(datos1000Random, linkedList1000);
        getTimeRemove(datos1000Random, vector1000);
        getTimeRemove(datos1000Random, stack1000);

        System.out.println("10,000 elementos: ");
        getTimeRemove(datos10000Random, arrayList10000);
        getTimeRemove(datos10000Random, linkedList10000);
        getTimeRemove(datos10000Random, vector10000);
        getTimeRemove(datos10000Random, stack10000);

        System.out.println("100,000 elementos: ");
        getTimeRemove(datos100000Random, arrayList100000);
        getTimeRemove(datos100000Random, linkedList100000);
        getTimeRemove(datos100000Random, vector100000);
        getTimeRemove(datos100000Random, stack100000);

         */

    }
}
