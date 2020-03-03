package agente.vuelos;

import java.util.LinkedList;

/**
 *
 * @author angelrg
 */
public class AgenteVuelos {

    final int iterations = 20;
    final int hormigas = 20;
    final double alpha = 0.5;
    final double roo = 0.6;

    LinkedList<Destino> destinos = new LinkedList<>();
    LinkedList<Destino> destinosDisponibles = new LinkedList<>();

    LinkedList<recorridoHormiga> recorridosCortos = new LinkedList<>();

    public static void main(String[] args) {
        AgenteVuelos agenteV = new AgenteVuelos();

        agenteV.startData();
//        agenteV.recorrido("Guatemala", "EEUU");
//        agenteV.algoritmoHormigas("Guatemala", "EEUU");
        agenteV.algoritmoHormigas("Guatemala", "Chile");
    }

    public Destino findDestino(String name) {
        for (Destino destino : destinos) {
            if (destino.getName().replaceAll(" ", "").equalsIgnoreCase(name.replaceAll(" ", ""))) {
                return destino;
            }
        }
        return null;
    }

    public int findDestinoPosicion(String name) {
        for (int i = 0; i < destinos.size(); i++) {
            if (destinos.get(i).getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public void recorrido(String inicio, String fin) {
        if (findDestino(inicio) != null) {
            for (Route rute : findDestino(inicio).getRutes()) {
                if (!rute.destino.routes.isEmpty()) {
                    System.out.print(findDestino(inicio).Name + "->");
                    recorrido(rute.destino.Name, fin);
                } else {
                    if (rute.destino.Name.equalsIgnoreCase(fin)) {
                        System.out.println(rute.destino.Name);
                    }
                }
            }
        }
    }

    public void startData() {
        destinos.add(new Destino("Guatemala"));
        destinos.add(new Destino("Mexico"));
        destinos.add(new Destino("El Salvador"));
        destinos.add(new Destino("Nicaragua"));
        destinos.add(new Destino("Honduras"));
        destinos.add(new Destino("Costa Rica"));
        destinos.add(new Destino("EEUU"));
        destinos.add(new Destino("Colombia"));
        destinos.add(new Destino("Peru"));
        destinos.add(new Destino("Argentina"));
        destinos.add(new Destino("Chile"));

        findDestino("Guatemala").addRoute(new Route(findDestino("Guatemala"), findDestino("Mexico"), 120, 45));
        findDestino("Guatemala").addRoute(new Route(findDestino("Guatemala"), findDestino("El Salvador"), 60, 90));
        findDestino("El Salvador").addRoute(new Route(findDestino("El Salvador"), findDestino("Nicaragua"), 60, 60));
        findDestino("El Salvador").addRoute(new Route(findDestino("El Salvador"), findDestino("Honduras"), 60, 60));
        findDestino("El Salvador").addRoute(new Route(findDestino("El Salvador"), findDestino("Costa Rica"), 60, 90));
        findDestino("El Salvador").addRoute(new Route(findDestino("El Salvador"), findDestino("EEUU"), 360, 90));
        findDestino("El Salvador").addRoute(new Route(findDestino("El Salvador"), findDestino("Colombia"), 120, 90));
        findDestino("Colombia").addRoute(new Route(findDestino("Colombia"), findDestino("Peru"), 120, 90));
        findDestino("Colombia").addRoute(new Route(findDestino("Colombia"), findDestino("Argentina"), 210, 105));
        findDestino("Colombia").addRoute(new Route(findDestino("Colombia"), findDestino("Chile"), 210, 120));
        findDestino("Colombia").addRoute(new Route(findDestino("Colombia"), findDestino("EEUU"), 480, 180));
        findDestino("Argentina").addRoute(new Route(findDestino("Argentina"), findDestino("Chile"), 45, 45));
        findDestino("Mexico").addRoute(new Route(findDestino("Mexico"), findDestino("Nicaragua"), 120, 30));
        findDestino("Mexico").addRoute(new Route(findDestino("Mexico"), findDestino("Honduras"), 120, 30));
        findDestino("Mexico").addRoute(new Route(findDestino("Mexico"), findDestino("Costa Rica"), 135, 30));
        findDestino("Mexico").addRoute(new Route(findDestino("Mexico"), findDestino("Colombia"), 150, 60));
        findDestino("Mexico").addRoute(new Route(findDestino("Mexico"), findDestino("EEUU"), 210, 180));

//        for (Destino destino : destinos) {
//            destino.PrintMe();
//        }
    }

    public void algoritmoHormigas(String inicio, String fin) {
        try {
            for (int i = 0; i < iterations; i++) {
                LinkedList<recorridoHormiga> recorrido = new LinkedList<>();

                for (int j = 0; j < hormigas; j++) {
                    if (!destinosDisponibles.isEmpty()) {
                        destinosDisponibles.clear();
                    }
                    destinosDisponibles.addAll(destinos);

                    recorridoHormiga caminoHormiga = new recorridoHormiga();
                    Destino destinoActual = findDestino(inicio);

                    while (!destinoActual.getName().equalsIgnoreCase(fin)) {
                        eliminarDestinoActual(destinoActual, destinosDisponibles);
                        if (!destinosDisponibles.isEmpty()) {
                            Route routeAux = probabilidad(destinoActual, fin);
                            caminoHormiga.sumarLongitudCamino(routeAux.tiempoVuelo);
                            
                            if (routeAux.getDestino().getName().equalsIgnoreCase(fin)) {
                            caminoHormiga.sumarLongitudCamino(routeAux.tiempoEspera);
                            }
                            
                            caminoHormiga.agregarRecorrido(routeAux);
                            destinoActual = routeAux.getDestino();
                        }

                    }
                    recorrido.add(caminoHormiga);
                }
                evaporacionFeromonas();
                recorridoHormiga mejorRecorrido = obtenerMejorRecorrido(recorrido);
                enriquecerFeromonas(recorrido, mejorRecorrido.getLongitud());
                recorridosCortos.add(mejorRecorrido);
            }
            obtenerMejorRecorrido(recorridosCortos).imprimirRecorrido();
        } catch (Exception e) {
        }
    }

    private void enriquecerFeromonas(LinkedList<recorridoHormiga> recorrido, double mayorRecorrido) {
        for (int i = 0; i < recorrido.size(); i++) {
            for (int j = 0; j < recorrido.get(i).getCamino().size(); j++) {
                String destinoSiguiente = recorrido.get(i).getCamino().get(j).getDestino().getName();
                if ((j + 1) < recorrido.get(i).getCamino().size()) {
                    String detinoSiguiente = recorrido.get(i).getCamino().get(j + 1).getDestino().getName();
                    if (destinoSiguiente.equalsIgnoreCase(detinoSiguiente)) {
                        double factor = recorrido.get(i).getLongitud() / mayorRecorrido;
                        double nuevasFeromonas = recorrido.get(i).getCamino().get(j).getFeromonas() * (1 + factor);
                        recorrido.get(i).getCamino().get(j).setFeromonas(nuevasFeromonas);
                    }
                }
            }
        }
    }

    private recorridoHormiga obtenerMejorRecorrido(LinkedList<recorridoHormiga> lista) {
        double mejor = 0;
        recorridoHormiga mejorR = null;
        for (recorridoHormiga listaAux : lista) {
            if (listaAux.getLongitud() > mejor) {
                mejor = listaAux.getLongitud();
                mejorR = listaAux;
            }
        }
        return mejorR;
    }

    private void evaporacionFeromonas() {
        for (Destino destino : destinos) {
            for (Route rute : destino.getRutes()) {
                rute.setFeromonas(rute.getFeromonas() * (1 - roo));
            }
        }
    }

    private Route probabilidad(Destino calcNodo, String fin) {
        Route nodoAux = null;
        double mejorProbabilidad = 0;
        double probabilidadGeneral = 0;

        for (Route RouteList : calcNodo.getRutes()) {
            probabilidadGeneral += (Math.pow(RouteList.getFeromonas(), alpha));
        }

        for (Route RouteAux : calcNodo.getRutes()) {
            if (destinoDisponiblePorNombre(RouteAux.getDestino().getName())) {
                double aux = ((Math.pow(RouteAux.getFeromonas(), alpha) * (RouteAux.getTiempoEspera() + RouteAux.getTiempoVuelo())) / probabilidadGeneral);
                if ((aux > mejorProbabilidad && !RouteAux.getDestino().getRutes().isEmpty())
                        || (aux > mejorProbabilidad && RouteAux.getDestino().getName().equalsIgnoreCase(fin))) {
                    nodoAux = RouteAux;
                    mejorProbabilidad = aux;
                }
            }
        }

        return nodoAux;
    }

    private boolean destinoDisponiblePorNombre(String nombre) {
        for (Destino destinosDisponible : destinosDisponibles) {
            if (destinosDisponible.getName().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    private boolean eliminarDestinoActual(Destino destino, LinkedList<Destino> destinos) {
        for (int i = 0; i < destinos.size(); i++) {
            if (destinos.get(i).getName().equalsIgnoreCase(destino.getName())) {
                destinos.remove(i);
            }
            return true;
        }
        return false;
    }

}
