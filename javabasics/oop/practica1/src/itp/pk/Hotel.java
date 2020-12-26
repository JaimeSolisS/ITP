package itp.pk;

import java.util.Scanner;

public class Hotel {

    public static void initialise(Cuarto[] habitaciones) {
        for (int x = 0; x < habitaciones.length; x++) {
            habitaciones[x].setName("vacio");
        }
    }

    public static void ReservarHabitacion(Cuarto[] habitaciones, int roomNum) {
        System.out.println("Reservar Habitación:");
        String roomName;
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese numero de habitacion (1-10):");
        roomNum = input.nextInt() - 1;
        System.out.println("Ingrese nombre del huesped " + (roomNum + 1) + " :");
        roomName = input.next();
        habitaciones[roomNum].setName(roomName);
    }

    public static void VerSiVacio(Cuarto[] habitaciones) {
        System.out.println("Habitaciones Vacias:");
        for (int x = 0; x < habitaciones.length; x++) {
            if (habitaciones[x].getName().equals("vacio")) {
                System.out.println("La habitacion " + (x + 1) + " esta vacia");
            }
        }
    }

    public static void VerTodasLasHabitaciones(Cuarto[] habitaciones) {
        System.out.println("Todas las habitaciones: ");
        for (int x = 0; x < habitaciones.length; x++) {
            if (habitaciones[x].getName().equals("vacio"))
                System.out.println("La habitacion " + (x + 1) + " esta vacia");
            else System.out.println("Habitacion " + (x + 1) + " ocupada por " + habitaciones[x].getName());
        }
    }

    public static void BorrarHuesped(Cuarto[] habitaciones, int roomNum) {
        System.out.println("Borrar reservacion: ");
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese numero de habitacion para borrar (1-10):");
        roomNum = input.nextInt() - 1;
        habitaciones[roomNum].setName("vacio");
        System.out.println("Se ha eliminado al huesped :)");
    }

    public static void BuscarHuesped(Cuarto[] habitaciones) {
        System.out.println("Buscar Reservacion");
        Scanner input = new Scanner(System.in);
        String roomName;
        System.out.println("Ingrese nombre para buscar");
        roomName = input.next();
        int x;
        boolean Checker = false;
        for (x = 0; x < habitaciones.length; x++) {
            if (roomName.equals(habitaciones[x].getName())) {
                System.out.println("El huesped "+ habitaciones[x].getName() + "Tiene asignada la habitacion " + (x+1));
                Checker = true;
            }
        }
        if (Checker == false) {
            System.out.println("No hay habitaciones asignadas a ese nombre");
        }
    }
}
