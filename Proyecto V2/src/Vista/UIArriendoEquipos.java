package Vista;

import Controlador.ControladorArriendoEquipos;
import Excepciones.*;
import Modelo.EstadoEquipo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.zip.DataFormatException;

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
                System.out.println("6. Paga arriendo");
                System.out.println("7. Genera reportes");
                System.out.println("8. Cargar datos desde archivo");
                System.out.println("9. Guardar datos en archivo");
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
                    case 3:
                        arriendaEquipos();
                        break;
                    case 4:
                        devuelveEquipos();
                        break;
                    case 5:
                        cambiaEstadoCliente();
                        break;
                    case 6:
                        pagaArriendo();
                        break;
                    case 7:
                        do {
                            System.out.println("*** MENU DE REPORTES ***");
                            System.out.println("1. Lista todos los clientes");
                            System.out.println("2. Lista todos los equipos");
                            System.out.println("3. Lista de todos los arriendos");
                            System.out.println("4. Lista detalles de un arriendo");
                            System.out.println("5. Lista arriendos con pagos");
                            System.out.println("6. Lista los pagos de un arriendo");
                            System.out.println("7. Salir");
                            System.out.print("\tIngrese opcion: ");
                            opcion= scan.nextInt();
                            switch (opcion) {
                                case 1:
                                    listaCliente();
                                    break;
                                case 2:
                                    listaEquipo();
                                    break;
                                case 3:
                                    listaArriendos();
                                    break;
                                case 4:
                                    listaDetallesArriendo();
                                    break;
                                case 5:
                                    listaArriendosPagados();
                                    break;
                                case 6:
                                    listaPagosDeUnArriendo();
                                    break;
                                case 7:
                                    break;
                            }
                        }while (opcion!=7);
                        listaEquipo();
                        break;
                    case 8:
                        readDatosSistema();
                        break;
                    case 9:
                        saveDatosSistema();
                        break;
                    case 10:
                        break;
                    default:
                        System.out.println("La opcion ingresada no es valida\n\n");
                        break;
                }
            }while(opcion!=10);
        }catch (InputMismatchException | ClienteException | EquipoException | ArriendoException e) {
            System.out.println("El caracter ingresado no es valido, intenta de nuevo con uno que sea valido");
            menu();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }

    }

    private void listaPagosDeUnArriendo() throws ArriendoException {
        System.out.println("Codigo arriendo");
        long cod= scan.nextLong();
        //newwwwwwwwwwwwwwwww
        String [][] listaPagosdeUnArriendo = ControladorArriendoEquipos.getInstancia().listaPagosDeArriendo(cod);
        if (listaPagosdeUnArriendo.length == 0) {
            System.out.println("El arriendo no tiene pagos asociados");
            return;
        }
        System.out.println("\n>>>>>>>>>>>>>>   PAGOS REALIZADOS   <<<<<<<<<<<<<<<");
            System.out.printf("%-15s %-15s %-15s %n", "Monto", "Fecha", "Tipo pago");
            for (String[] linea : listaPagosdeUnArriendo){
                System.out.printf("%-15s %-15s %-15s %n", linea[0],linea[1], linea[2]);
            }
        //}
        //newwwwwwwwwwwwwwwww
    }

    private void listaArriendosPagados() {
        String[][] arriendosPagados = ControladorArriendoEquipos.getInstancia().listaArriendosPagados();
        if (arriendosPagados.length > 0) {
            System.out.println("\nLISTADO DE ARRIENDOS PAGADOS");
            System.out.println("-----------------------------");
            System.out.printf("%-15s%-15s%-15s%-15s%15s%15s%15s%n","Codigo","Estado","Rut cliente","Nombre cliente","Monto deuda","Monto pagado","Saldo adeudado");
            for (String[] dato : arriendosPagados) {    //revisar si es correcto el recorrido
                System.out.printf("%-15s%-15s%-15s%-15s%15s%15s%15s%n", dato[0], dato[1], dato[2], dato[3], dato[4], dato[5],dato[6]);
            }
        } else {
            System.out.println("\nNo hay arriendos pagados");
        }

    }

    private void pagaArriendo() {
        System.out.println("Pagando arriendo...");
        System.out.println("Codigo arriendo a pagar: ");
        long cod = scan.nextInt();
        System.out.println("\n----- ANTECEDENTES DEL ARRIEDNO -----");
        String [] datosArriendo;
        datosArriendo = ControladorArriendoEquipos.getInstancia().consultaArriendoAPagar(cod);
        if (datosArriendo.length!=0) {  //se debe validar el estado???
            System.out.println("Codigo: " + datosArriendo[0]);
            System.out.println("Estado: " + datosArriendo[1]);
            System.out.println("Rut cliente: " + datosArriendo[2]);
            System.out.println("Nombre cliente: " + datosArriendo[3]);
            System.out.println("Monto total: " + datosArriendo[4]);
            System.out.println("Monto pagado: " + datosArriendo[5]);
            System.out.println("Saldo adeudado: " + datosArriendo[6]);
            System.out.println("\n----- ANTECEDENTES DEL PAGO -----");
            System.out.println("Medio de pago (1: Contado, 2: Debito, 3: Credito): ");

            //newwwwwwwwwwwwwwwwww
            int opcionPago = scan.nextInt();
            while (opcionPago!= 1 && opcionPago!=2 && opcionPago!=3){
                System.out.println("Ingrese un numero entre los rangos ");
                opcionPago = scan.nextInt();
            }
            //newwwwwwwwwwwwwwwwww
            System.out.println("Monto: ");
            long monto=scan.nextLong(); //se debe validar el monto??? (que deba ser mayor a 0)
            String codTransaccion, numTarjeta;
            try {
                switch (opcionPago) {
                    case 1 -> ControladorArriendoEquipos.getInstancia().pagaArriendoContado(cod, monto);
                    case 2 -> {
                        System.out.println("Codigo transaccion ");
                        codTransaccion = scan.next();
                        System.out.println("Numero tarjeta ");
                        numTarjeta = scan.next();
                        ControladorArriendoEquipos.getInstancia().pagaArriendoDebito(cod, monto, codTransaccion, numTarjeta);
                    }
                    case 3 -> {
                        System.out.println("Codigo transaccion ");
                        codTransaccion = scan.next();
                        System.out.println("Numero tarjeta ");
                        numTarjeta = scan.next();
                        System.out.println("Numero de cuotas ");
                        int nroCuotas = scan.nextInt();
                        ControladorArriendoEquipos.getInstancia().pagaArriendoCredito(cod, monto, codTransaccion, numTarjeta, nroCuotas);
                    }
                }
            }catch (ArriendoException e){
                System.out.println(e.getMessage());
            }
        }else {
            System.out.println("Arriendo no existe o no se han devuelto los equipos arrendados");   //si consultaArriendoAPagar devuelve array[0]
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
        long cod=0;
        String desc="Hola";
        long precio=0;
        try {
            CrearEquipo creaEquipo=new CrearEquipo();
                    ControladorArriendoEquipos.getInstancia().creaImplemento(cod, desc, precio);
                    System.out.println("Se ha creado exitosamente un nuevo implemento");
        }catch (EquipoException e) {
            System.out.println(e.getMessage());
        }
    }
    private void crearComplemento(){
        long cod;
        String desc;
        int tipoEquipo;
        long precio;
        int nroEquiposComponentes;
        try {
            CrearEquipo creaEquipo=new CrearEquipo();
            cod=0;
            desc= "";
                    System.out.println("Numero de equipos componentes ");
                    nroEquiposComponentes=scan.nextInt();
                    long [] codEquipos = new long[nroEquiposComponentes];
                    for (int i=0; i < nroEquiposComponentes; i++) {
                        System.out.println("Codigo equipo " + i + " de " + nroEquiposComponentes + ":");
                        codEquipos[i] = scan.nextLong();
                    }

                    ControladorArriendoEquipos.getInstancia().creaConjunto(cod,desc,codEquipos);
                    System.out.println("Se ha creado exitosamente un nuevo conjunto");
        }catch (EquipoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void arriendaEquipos(){
        String[] nom;
        String[] equipo =new String[1];
        System.out.println("Arriendo de equipos...");
        System.out.print("Rut Cliente:");
        String rut = scan.next();
        try {
            long codArriendo = ControladorArriendoEquipos.getInstancia().creaArriendo(rut);//CREA ARRIENDO
            nom = ControladorArriendoEquipos.getInstancia().consultaCliente(rut);
            System.out.println("Nombre del cliente: "+ nom[1]);
            String opcion;
            long precioTotal=0;
            do {
                System.out.print("Codigo equipo: ");
                long codEquipo = scan.nextLong();
                equipo = ControladorArriendoEquipos.getInstancia().consultaEquipo(codEquipo);
                System.out.println(equipo[4]);
                System.out.println(ControladorArriendoEquipos.getInstancia().agregaEquipoToArriendo(codArriendo, codEquipo));
                System.out.println("Se ha agregado " +equipo[1]+ " al arriendo");
                System.out.print("Desea agregar otro equipo al arriendo? (s/n): ");
                opcion = scan.next();
                precioTotal = Long.parseLong(equipo[2])+ precioTotal;
            }while (opcion.equalsIgnoreCase("s"));
            ControladorArriendoEquipos.getInstancia().cierraArriendo(codArriendo);
            System.out.println("Monto total por dia de arriendo --->  $"+precioTotal );

        }catch (ClienteException| ArriendoException | EquipoException e){
            System.out.println(e.getMessage());
        }


        //*************FALTA COMPLETAR***************
    }
    
    /** private void devuelveEquipos() throws ClienteException, EquipoException, ArriendoException {
        String rut;
        long codigo;
        int estado;
        System.out.println("Devolviendo equipos arriendo...");
        System.out.println("Rut cliente: ");
        rut=scan.next();
        String[] cliente = ControladorArriendoEquipos.getInstancia().consultaCliente(rut);
        System.out.println("Nombre del cliente: " + cliente[1]+" \n");
        String[][] datosArriendos = ControladorArriendoEquipos.getInstancia().listaArriendosPorDevolver(rut);
            //if (datosArriendos.length > 0) {
                System.out.println("Los arriendos por devolver son: ");
                System.out.println("---------------------------------------------------------------");
                System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %n", "Codigo", "Fecha inicio", "Fecha devol.", "Estado", "Rut cliente","Monto total");
                for (String[] linea : datosArriendos){
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %n", linea[0],linea[1], linea[2], linea[3], linea[4], linea[5], linea[6]);
                }
                System.out.println("Ingrese el codigo del arriendo a devolver:");
                codigo=scan.nextLong();
                String[][] detallesArriendo = ControladorArriendoEquipos.getInstancia().listaDetallesArriendo(codigo);
                if (detallesArriendo.length > 0){
                    System.out.println("Ingresa codigo y estado en el que se devuelve cada equipo que se indica:");
                    EstadoEquipo[] estadoEquipo = new EstadoEquipo[detallesArriendo.length];
                    int i= 0;
                    for (String[] linea : detallesArriendo){
                        System.out.print(linea[1]+"("+linea[0]+")"+" --> Estado (1: Operativo, 2:En reparacion, 3: Dadode baja): ");
                        estado=scan.nextInt();
                        switch (estado) {
                            case 1 -> estadoEquipo[i] = EstadoEquipo.valueOf("OPERATIVO");
                            case 2 -> estadoEquipo[i] = EstadoEquipo.valueOf("EN REPARACION");
                            case 3 -> estadoEquipo[i] = EstadoEquipo.valueOf("DADO DE BAJA");
                            default -> System.out.println("ingrese un numero valido");
                        }
                    }
                    ControladorArriendoEquipos.getInstancia().devuelveEquipos(codigo, estadoEquipo);
                    System.out.println(detallesArriendo.length+" equipo(s) fue(ron) devuelto(s) exitosamente");
                }
            //}else {
            //	System.out.println("Problema en la linea 361 del ui");
           // }
    }
**/
    
    
    private void devuelveEquipos() throws ClienteException, EquipoException, ArriendoException {
    	  String rut;
    	  long codigo;
    	  int estado;
    	  System.out.println("Devolviendo equipos arriendo...");
    	  System.out.println("Rut cliente: ");
    	  rut = scan.next();
    	  String[] cliente = ControladorArriendoEquipos.getInstancia().consultaCliente(rut);
    	  System.out.println("Nombre del cliente: " + cliente[1] + " \n");
    	  String[][] datosArriendos = ControladorArriendoEquipos.getInstancia().listaArriendosPorDevolver(rut);

    	  if(datosArriendos.length==0) {
    		  System.out.println("La matriz esta vacia");
    	  }
    	    System.out.println("Los arriendos por devolver son: ");
    	    System.out.println("---------------------------------------------------------------");
    	    System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %n", "Codigo", "Fecha inicio", "Fecha devol.", "Estado", "Rut cliente", "Monto total");
    	    for (String[] linea : datosArriendos) {
    	      System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %n", linea[0], linea[1], linea[2], linea[3], linea[4], linea[5], linea[6]);
    	    }
    	    System.out.println("Ingrese el codigo del arriendo a devolver:");
    	    codigo = scan.nextLong();

    	    String[][] detallesArriendo = ControladorArriendoEquipos.getInstancia().listaDetallesArriendo(codigo);
            //if (detallesArriendo.length > 0){
                System.out.println("Ingresa codigo y estado en el que se devuelve cada equipo que se indica:");
                EstadoEquipo[] estadoEquipo = new EstadoEquipo[detallesArriendo.length];
                int i= 0;

                for (String[] linea : detallesArriendo){
                    System.out.print(detallesArriendo[i][1]+" ("+detallesArriendo[i][0]+")"+" --> Estado (1: Operativo, 2:En reparacion, 3: Dadode baja): ");
                    estado=scan.nextInt();
                    switch (estado) {
                        case 1 -> estadoEquipo[i] = EstadoEquipo.valueOf("OPERATIVO");
                        case 2 -> estadoEquipo[i] = EstadoEquipo.valueOf("EN REPARACION");
                        case 3 -> estadoEquipo[i] = EstadoEquipo.valueOf("DADO DE BAJA");
                        default -> System.out.println("ingrese un numero valido");
                    }
                    //cambio
                    i++;
                }
                ControladorArriendoEquipos.getInstancia().devuelveEquipos(codigo, estadoEquipo);
                System.out.println(detallesArriendo.length+" equipo(s) fue(ron) devuelto(s) exitosamente");
            //}
    	    
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
            System.out.println("Ingrese un caracter valido");
        }
    }

    private void listaCliente(){
        String[][] matrizCliente = ControladorArriendoEquipos.getInstancia().listaClientes();
        if (matrizCliente.length > 0) {
            System.out.println("\nListado de clientes");
            System.out.println("------------");
            System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n","Rut","Nombre","Direccion","Telefono","Estado","Arriendos por devolver");
            for (String[] linea : matrizCliente) {
                System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n", linea[0], linea[1], linea[2],linea[3],linea[4],linea[5]);
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

    private void listaArriendos() {

        try {
            SimpleDateFormat formato =new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("Fecha inicio periodo (dd/mm/aaaa):");
            String fechaInicio = scan.next();
            String[] fecha1 = fechaInicio.split("/");

            Date fechaInicial = formato.parse(fechaInicio);

            System.out.println("Fecha fin periodo (dd/mm/aaaa):");
            String fechaFin = scan.next();
            String[] fecha2 = fechaFin.split("/");

            Date fechaFinal = formato.parse(fechaFin);
            System.out.println("\nLISTADO DE ARRIENDOS");
            System.out.println("---------------------------");

            String[][] matrizArriendo = ControladorArriendoEquipos.getInstancia().listaArriendos(fechaInicial,fechaFinal);
            if (matrizArriendo.length > 0) {
                for (String[] linea : matrizArriendo) {
                    System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n", "Codigo", "Fecha inicio", "Fecha devol.", "Estado",
                            "Rut cliente", "Monto total");
                    System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n", linea[0], linea[1], linea[2], linea[3],
                            linea[4], linea[6]);
                }
            }else{
                System.out.println("\nNo hay arriendos");
            }
        } catch (ParseException e) {
            System.out.println("Error en el programa");
        }

    }

    private void listaDetallesArriendo() throws ArriendoException{
        System.out.println("codigo arriendo");
        long num = scan.nextLong();

        System.out.println("-------------------------------------------");

        String[] datosArriendo;
        datosArriendo = ControladorArriendoEquipos.getInstancia().consultaArriendo(num);

        System.out.println("Codigo: " + datosArriendo[0]);
        System.out.println("Fecha Inicio: " + datosArriendo[1]);
        System.out.println("Fecha Devolucion: " + datosArriendo[2]);
        System.out.println("Estado: " + datosArriendo[3]);
        System.out.println("Rut cliente: " + datosArriendo[4]);
        System.out.println("Monto total: " + datosArriendo[5]);


        System.out.println("-------------------------------------------");
        System.out.println("          DETALLE DEL ARRIENDO");
        System.out.println("-------------------------------------------");

        String[][] detallesArriendo;
        detallesArriendo = ControladorArriendoEquipos.getInstancia().listaDetallesArriendo(num);

        int i = 0;
        for (String[] detalles : detallesArriendo) {
            System.out.println(detallesArriendo[i][1]);
            i++;
        }
    }

    private void saveDatosSistema() throws DataFormatException {
    	System.out.println("Guardando datos del sistema");
        ControladorArriendoEquipos.getInstancia().saveDatosSistema();
    }
    private void readDatosSistema() throws DataFormatException {
    	System.out.println("Cargando datos guardados");
        ControladorArriendoEquipos.getInstancia().readDatosSistema();

    }
}
