package itp.pk;
import itp.pk.Controller.DataGenerator;
import itp.pk.Model.Data;
import java.util.*;
public class Main {

    public static double store(List<Data> listaNoProcesada, Collection<Data> collectionLista){

        double startTime = System.nanoTime();
        for (Data model: listaNoProcesada)
            collectionLista.add(model);
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

    public static double getTimeSearch(List<Data> randomList, Collection<Data> collectionLista){

        double startTime = System.nanoTime();
        int i = 0;
        for (Data model  : randomList)
           collectionLista.contains(model) ;

        double stopTime = System.nanoTime();
        return (stopTime - startTime)/1000000;
    }

    public static double getTimeRemove(List<Data> randomList, Collection<Data> collectionLista){
        Collection<Data> collectionListCopy = collectionLista;
        double startTime = System.nanoTime();
        int i = 0;
        for (Data model  : randomList)
            collectionListCopy.remove(model) ;

        double stopTime = System.nanoTime();
        return (stopTime - startTime)/1000000;
    }

    public static double getTimeReplace(List<Data> randomList, Collection<Data> collectionLista, Collection<Data> collectionListCopy){
        double startTime = System.nanoTime();
        int index = 0;
        for (Data model  : randomList){
            if (collectionLista.contains(model)== true){
                collectionListCopy.remove(model);
                List<Data> newElement = DataGenerator.generateData(1);
                collectionListCopy.add(index, newElement.get(0));
            }
            index++;
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

        List<List<Data>> listOfTypeLists = new ArrayList<>();
        listOfTypeLists.add(arrayList1000);
        listOfTypeLists.add(arrayList10000);
        listOfTypeLists.add(arrayList100000);
        listOfTypeLists.add(linkedList1000);
        listOfTypeLists.add(linkedList10000);
        listOfTypeLists.add(linkedList100000);
        listOfTypeLists.add(vector1000);
        listOfTypeLists.add(vector10000);
        listOfTypeLists.add(vector100000);
        listOfTypeLists.add(stack1000);
        listOfTypeLists.add(stack10000);
        listOfTypeLists.add(stack100000);

        ArrayList<Data> arrayList1000Copy = new ArrayList<>();
        ArrayList<Data> arrayList10000Copy = new ArrayList<>();
        ArrayList<Data> arrayList100000Copy = new ArrayList<>();
        LinkedList<Data> linkedList1000Copy = new LinkedList<>();
        LinkedList<Data> linkedList10000Copy = new LinkedList<>();
        LinkedList<Data> linkedList100000Copy = new LinkedList<>();
        Vector<Data> vector1000Copy = new Vector<>();
        Vector<Data> vector10000Copy = new Vector<>();
        Vector<Data> vector100000Copy = new Vector<>();
        Stack<Data> stack1000Copy = new Stack<>();
        Stack<Data> stack10000Copy = new Stack<>();
        Stack<Data> stack100000Copy = new Stack<>();

        List<List<Data>> listOfTypeListsCopies = new ArrayList<>();
        listOfTypeLists.add(arrayList1000);
        listOfTypeLists.add(arrayList10000);
        listOfTypeLists.add(arrayList100000);
        listOfTypeLists.add(linkedList1000);
        listOfTypeLists.add(linkedList10000);
        listOfTypeLists.add(linkedList100000);
        listOfTypeLists.add(vector1000);
        listOfTypeLists.add(vector10000);
        listOfTypeLists.add(vector100000);
        listOfTypeLists.add(stack1000);
        listOfTypeLists.add(stack10000);
        listOfTypeLists.add(stack100000);


        double[][] tiempos = new double [4][3];
        System.out.println("-------Time to Store in ms");
        System.out.println("TypeList \t\t 1000 \t\t 10000 \t\t 100000");
        int z = 0;
        for (int i = 0; i<4; i++){
            System.out.printf( "%-10s", listOfTypeLists.get(z).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiempos[i][j] = store( listOfLists.get(j), listOfTypeLists.get(z));
                System.out.printf( "\t\t%.5f", tiempos[i][j]);
                z++;
            }
            System.out.println();
        }

        double[][] tiemposSearch = new double [4][3];
        System.out.println("-------Time to Search in ms");
        System.out.println("TypeList \t\t 1000 \t\t 10000 \t\t 100000");
         z = 0;
        for (int i = 0; i<4; i++){
            System.out.printf( "%-10s", listOfTypeLists.get(z).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiemposSearch[i][j] = getTimeSearch( listOfElementsToFind.get(j), listOfTypeLists.get(z));
                System.out.printf( "\t\t%.5f", tiemposSearch[i][j]);
                z++;
            }
            System.out.println();
        }
        double[][] tiemposRemove = new double [4][3];
        System.out.println("-------Time to Remove in ms");
        System.out.println("TypeList \t\t 1000 \t\t 10000 \t\t 100000");
        z = 0;
        for (int i = 0; i<4; i++){
            System.out.printf( "%-10s", listOfTypeLists.get(z).getClass().getSimpleName());
            for (int j = 0; j<3; j++){
                tiemposRemove[i][j] = getTimeRemove( listOfElementsToFind.get(j), listOfTypeLists.get(z), listOfTypeListsCopies.get(z));
                System.out.printf( "\t\t%.5f", tiemposRemove[i][j]);
                z++;
            }
            System.out.println();
        }

    }
}
