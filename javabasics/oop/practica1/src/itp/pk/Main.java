package itp.pk;
import itp.pk.Hotel;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static boolean Menu = true;

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        Cuarto[] habitaciones = new Cuarto[10];
        for (int i = 0; i<10; i++)
            habitaciones[i] = new Cuarto();

        int roomNum = 0;
        Hotel.initialise(habitaciones);
            while (Menu) {
                ImprimirMenu();
                String Selection = input.next();
                Selection = Selection.toUpperCase();
                Opciones(Selection, habitaciones, roomNum);
                System.out.println();
                System.out.println("Realizar otra operacion\n1 ) Si\n2 ) No");
                System.out.println("------------------------------------------");
                if (input.nextInt() == 1) {
                    Menu = true;
                } else {
                    Menu = false;
                    System.out.println("Adios");
                }
            }
        }
    private static void ImprimirMenu(){
        System.out.println("***** HOTEL *****");
        System.out.println("Selecciona una de las opciones");
        System.out.println("R: Reservar habitacion");
        System.out.println("V: Ver habitaciones vacias.");
        System.out.println("T: Ver todas las habitaciones.");
        System.out.println("E: Eliminar huesped");
        System.out.println("B: Buscar reservacion");
        System.out.println("------------------------------------------");
    }

    private static void Opciones(String Opcion, Cuarto[] habitaciones, int roomNum){
        switch (Opcion) {
            case "R":
                Hotel.ReservarHabitacion(habitaciones, roomNum);
                break;
            case "V":
                Hotel.VerSiVacio(habitaciones);
                break;
            case "T":
                Hotel.VerTodasLasHabitaciones(habitaciones);
                break;
            case "E":
                Hotel.BorrarHuesped(habitaciones, roomNum);
                break;
            case "B":
                Hotel.BuscarHuesped(habitaciones);
                break;
            default:
                System.out.println("Opcion Invalida");
                break;
        }
    }

















}

