package agente.vuelos;

import java.util.LinkedList;

/**
 *
 * @author angelrg
 */
public class recorridoHormiga {

    LinkedList<Route> camino = new LinkedList<>();
    double longitud = 0;

    public recorridoHormiga() {
    }

    public void sumarLongitudCamino(double peso) {
        longitud += peso;
    }

    public void agregarRecorrido(Route nodoRecorrido) {
        camino.add(nodoRecorrido);
    }

    public LinkedList<Route> getCamino() {
        return camino;
    }

    public double getLongitud() {
        return longitud;
    }

    public void imprimirRecorrido() {
        System.out.println("\n>>>>>Tiempo total: " + this.longitud + "<<<<<<\n");
        for (Route road : this.camino) {
            road.printMe();
        }
    }
}
