package agente.vuelos;

/**
 *
 * @author angelrg
 */
public class Route {

    Destino origen;
    Destino destino;
    int tiempoVuelo;
    int tiempoEspera;
    double feromonas;

    public Route(Destino origen, Destino destino, int tiempoVuelo, int tiempoEspera) {
        this.origen = origen;
        this.destino = destino;
        this.tiempoVuelo = tiempoVuelo;
        this.tiempoEspera = tiempoEspera;
        this.feromonas = (Math.random() * ((0.5 - 0.1) + 1)) + 0.1;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public int getTiempoVuelo() {
        return tiempoVuelo;
    }

    public void setTiempoVuelo(int tiempoVuelo) {
        this.tiempoVuelo = tiempoVuelo;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public double getFeromonas() {
        return feromonas;
    }

    public void setFeromonas(double feromonas) {
        this.feromonas = feromonas;
    }

    public Destino getOrigen() {
        return origen;
    }

    public void setOrigen(Destino origen) {
        this.origen = origen;
    }

    public void printMe() {
        System.out.println("Inicio:" + origen.Name + " Destino: " + destino.Name + " Vuelo:" + tiempoVuelo + " Espera:" + tiempoEspera);
    }
}
