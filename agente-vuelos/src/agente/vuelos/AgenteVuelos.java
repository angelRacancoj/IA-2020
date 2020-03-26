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

    LinkedList<Destino> destinos;
    LinkedList<Destino> destinosDisponibles = new LinkedList<>();

    LinkedList<recorridoHormiga> recorridosCortos = new LinkedList<>();

    public AgenteVuelos(LinkedList<Destino> destinos) {
        this.destinos = destinos;
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

                    while (!destinoActual.getName().equalsIgnoreCase(fin) && !destinoActual.getRutes().isEmpty()) {
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
        double mejor = lista.getFirst().getLongitud();
        recorridoHormiga mejorR = lista.getFirst();
        for (recorridoHormiga listaAux : lista) {
            if (listaAux.getLongitud() < mejor) {
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
}
