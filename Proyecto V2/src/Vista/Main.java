package Vista;

import Excepciones.ClienteException;

public class Main {
    public static void main(String[] args) throws ClienteException {
        UIArriendoEquipos.getInstancia().menu();
    }
}
