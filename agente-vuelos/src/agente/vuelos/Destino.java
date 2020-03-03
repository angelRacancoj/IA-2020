package agente.vuelos;

import java.util.LinkedList;

/**
 *
 * @author angelrg
 */
public class Destino {

    String Name;
    LinkedList<Route> routes = new LinkedList<>();

    public Destino() {
    }

    public Destino(String Name) {
        this.Name = Name;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public LinkedList<Route> getRutes() {
        return routes;
    }

    public void setRutes(LinkedList<Route> rutes) {
        this.routes = rutes;
    }

    public void PrintMe() {
        System.out.println("Inicio: " + Name);
        for (Route route : routes) {
            route.printMe();
        }
    }
}
