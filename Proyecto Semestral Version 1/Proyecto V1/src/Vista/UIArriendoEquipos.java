package Vista;

import Controlador.ControladorArriendoEquipos;
import Excepciones.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UIArriendoEquipos {

    private static UIArriendoEquipos instancia=null;
    private final Scanner scan;
    private UIArriendoEquipos(){
        scan=new Scanner(System.in);
    }

    public static UIArriendoEquipos getInstancia(){

        if(instancia==null){
            instancia=new UIArriendoEquipos();
        }
        return instancia;
    }

    public void menu() {
        int opcion;
        try {
            do{
                System.out.println("******* SISTEMA DE ARRIENDO DE EQUIPOS DE NIEVE *******");
                System.out.println("\n*** MENU DE OPCIONES ***");
                System.out.println("1. Crea un nuevo cliente");
                System.out.println("2. Crea un nuevo equipo");
                System.out.println("3. Arrienda equipos");
                System.out.println("4. Devuelve equipos");
                System.out.println("5. Cambiar estado de un cliente");
                System.out.println("6. Lista todos los clientes");
                System.out.println("7. Lista todos los equipos");
                System.out.println("8. Lista de todos los arriendos");
                System.out.println("9. Lista detalles de un arriendo");
                System.out.println("10. Salir");
                System.out.print("\tIngrese opcion: ");
                opcion=scan.nextInt();

                switch(opcion){
                    case 1:
                        crearCliente();
                        break;
                    case 2:
                        crearEquipo();
                        break;
                    case 5:
                        cambiaEstadoCliente();
                        break;
                    case 6:
                        listaCliente();
                        break;
                    case 7:
                        listaEquipo();
                        break;
                    case 10:
                        break;
                    default:
                        System.out.print("La opcion ingresada no es valida");
                        break;
                }
            }while(opcion!=10);
        }catch (InputMismatchException e) {
            System.out.println("El caracter ingresado no es valido, intenta de nuevo con uno que sea valido");
        }

    }
    private void crearCliente(){
        String rut,nom,dir,tel;
        try {
            System.out.println("Ingrese el rut del cliente: ");
            scan.nextLine();
            rut=scan.nextLine();
            System.out.println("Ingrese el nombre del cliente: ");
            nom=scan.nextLine();

            System.out.println("Ingrese la direccion del cliente: ");
            dir=scan.nextLine();

            System.out.println("Ingrese el numero de telefono del cliente: ");
            tel=scan.next();

            ControladorArriendoEquipos.getInstancia().creaCliente(rut,nom,dir,tel);
        }catch (ClienteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void crearEquipo(){
        long cod;
        String desc;
        long precio;
        try {
            System.out.println("Ingrese el codigo del equipo: ");
            cod=scan.nextLong();
            scan.nextLine();
            System.out.println("Ingrese la descripcion del equipo: ");
            desc=scan.nextLine();
            System.out.println("Ingrese el precio del equipo: ");
            precio=scan.nextLong();
            ControladorArriendoEquipos.getInstancia().creaEquipo(cod,desc,precio);
        }catch (EquipoException e) {
            System.out.println(e.getMessage());
        }
    }
    private void cambiaEstadoCliente(){
        String rut;
        try {
            System.out.println("Cambiando estado de un cliente");
            System.out.println("Rut cliente");
            rut=scan.next();
            String[] cliente = ControladorArriendoEquipos.getInstancia().consultaCliente(rut);
            if (cliente.length > 0) {
                ControladorArriendoEquipos.getInstancia().cambiaEstadoCliente(rut);
                if(cliente[4] == "Activo"){
                    cliente[4] = "Inactivo";
                }
                else {
                    cliente[4] = "Activo";
                }
                System.out.println("se a cambiado exitosamente el estado del cliente: "+ cliente[1]+" a "+ cliente[4]);
            }
        }catch(ClienteException e) {
            System.out.println(e.getMessage());
        }catch (InputMismatchException e) {
            System.out.println("ingrese un caracter valido");
        }
    }
    private void listaCliente(){
        String[][] matrizCliente = ControladorArriendoEquipos.getInstancia().listaClientes();
        if (matrizCliente.length > 0) {
            System.out.println("\nListado de clientes");
            System.out.println("------------");
            System.out.printf("%-15s%-15s%-15s%-15s%n","Rut","Nombre","Direccion","Telefono","Estado");
            for (String[] linea : matrizCliente) {
                System.out.printf("%-15s%-15s%-15s%-15s%n", linea[0], linea[1], linea[2],linea[3]);
            }
        } else {
            System.out.println("\nNo hay clientes");
        }
    }
    private void listaEquipo(){
        String[][] matrizEquipo = ControladorArriendoEquipos.getInstancia().listaEquipos();
        if (matrizEquipo.length > 0) {
            System.out.println("\nListado de equipo");
            System.out.println("------------");
            System.out.printf("%-15s%-15s%-15s%-15s%n","Codigo","Descripcion","Precio","Estado");
            for (String[] linea : matrizEquipo) {
                System.out.printf("%-15s%-15s%-15s%-15s%n", linea[0], linea[1], linea[2],linea[3]);
            }
        } else {
            System.out.println("\nNo hay equipo");
        }
    }
}
