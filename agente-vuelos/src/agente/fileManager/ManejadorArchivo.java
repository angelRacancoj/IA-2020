package agente.fileManager;

import agente.vuelos.Destino;
import agente.vuelos.Route;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author angelrg
 */
public class ManejadorArchivo {

    BufferedReader lectura;

    public void lecturaArchivo(String path, LinkedList<Destino> destinos) throws FileNotFoundException, IOException {
        System.out.println(path);
        File file = new File(path);
        String linea = ",";
        lectura = new BufferedReader(new FileReader(file));
        while ((linea = lectura.readLine()) != null && !linea.isEmpty()) {
            String[] text = linea.split(",");

            Destino inicio = buscarDestino(destinos, text[0]);
            Destino destino = buscarDestino(destinos, text[1]);

            if (inicio == null) {
                inicio = new Destino(text[0]);
                destinos.add(inicio);
            }
            if (destino == null) {
                destino = new Destino(text[1]);
                destinos.add(destino);
            }
            inicio.addRoute(new Route(inicio, destino, Integer.parseInt(text[2].replaceAll(" ", "")), Integer.parseInt(text[3].replaceAll(" ", ""))));
        }
    }

    private Destino buscarDestino(LinkedList<Destino> destinos, String pais) {
        for (Destino destino : destinos) {
            if (destino.getName().replaceAll(" ", "").equalsIgnoreCase(pais.replaceAll(" ", ""))) {
                return destino;
            }
        }
        return null;
    }
}
