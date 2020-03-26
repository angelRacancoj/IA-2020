package main;

import agente.fileManager.ManejadorArchivo;
import agente.vuelos.AgenteVuelos;
import agente.vuelos.Destino;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author angelrg
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LinkedList<Destino> destinos = new LinkedList<>();

        try {
            ManejadorArchivo files = new ManejadorArchivo();
            files.lecturaArchivo("/home/angelrg/Documents/comp-data-lab3-03-2020.txt", destinos);
            AgenteVuelos agenteV = new AgenteVuelos(destinos);
            agenteV.algoritmoHormigas("Guatemala", "Italia");
        } catch (IOException e) {
            System.out.println("Error en la lectura del archivo");
            e.printStackTrace();
        }
    }

}
